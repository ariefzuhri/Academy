package com.ariefzuhri.academy.data.source;

import androidx.lifecycle.LiveData;

import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.data.ModuleEntity;

import java.util.List;

// Untuk menggabungkan kedua repository
public interface AcademyDataSource {
    LiveData<List<CourseEntity>> getAllCourses();

    LiveData<CourseEntity> getCourseWithModules(String courseId);

    LiveData<List<ModuleEntity>> getAllModulesByCourse(String courseId);

    LiveData<List<CourseEntity>> getBookmarkedCourses();

    LiveData<ModuleEntity> getContent(String courseId, String moduleId);
}
