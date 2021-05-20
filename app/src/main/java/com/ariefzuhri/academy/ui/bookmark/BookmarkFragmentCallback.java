package com.ariefzuhri.academy.ui.bookmark;

import com.ariefzuhri.academy.data.source.local.entity.CourseEntity;

interface BookmarkFragmentCallback {
    void onShareClick(CourseEntity course);
}
