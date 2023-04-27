package com.example.i2study.localDatabase.dataModels;

public class PointModel {
    private int id;
    private String name;
    private int score;
    private int max_score;
    private int userId;
    private int subjectId;

    public static PointModel createMark(int id, String name, int score, int max_score, int userId, int subjectId){
        PointModel mark = new PointModel();

        mark.setId(id);
        mark.setName(name);
        mark.setScore(score);
        mark.setMax_score(max_score);
        mark.setUserId(userId);
        mark.setSubjectId(subjectId);

        return mark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMax_score() {
        return max_score;
    }

    public void setMax_score(int max_score) {
        this.max_score = max_score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }
}
