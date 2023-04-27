package com.example.i2study.localDatabase.dataTables;

import com.example.i2study.localDatabase.dataModels.SubjectModel;

public class SubjectTable {
    private static final SubjectModel[] subjects = {
            SubjectModel.createSubject(1, "Математика", 1),
            SubjectModel.createSubject(2, "Физика", 1),
            SubjectModel.createSubject(3, "Информатика", 1),
    };

    public static SubjectModel[] getSubjects(){
        return subjects;
    }
}
