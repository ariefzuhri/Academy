package com.ariefzuhri.academy.data;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.ariefzuhri.academy.data.source.local.LocalDataSource;
import com.ariefzuhri.academy.data.source.local.entity.CourseEntity;
import com.ariefzuhri.academy.data.source.local.entity.CourseWithModule;
import com.ariefzuhri.academy.data.source.local.entity.ModuleEntity;
import com.ariefzuhri.academy.data.source.remote.RemoteDataSource;
import com.ariefzuhri.academy.data.source.remote.response.ContentResponse;
import com.ariefzuhri.academy.data.source.remote.response.CourseResponse;
import com.ariefzuhri.academy.data.source.remote.response.ModuleResponse;
import com.ariefzuhri.academy.utils.AppExecutors;
import com.ariefzuhri.academy.utils.DataDummy;
import com.ariefzuhri.academy.utils.LiveDataTestUtil;
import com.ariefzuhri.academy.utils.PagedListUtil;
import com.ariefzuhri.academy.vo.Resource;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/* Kali ini kita menggunakan doAnswer.when karena pemanggilan getAllCourses membutuhkan callback.
Kemudian di bagian verify terdapat times(wantedNumberOfInvocations), yang berfungsi untuk melakukan
verify selama 1 kali dengan pengecekan apapun di kelas callback-nya.*/
public class AcademyRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final RemoteDataSource remote = mock(RemoteDataSource.class);
    private LocalDataSource local = mock(LocalDataSource.class);
    private AppExecutors appExecutors = mock(AppExecutors.class);

    private final FakeAcademyRepository academyRepository = new FakeAcademyRepository(remote, local, appExecutors);

    private final List<CourseResponse> courseResponses = DataDummy.generateRemoteDummyCourses();
    private final String courseId = courseResponses.get(0).getId();
    private final List<ModuleResponse> moduleResponses = DataDummy.generateRemoteDummyModules(courseId);
    private final String moduleId = moduleResponses.get(0).getModuleId();
    private final ContentResponse content = DataDummy.generateRemoteDummyContent(moduleId);

    @Test
    public void getAllCourses() {
        DataSource.Factory<Integer, CourseEntity> dataSourceFactory = mock(DataSource.Factory.class);
        when(local.getAllCourses()).thenReturn(dataSourceFactory);
        academyRepository.getAllCourses();

        Resource<PagedList<CourseEntity>> courseEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyCourses()));
        verify(local).getAllCourses();
        assertNotNull(courseEntities.data);
        assertEquals(courseResponses.size(), courseEntities.data.size());
    }

    @Test
    public void getAllModulesByCourse() {
        MutableLiveData<List<ModuleEntity>> dummyModules = new MutableLiveData<>();
        dummyModules.setValue(DataDummy.generateDummyModules(courseId));
        when(local.getAllModulesByCourse(courseId)).thenReturn(dummyModules);

        Resource<List<ModuleEntity>> courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId));
        verify(local).getAllModulesByCourse(courseId);
        assertNotNull(courseEntities.data);
        assertEquals(moduleResponses.size(), courseEntities.data.size());
    }

    @Test
    public void getBookmarkedCourses() {
        DataSource.Factory<Integer, CourseEntity> dataSourceFactory = mock(DataSource.Factory.class);
        when(local.getBookmarkedCourses()).thenReturn(dataSourceFactory);
        academyRepository.getBookmarkedCourses();

        Resource<PagedList<CourseEntity>> courseEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyCourses()));
        verify(local).getBookmarkedCourses();
        assertNotNull(courseEntities);
        assertEquals(courseResponses.size(), courseEntities.data.size());
    }

    @Test
    public void getContent() {
        MutableLiveData<ModuleEntity> dummyEntity = new MutableLiveData<>();
        dummyEntity.setValue(DataDummy.generateDummyModuleWithContent(moduleId));
        when(local.getModuleWithContent(courseId)).thenReturn(dummyEntity);

        Resource<ModuleEntity> courseEntitiesContent = LiveDataTestUtil.getValue(academyRepository.getContent(courseId));
        verify(local).getModuleWithContent(courseId);
        assertNotNull(courseEntitiesContent);
        assertNotNull(courseEntitiesContent.data.contentEntity);
        assertNotNull(courseEntitiesContent.data.contentEntity.getContent());
        assertEquals(content.getContent(), courseEntitiesContent.data.contentEntity.getContent());
    }

    @Test
    public void getCourseWithModules() {
        MutableLiveData<CourseWithModule> dummyEntity = new MutableLiveData<>();
        dummyEntity.setValue(DataDummy.generateDummyCourseWithModules(DataDummy.generateDummyCourses().get(0), false));
        when(local.getCourseWithModules(courseId)).thenReturn(dummyEntity);

        Resource<CourseWithModule> courseEntities = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId));
        verify(local).getCourseWithModules(courseId);
        assertNotNull(courseEntities.data);
        assertNotNull(courseEntities.data.mCourse.getTitle());
        assertEquals(courseResponses.get(0).getTitle(), courseEntities.data.mCourse.getTitle());
    }
}