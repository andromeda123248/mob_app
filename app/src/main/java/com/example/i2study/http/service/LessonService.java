package com.example.i2study.http.service;

import com.example.i2study.http.request.AddLessonRequest;
import com.example.i2study.http.request.MarkAttendanceRequest;
import com.example.i2study.http.request.StartLessonRequest;
import com.example.i2study.http.response.AddLessonResponse;
import com.example.i2study.http.response.GetLessonResponse;
import com.example.i2study.http.response.MarkAttendanceResponse;
import com.example.i2study.http.response.StartLessonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LessonService {

    @POST("lesson")
    Call<AddLessonResponse> createLesson(@Body AddLessonRequest addLessonRequest, @Header("Authorization") String token);

    @GET("lesson")
    Call<List<GetLessonResponse>> getActualLessonsByGroupId(
            @Query("groupId") int groupId,
            @Query("day") String day
    );

    @GET("lesson")
    Call<List<GetLessonResponse>> getAllLessonsByGroupId(
            @Query("groupId") int groupId
    );

    @GET("lesson")
    Call<List<GetLessonResponse>> getActualLessonsByUserId(
            @Query("day") String day,
            @Query("userId") int userId
    );

    @GET("lesson")
    Call<List<GetLessonResponse>> getAllLessonsByUserId(
            @Query("userId") int userId
    );

    @POST("lesson/start")
    Call<Integer> startLesson(@Body StartLessonRequest startLessonRequest, @Header("Authorization") String token);

    @POST("lesson/mark-attendance")
    Call<MarkAttendanceResponse> markMyAttendance(@Body MarkAttendanceRequest markAttendanceRequest);
}
