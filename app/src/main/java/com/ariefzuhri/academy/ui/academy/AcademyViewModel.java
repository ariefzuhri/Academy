package com.ariefzuhri.academy.ui.academy;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.data.source.AcademyRepository;

import java.util.List;

public class AcademyViewModel extends ViewModel {
    private final AcademyRepository academyRepository;

    public AcademyViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    public LiveData<List<CourseEntity>> getCourses(){
        return academyRepository.getAllCourses();
    }
}
