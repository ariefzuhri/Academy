package com.ariefzuhri.academy.data.source.local.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

// Tabel semu/bukan tabel (ini relasi 1-n, courseId ada di moduleentities)
// Karena gabungan dari tabel courseentities dan moduleentities

/*Coursewithmodule adalah obyek baru yang menggabungkan CoursesEntity dengan ModulesEntity*/
public class CourseWithModule {
    /*Ini bisa tercipta dengan memanfaatkan anotasi @Embedded dan @Relation*/
    @Embedded
    public CourseEntity mCourse;

    @Relation(parentColumn = "courseId", entityColumn = "courseId")
    public List<ModuleEntity> mModules;
}
