package com.ariefzuhri.academy.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.data.ModuleEntity;
import com.ariefzuhri.academy.data.source.AcademyRepository;

import java.util.List;

public class DetailCourseViewModel extends ViewModel {
    private String courseId;
    private final AcademyRepository academyRepository;

    public DetailCourseViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public void setSelectedCourse(String courseId){
        this.courseId = courseId;
    }

    public LiveData<CourseEntity> getCourse() {
        return academyRepository.getCourseWithModules(courseId);
    }

    public LiveData<List<ModuleEntity>> getModules() {
        return academyRepository.getAllModulesByCourse(courseId);
    }
}
