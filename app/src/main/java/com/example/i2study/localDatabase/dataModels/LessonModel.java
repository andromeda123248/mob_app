package com.example.i2study.localDatabase.dataModels;

public class LessonModel {
    private int id;
    private String room;
    private String day;
    private String start_time;
    private boolean is_online;
    private String lesson_type;
    private int userId;
    private int groupId;
    private int subjectId;

    public static LessonModel createLesson(int id, String room, String day, String start_time, boolean is_online, String lesson_type, int userId, int groupId, int subjectId){
        LessonModel lessonModel = new LessonModel();

        lessonModel.setId(id);
        lessonModel.setDay(day);
        lessonModel.setRoom(room);
        lessonModel.setStart_time(start_time);
        lessonModel.setIs_online(is_online);
        lessonModel.setLesson_type(lesson_type);
        lessonModel.setUserId(userId);
        lessonModel.setGroupId(groupId);
        lessonModel.setSubjectId(subjectId);
        return lessonModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public boolean isIs_online() {
        return is_online;
    }

    public void setIs_online(boolean is_online) {
        this.is_online = is_online;
    }

    public String getLesson_type() {
        return lesson_type;
    }

    public void setLesson_type(String lesson_type) {
        this.lesson_type = lesson_type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
