package com.ariefzuhri.academy.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.ariefzuhri.academy.data.source.remote.RemoteDataSource;
import com.ariefzuhri.academy.data.source.remote.response.ContentResponse;
import com.ariefzuhri.academy.data.source.remote.response.CourseResponse;
import com.ariefzuhri.academy.data.source.remote.response.ModuleResponse;
import com.ariefzuhri.academy.utils.DataDummy;
import com.ariefzuhri.academy.utils.LiveDataTestUtil;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/* Kali ini kita menggunakan doAnswer.when karena pemanggilan getAllCourses membutuhkan callback.
Kemudian di bagian verify terdapat times(wantedNumberOfInvocations), yang berfungsi untuk melakukan
verify selama 1 kali dengan pengecekan apapun di kelas callback-nya.*/
public class AcademyRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final RemoteDataSource remote = Mockito.mock(RemoteDataSource.class);
    private final FakeAcademyRepository academyRepository = new FakeAcademyRepository(remote);

    private final List<CourseResponse> courseResponses = DataDummy.generateRemoteDummyCourses();
    private final String courseId = courseResponses.get(0).getId();
    private final List<ModuleResponse> moduleResponses = DataDummy.generateRemoteDummyModules(courseId);
    private final String moduleId = moduleResponses.get(0).getModuleId();
    private final ContentResponse content = DataDummy.generateRemoteDummyContent(moduleId);

    @Test
    public void getAllCourses() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));
        List<CourseEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourses());
        verify(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));
        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(), courseEntities.size());
    }

    @Test
    public void getAllModulesByCourse() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadModulesCallback) invocation.getArguments()[1])
                    .onAllModulesReceived(moduleResponses);
            return null;
        }).when(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModulesCallback.class));

        List<ModuleEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId));

        verify(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModulesCallback.class));

        assertNotNull(courseEntities);
        assertEquals(moduleResponses.size(), courseEntities.size());
    }

    @Test
    public void getBookmarkedCourses() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));

        List<CourseEntity> courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses());

        verify(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));

        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(), courseEntities.size());
    }

    @Test
    public void getContent() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadModulesCallback) invocation.getArguments()[1])
                    .onAllModulesReceived(moduleResponses);
            return null;
        }).when(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModulesCallback.class));

        doAnswer(invocation -> {
            ((RemoteDataSource.LoadContentCallback) invocation.getArguments()[1])
                    .onContentReceived(content);
            return null;
        }).when(remote).getContent(eq(moduleId), any(RemoteDataSource.LoadContentCallback.class));

        ModuleEntity courseEntitiesContent = LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId));

        verify(remote).getModules(eq(courseId), any(RemoteDataSource.LoadModulesCallback.class));

        verify(remote).getContent(eq(moduleId), any(RemoteDataSource.LoadContentCallback.class));

        assertNotNull(courseEntitiesContent);
        assertNotNull(courseEntitiesContent.contentEntity);
        assertNotNull(courseEntitiesContent.contentEntity.getContent());
        assertEquals(content.getContent(), courseEntitiesContent.contentEntity.getContent());
    }

    @Test
    public void getCourseWithModules() {
        doAnswer(invocation -> {
            ((RemoteDataSource.LoadCoursesCallback) invocation.getArguments()[0])
                    .onAllCoursesReceived(courseResponses);
            return null;
        }).when(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));

        CourseEntity courseEntities = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId));

        verify(remote).getAllCourses(any(RemoteDataSource.LoadCoursesCallback.class));

        assertNotNull(courseEntities);
        assertNotNull(courseEntities.getTitle());
        assertEquals(courseResponses.get(0).getTitle(), courseEntities.getTitle());
    }
}