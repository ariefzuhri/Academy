package com.ariefzuhri.academy.di;

import android.content.Context;

import com.ariefzuhri.academy.data.source.AcademyRepository;
import com.ariefzuhri.academy.data.source.remote.RemoteDataSource;
import com.ariefzuhri.academy.utils.JsonHelper;

public class Injection {
    public static AcademyRepository provideRepository(Context context){
        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(new JsonHelper(context));
        return AcademyRepository.getInstance(remoteDataSource);
    }
}
