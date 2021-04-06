package com.ariefzuhri.academy.ui.reader;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.ariefzuhri.academy.data.ContentEntity;
import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.data.ModuleEntity;
import com.ariefzuhri.academy.data.source.AcademyRepository;
import com.ariefzuhri.academy.utils.DataDummy;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CourseReaderViewModelTest {

    private CourseReaderViewModel viewModel;
    private final CourseEntity dummyCourse = DataDummy.generateDummyCourses().get(0);
    private final String courseId = dummyCourse.getCourseId();
    private final List<ModuleEntity> dummyModules = DataDummy.generateDummyModules(courseId);
    private final String moduleId = dummyModules.get(0).getModuleId();

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private AcademyRepository academyRepository;

    @Mock
    private Observer<List<ModuleEntity>> modulesObserver;

    @Mock
    private Observer<ModuleEntity> moduleObserver;

    @Before
    public void setUp() {
        viewModel = new CourseReaderViewModel(academyRepository);
        viewModel.setSelectedCourse(courseId);
        viewModel.setSelectedModule(moduleId);

        ModuleEntity dummyModule = dummyModules.get(0);
        dummyModule.contentEntity = new ContentEntity("<h3 class=\\\"fr-text-bordered\\\">" + dummyModule.getTitle() + "</h3><p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>");
    }

    @Test
    public void getModules() {
        MutableLiveData<List<ModuleEntity>> modules = new MutableLiveData<>();
        modules.setValue(dummyModules);

        when(academyRepository.getAllModulesByCourse(courseId)).thenReturn(modules);
        List<ModuleEntity> moduleEntities = viewModel.getModules().getValue();
        verify(academyRepository).getAllModulesByCourse(courseId);
        assertNotNull(moduleEntities);
        assertEquals(7, moduleEntities.size());

        viewModel.getModules().observeForever(modulesObserver);
        verify(modulesObserver).onChanged(dummyModules);
    }

    @Test
    public void getSelectedModule() {
        MutableLiveData<ModuleEntity> module = new MutableLiveData<>();
        module.setValue(dummyModules.get(0));

        when(academyRepository.getContent(courseId, moduleId)).thenReturn(module);
        ModuleEntity moduleEntity = viewModel.getSelectedModule().getValue();
        verify(academyRepository).getContent(courseId, moduleId);
        assertNotNull(moduleEntity);
        ContentEntity contentEntity = moduleEntity.contentEntity;
        assertNotNull(contentEntity);
        String content = contentEntity.getContent();
        assertNotNull(content);
        assertEquals(content,  dummyModules.get(0).contentEntity.getContent());

        viewModel.getSelectedModule().observeForever(moduleObserver);
        verify(moduleObserver).onChanged(dummyModules.get(0));
    }
}