package com.ariefzuhri.academy.ui.academy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.ariefzuhri.academy.data.source.local.entity.CourseEntity;
import com.ariefzuhri.academy.data.AcademyRepository;
import com.ariefzuhri.academy.vo.Resource;

public class AcademyViewModel extends ViewModel {
    private final AcademyRepository academyRepository;

    public AcademyViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public LiveData<Resource<PagedList<CourseEntity>>> getCourses(){
        return academyRepository.getAllCourses();
    }
}
