package com.ariefzuhri.academy.ui.bookmark;

import androidx.lifecycle.ViewModel;

import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.utils.DataDummy;

import java.util.List;

public class BookmarkViewModel extends ViewModel {
    public List<CourseEntity> getBookmarks() {
        return DataDummy.generateDummyCourses();
    }
}
