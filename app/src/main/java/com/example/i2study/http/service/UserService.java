package com.example.i2study.http.service;

import com.example.i2study.http.request.LoginRequest;
import com.example.i2study.http.response.GetUserResponse;
import com.example.i2study.http.response.LoginResponse;
import com.example.i2study.http.request.RegistrationRequest;
import com.example.i2study.http.response.RegistrationResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

        @POST("user/login/")
        Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

        @POST("user/registration/")
        Call<RegistrationResponse> userRegistration(@Body RegistrationRequest registrationRequest);

        @GET("user")
        Call<List<GetUserResponse>> getUserById(@Query("id") int id);
}
