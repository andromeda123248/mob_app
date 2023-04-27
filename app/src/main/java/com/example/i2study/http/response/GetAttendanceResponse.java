package com.example.i2study.http.response;

import com.google.gson.JsonObject;

import java.util.List;

public class GetAttendanceResponse {

    private List<JsonObject> attend;
    private int at_count;

    public List<JsonObject> getAttend() {
        return attend;
    }

    public void setAttend(List<JsonObject> attend) {
        this.attend = attend;
    }

    public int getAt_count() {
        return at_count;
    }

    public void setAt_count(int at_count) {
        this.at_count = at_count;
    }
}
