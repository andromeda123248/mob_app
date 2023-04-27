package com.example.i2study.localDatabase.dataTables;

import com.example.i2study.localDatabase.dataModels.LessonModel;

public class LessonTable {
    private static final LessonModel[] lessons = {
            LessonModel.createLesson(1, "123A", "Monday", "8:20", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(2, "3574-B", "Monday", "10:15", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(3, "123A", "Tuesday", "8:20", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(4, "3574-B", "Tuesday", "10:15", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(5, "123A", "Wednesday", "8:20", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(6, "3574-B", "Wednesday", "10:15", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(7, "123A", "Thursday", "8:20", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(8, "3574-B", "Thursday", "10:15", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(9, "123A", "Friday", "8:20", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(10, "3574-B", "Friday", "10:15", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(11, "123A", "Saturday", "8:20", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(12, "3574-B", "Saturday", "10:15", false, "Лекция", 2, 1, 1),
            LessonModel.createLesson(13, "3574-B", "Sunday", "13:45", false, "Лекция", 2, 1, 1),

    };

    public static LessonModel[] getLessons() {
        return lessons;
    }
}
