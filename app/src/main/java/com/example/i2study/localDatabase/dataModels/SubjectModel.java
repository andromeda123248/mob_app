package com.example.i2study.localDatabase.dataModels;

public class SubjectModel {
    private int id;
    private String name;
    private int group_id;

    public static SubjectModel createSubject(int id, String name, int group_id){
        SubjectModel subjectModel = new SubjectModel();

        subjectModel.setId(id);
        subjectModel.setGroup_id(group_id);
        subjectModel.setName(name);
        return subjectModel;
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

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}
