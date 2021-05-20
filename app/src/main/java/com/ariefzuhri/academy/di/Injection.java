package com.ariefzuhri.academy.di;

import android.content.Context;

import com.ariefzuhri.academy.data.AcademyRepository;
import com.ariefzuhri.academy.data.source.local.LocalDataSource;
import com.ariefzuhri.academy.data.source.local.room.AcademyDatabase;
import com.ariefzuhri.academy.data.source.remote.RemoteDataSource;
import com.ariefzuhri.academy.utils.AppExecutors;
import com.ariefzuhri.academy.utils.JsonHelper;

public class Injection {

    public static AcademyRepository provideRepository(Context context){
        AcademyDatabase database = AcademyDatabase.getInstance(context);

        RemoteDataSource remoteDataSource = RemoteDataSource.getInstance(new JsonHelper(context));
        LocalDataSource localDataSource = LocalDataSource.getInstance(database.academyDao());
        AppExecutors appExecutors = new AppExecutors();

        return AcademyRepository.getInstance(remoteDataSource, localDataSource, appExecutors);
    }
}
