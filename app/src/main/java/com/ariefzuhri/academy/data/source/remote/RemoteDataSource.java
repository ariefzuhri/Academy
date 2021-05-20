package com.ariefzuhri.academy.data.source.remote;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ariefzuhri.academy.data.source.remote.response.ContentResponse;
import com.ariefzuhri.academy.data.source.remote.response.CourseResponse;
import com.ariefzuhri.academy.data.source.remote.response.ModuleResponse;
import com.ariefzuhri.academy.utils.EspressoIdlingResource;
import com.ariefzuhri.academy.utils.JsonHelper;

import java.util.List;

// Kelas repository untuk remote
public class RemoteDataSource {

    private static RemoteDataSource INSTANCE;
    private final JsonHelper jsonHelper;
    private final Handler handler = new Handler(Looper.getMainLooper()); // Memberikan waktu delay sesuai dengan kebutuhan
    private final long SERVICE_LATENCY_IN_MILLIS = 1000;

    private RemoteDataSource(JsonHelper jsonHelper){
        this.jsonHelper = jsonHelper;
    }

    /* Di sini terdapat method getInstance yang berfungsi untuk membuat kelas RemoteDataSource
    sebagai Singleton. Fungsinya yaitu supaya tercipta satu instance saja di dalam JVM.*/
    public static RemoteDataSource getInstance(JsonHelper helper){
        if (INSTANCE == null){
            INSTANCE = new RemoteDataSource(helper);
        }
        return INSTANCE;
    }

    public LiveData<ApiResponse<List<CourseResponse>>> getAllCourses() {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<CourseResponse>>> resultCourse = new MutableLiveData<>();
        handler.postDelayed(() -> {
            resultCourse.setValue(ApiResponse.success(jsonHelper.loadCourses()));
            EspressoIdlingResource.decrement();
        }, SERVICE_LATENCY_IN_MILLIS);
        return resultCourse;
    }

    public LiveData<ApiResponse<List<ModuleResponse>>> getModules(String courseId) {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<List<ModuleResponse>>> resultModules = new MutableLiveData<>();
        handler.postDelayed(() -> {
            resultModules.setValue(ApiResponse.success(jsonHelper.loadModule(courseId)));
            EspressoIdlingResource.decrement();
        }, SERVICE_LATENCY_IN_MILLIS);
        return resultModules;
    }

    public LiveData<ApiResponse<ContentResponse>> getContent(String moduleId) {
        EspressoIdlingResource.increment();
        MutableLiveData<ApiResponse<ContentResponse>> resultContent = new MutableLiveData<>();
        handler.postDelayed(() -> {
            resultContent.setValue(ApiResponse.success(jsonHelper.loadContent(moduleId)));
            EspressoIdlingResource.decrement();
        }, SERVICE_LATENCY_IN_MILLIS);
        return resultContent;
    }

    public interface LoadCoursesCallback {
        void onAllCoursesReceived(List<CourseResponse> courseResponses);
    }

    public interface LoadModulesCallback {
        void onAllModulesReceived(List<ModuleResponse> moduleResponses);
    }

    public interface LoadContentCallback {
        void onContentReceived(ContentResponse contentResponse);
    }
}

/* Penggunaan Handler untuk delay, seperti yang dilakukan di atas merupakah hal yang tidak
disarankan. Karena proyek yang kita buat hanyalah simulasi, di mana data yang diperoleh
disimulasikan berasal dari server atau API. Maka dari itu, penggunaan Handler pada proyek Academy
digunakan untuk mensimulasikan proses asynchronous yang terjadi.*/