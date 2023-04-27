package com.example.i2study.localDatabase.dataModels;

public class GroupModel {
    private int id;
    private String name;

    public static GroupModel createGroup(int id, String name){
        GroupModel groupModel = new GroupModel();

        groupModel.setId(id);
        groupModel.setName(name);
        return groupModel;
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

}
