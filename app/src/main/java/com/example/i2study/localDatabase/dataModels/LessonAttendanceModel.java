package com.example.i2study.localDatabase.dataModels;

public class LessonAttendanceModel {
    private int id;
    private int userId;
    private int lessonId;
    private String attendance;

    public static LessonAttendanceModel createPresenceRecording(int id, int userId, int lessonId, String attendance){
        LessonAttendanceModel record = new LessonAttendanceModel();

        record.setAttendance(attendance);
        record.setLessonId(lessonId);
        record.setId(id);
        record.setUserId(userId);

        return record;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
