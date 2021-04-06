package com.ariefzuhri.academy.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.data.source.AcademyRepository;

import java.util.List;

public class BookmarkViewModel extends ViewModel {
    private final AcademyRepository academyRepository;

    public BookmarkViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    LiveData<List<CourseEntity>> getBookmarks() {
        return academyRepository.getBookmarkedCourses();
    }
}
