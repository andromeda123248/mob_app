package com.example.i2study.http.service;

import com.example.i2study.http.response.GetUniversityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UniversityService {

    @GET("university")
    Call<List<GetUniversityResponse>> getUniversityById(@Query("id") int id);
}
