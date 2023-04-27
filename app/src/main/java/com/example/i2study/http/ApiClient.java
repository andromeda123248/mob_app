package com.example.i2study.http;

import com.example.i2study.http.service.GroupService;
import com.example.i2study.http.service.LessonService;
import com.example.i2study.http.service.Lesson_attendanceService;
import com.example.i2study.http.service.UniversityService;
import com.example.i2study.http.service.UserService;
import com.example.i2study.urlStorage;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit getRetrofit(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(urlStorage.getPathApi())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

    public static UserService getUserService(){
        UserService userService = getRetrofit().create(UserService.class);

        return userService;
    }

    public static GroupService getGroupService(){
        GroupService groupService = getRetrofit().create(GroupService.class);

        return groupService;
    }

    public static LessonService getLessonService(){
        LessonService lessonService = getRetrofit().create(LessonService.class);

        return lessonService;
    }

    public static Lesson_attendanceService getLesson_attendanceService(){
        Lesson_attendanceService lesson_attendanceService = getRetrofit().create(Lesson_attendanceService.class);

        return  lesson_attendanceService;
    }

    public static UniversityService getUniversityService(){
        UniversityService universityService = getRetrofit().create(UniversityService.class);

        return universityService;
    }

}
