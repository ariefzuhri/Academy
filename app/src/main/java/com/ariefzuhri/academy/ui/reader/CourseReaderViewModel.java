package com.ariefzuhri.academy.ui.reader;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ariefzuhri.academy.data.ModuleEntity;
import com.ariefzuhri.academy.data.source.AcademyRepository;

import java.util.List;

public class CourseReaderViewModel extends ViewModel {
    private String courseId;
    private String moduleId;
    private final AcademyRepository academyRepository;

    public CourseReaderViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public void setSelectedCourse(String courseId) {
        this.courseId = courseId;
    }

    public void setSelectedModule(String moduleId) {
        this.moduleId = moduleId;
    }

    public LiveData<List<ModuleEntity>> getModules() {
        return academyRepository.getAllModulesByCourse(courseId);
    }

    public LiveData<ModuleEntity> getSelectedModule() {
        return academyRepository.getContent(courseId, moduleId);
    }
}
