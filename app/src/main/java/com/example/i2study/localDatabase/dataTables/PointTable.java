package com.example.i2study.localDatabase.dataTables;

import com.example.i2study.localDatabase.dataModels.PointModel;

public class PointTable {
    private static final PointModel[] points = {
            PointModel.createMark(1, "Рубежный контроль 1", 75, 100, 1, 1),
            PointModel.createMark(2, "Рубежный контроль 2", 85, 100, 1, 1),
            PointModel.createMark(3, "Рубежный контроль 1", 100, 100, 1, 2),
            PointModel.createMark(4, "Рубежный контроль 2", 45, 100, 1, 2),
            PointModel.createMark(5, "Рубежный контроль 1", 15, 25, 1, 3),
            PointModel.createMark(6, "Рубежный контроль 1", 75, 100, 4, 1),
            PointModel.createMark(7, "Рубежный контроль 2", 85, 100, 4, 1),
            PointModel.createMark(8, "Рубежный контроль 1", 100, 100, 4, 2),
            PointModel.createMark(9, "Рубежный контроль 2", 45, 100, 4, 2),
            PointModel.createMark(10, "Рубежный контроль 1", 15, 25, 4, 3),
    };

    public static PointModel[] getPoints(){
        return points;
    }
}
