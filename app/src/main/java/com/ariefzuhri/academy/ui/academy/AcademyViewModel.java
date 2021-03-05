package com.ariefzuhri.academy.ui.academy;

import androidx.lifecycle.ViewModel;

import com.ariefzuhri.academy.data.CourseEntity;
import com.ariefzuhri.academy.utils.DataDummy;

import java.util.List;

public class AcademyViewModel extends ViewModel {

    public List<CourseEntity> getCourses(){
        return DataDummy.generateDummyCourses();
    }
}
