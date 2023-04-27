package com.example.i2study.localDatabase.dataTables;

import com.example.i2study.localDatabase.dataModels.GroupModel;

public class GroupTable {
    private static final GroupModel[] groups = {
            GroupModel.createGroup(1, "P33222"),
            GroupModel.createGroup(2, "P33011"),
            GroupModel.createGroup(3, "Преподаватель"),

    };

    public static GroupModel[] getGroups(){
        return groups;
    }
}
