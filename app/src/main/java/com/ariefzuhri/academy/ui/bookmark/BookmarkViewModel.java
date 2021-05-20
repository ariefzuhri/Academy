package com.ariefzuhri.academy.ui.bookmark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.ariefzuhri.academy.data.source.local.entity.CourseEntity;
import com.ariefzuhri.academy.data.AcademyRepository;

public class BookmarkViewModel extends ViewModel {
    private final AcademyRepository academyRepository;

    public BookmarkViewModel(AcademyRepository mAcademyRepository) {
        this.academyRepository = mAcademyRepository;
    }

    LiveData<PagedList<CourseEntity>> getBookmarks() {
        return academyRepository.getBookmarkedCourses();
    }

    void setBookmark(CourseEntity courseEntity) {
        final boolean newState = !courseEntity.isBookmarked();
        academyRepository.setCourseBookmark(courseEntity, newState);
    }
}
