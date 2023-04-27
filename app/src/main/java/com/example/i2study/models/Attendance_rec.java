package com.example.i2study.models;

import android.util.Log;
import android.widget.Toast;

import com.example.i2study.activities.AttendanceTeacherActivity;
import com.example.i2study.http.ApiClient;
import com.example.i2study.http.response.GetUserResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Attendance_rec {
    public int id;
    public String attendance;
    public int userId;
    public String userName;

    public static Attendance_rec getAttendance_rec(JsonObject jsonString){
        Attendance_rec atten = new Gson().fromJson(jsonString, Attendance_rec.class);
        return atten;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
