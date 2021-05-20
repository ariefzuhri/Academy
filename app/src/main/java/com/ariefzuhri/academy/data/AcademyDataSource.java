package com.ariefzuhri.academy.data;

import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.ariefzuhri.academy.data.source.local.entity.CourseEntity;
import com.ariefzuhri.academy.data.source.local.entity.CourseWithModule;
import com.ariefzuhri.academy.data.source.local.entity.ModuleEntity;
import com.ariefzuhri.academy.vo.Resource;

import java.util.List;

// Untuk menggabungkan kedua repository
public interface AcademyDataSource {

    LiveData<Resource<PagedList<CourseEntity>>> getAllCourses();

    LiveData<Resource<CourseWithModule>> getCourseWithModules(String courseId);

    LiveData<Resource<List<ModuleEntity>>> getAllModulesByCourse(String courseId);

    LiveData<Resource<ModuleEntity>> getContent(String moduleId);

    LiveData<PagedList<CourseEntity>> getBookmarkedCourses();

    void setCourseBookmark(CourseEntity course, boolean state);

    void setReadModule(ModuleEntity module);
}
