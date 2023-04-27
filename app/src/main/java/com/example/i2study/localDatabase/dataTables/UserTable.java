package com.example.i2study.localDatabase.dataTables;

import com.example.i2study.localDatabase.dataModels.UserModel;


public class UserTable {
    private static final UserModel[] users = {
            UserModel.createUser(1, "user-login@gmail.com", "1111", "Трофимов Анатолий", "STUDENT", 1, "student.png", 90),
            UserModel.createUser(2, "teacher-login@gmail.com", "1111", "Ассель Романова", "TEACHER", 3, "teacher.png", 97),
            UserModel.createUser(3, "admin-login@gmail.com","1111", "Афанасьев Роман", "ADMIN", 1, "pic", 15),
            UserModel.createUser(4, "user-second@gmail.com", "1111", "Разумов Андрей", "STUDENT", 1, "student.png", 25),
    };


    public static UserModel[] getUsers() {
        return users;
    }
}
