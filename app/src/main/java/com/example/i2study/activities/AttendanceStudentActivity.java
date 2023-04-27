package com.example.i2study.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i2study.R;
import com.example.i2study.SessionManager;
import com.example.i2study.http.ApiClient;
import com.example.i2study.http.request.MarkAttendanceRequest;
import com.example.i2study.http.request.StartLessonRequest;
import com.example.i2study.http.response.GetLessonResponse;
import com.example.i2study.http.response.MarkAttendanceResponse;
import com.example.i2study.localDatabase.dataModels.LessonAttendanceModel;
import com.example.i2study.localDatabase.dataModels.LessonModel;
import com.example.i2study.localDatabase.dataModels.UserModel;
import com.example.i2study.localDatabase.dataTables.LessonAttendanceTable;
import com.example.i2study.localDatabase.dataTables.LessonTable;
import com.example.i2study.localDatabase.dataTables.UserTable;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttendanceStudentActivity extends AppCompatActivity{
    protected Context context;

    private TextView name;
    private ImageButton imageButtonPoints, imageButtonSchedule, imageButtonActual, imageButtonAttendance, imageButtonProfile;
    private EditText editTextCode;
    private Button btnMarkAttendance;

    private SessionManager sessionManager;

    int orderingLesson;
    private String timeLesson;
    private int nowLessonId;
    private int startLessonRange = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_student);

        imageButtonPoints = findViewById(R.id.imageButtonPoints);
        imageButtonSchedule = findViewById(R.id.imageButtonSchedule);
        imageButtonActual = findViewById(R.id.imageButtonActual);
        imageButtonAttendance = findViewById(R.id.imageButtonAttendance);
        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        editTextCode = findViewById(R.id.editTextEnterCode);
        btnMarkAttendance = findViewById(R.id.buttonEnterCode);


        sessionManager = new SessionManager(getApplicationContext());

        name = findViewById(R.id.textViewName);
        String txtName = sessionManager.getUsername();
        name.setText(txtName);


        btnMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                markAttendance();
            }
        });

        imageButtonPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPoints(view);
            }
        });
        imageButtonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSchedule(view);
            }
        });
        imageButtonActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActual(view);
            }
        });
        imageButtonAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAttendance(view);
            }
        });
        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile(view);
            }
        });
    }


    public void goToPoints(View v) {
        Intent intent = new Intent(this, PointActivity.class);
        startActivity(intent);
    }

    public void goToSchedule(View v) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }

    public void goToActual(View v) {
        Intent intent = new Intent(this, ActualActivity.class);
        startActivity(intent);
    }

    public void goToAttendance(View v) {
        if (sessionManager.getRole().equals("STUDENT")) {
            Intent intent = new Intent(this, AttendanceStudentActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AttendanceTeacherActivity.class);
            startActivity(intent);
        }
    }

    public void goToProfile(View v) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public static String getDayStringOld(Date date, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        return formatter.format(date);
    }

    public static String getTimeStringOld(Date date) {
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }

    public void markAttendance(){

        LessonModel[] lesson_list = LessonTable.getLessons();
        UserModel[] user_list = UserTable.getUsers();

        // Узнаем, какой урок сейчас идет
        nowLessonId = -1;
        int nowTime = Integer.parseInt(getTimeStringOld(new Date(System.currentTimeMillis())).split(":")[0])*60 +
                Integer.parseInt(getTimeStringOld(new Date(System.currentTimeMillis())).split(":")[1]);
        for (LessonModel lessonModel : lesson_list) {
            // if lesson belongs to current group and is today
            if(lessonModel.getGroupId() == sessionManager.getUserGroupId() && lessonModel.getDay().equals(getDayStringOld(new Date(System.currentTimeMillis()), Locale.ENGLISH))){
                timeLesson = lessonModel.getStart_time();
                orderingLesson = Integer.parseInt(timeLesson.split(":")[0]) * 60 + Integer.parseInt(timeLesson.split(":")[1]);

                // if lesson start is close to now
                if (Math.abs(nowTime - orderingLesson) < startLessonRange) {
                    nowLessonId = lessonModel.getId();
                }
            }
        }

        // attempt to mark attendance
        if (nowLessonId > 0){
            if(editTextCode.getText().toString().equals(sessionManager.getLessonCode().toString()) && sessionManager.getLessonCode() != -1){
                LessonAttendanceTable.attendanceList.add(LessonAttendanceModel.createPresenceRecording(1, sessionManager.getUserId(), nowLessonId, "ПРИСУТСТВУЕТ"));
                Toast.makeText(AttendanceStudentActivity.this, "Вы отмечены", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(AttendanceStudentActivity.this, "WRONG CODE", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(AttendanceStudentActivity.this, "Сейчас не время отмечать посещаемость", Toast.LENGTH_LONG).show();
        }

    }

}