package com.example.i2study.http.service;

import com.example.i2study.http.response.GetAttendanceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Lesson_attendanceService {

    @GET("lesson_attendance")
    Call<GetAttendanceResponse> getAttendanceByLessonId(@Query("lessonId") int lessonId);
}
