package com.example.i2study.http.response;


public class GetLessonResponse {

    private int id;
    private String start_time;
    private int subjectId;
    private String room;
    private String day;

    public String getTime() {
        return start_time;
    }

    public void setTime(String time) {
        this.start_time = time;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
