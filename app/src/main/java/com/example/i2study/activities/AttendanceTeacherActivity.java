package com.example.i2study.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i2study.R;
import com.example.i2study.SessionManager;
import com.example.i2study.localDatabase.dataModels.LessonAttendanceModel;
import com.example.i2study.localDatabase.dataModels.LessonModel;
import com.example.i2study.localDatabase.dataModels.UserModel;
import com.example.i2study.localDatabase.dataTables.LessonAttendanceTable;
import com.example.i2study.localDatabase.dataTables.LessonTable;
import com.example.i2study.localDatabase.dataTables.UserTable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class AttendanceTeacherActivity extends AppCompatActivity {

    private TextView name, textViewCode;
    private ImageButton imageButtonPoints, imageButtonSchedule, imageButtonActual, imageButtonAttendance, imageButtonProfile;
    private Button btnStartLesson;
    private ListView studentList;

    private SessionManager sessionManager;

    List<String> names = new ArrayList<>();
    List<Integer> numbers = new ArrayList<>();

    final String ATTRIBUTE_NAME_NAMES = "names";
    final String ATTRIBUTE_NAME_NUMBER = "numbers";

    int orderingLesson;
    private String timeLesson;
    private int nowLessonId;
    private int startLessonRangeMinutes = 15;
    private int codeWorkTimeSeconds = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_teacher);

        imageButtonPoints = findViewById(R.id.imageButtonPoints);
        imageButtonSchedule = findViewById(R.id.imageButtonSchedule);
        imageButtonActual = findViewById(R.id.imageButtonActual);
        imageButtonAttendance = findViewById(R.id.imageButtonAttendance);
        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        textViewCode = findViewById(R.id.textViewCode);
        btnStartLesson = findViewById(R.id.buttonMakeCode);
        btnStartLesson.setEnabled(true);

        sessionManager = new SessionManager(getApplicationContext());

        name = findViewById(R.id.textViewName);
        String txtName = sessionManager.getUsername();
        name.setText(txtName);

        updateCode();
        Log.i("CODE", Integer.toString(sessionManager.getLessonCode()));

        if(sessionManager.getLessonCode() != -1){
            textViewCode.setText(sessionManager.getLessonCode().toString());
        }

        generateAttendingList();

        btnStartLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLesson();
                btnStartLesson.setEnabled(false);
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

    public void goToPoints(View v){
        Intent intent = new Intent(this, PointActivity.class);
        startActivity(intent);
    }
    public void goToSchedule(View v){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
    public void goToActual(View v){
        Intent intent = new Intent(this, ActualActivity.class);
        startActivity(intent);
    }
    public void goToAttendance(View v){
        if (sessionManager.getRole().equals("STUDENT")){
            Intent intent = new Intent(this, AttendanceStudentActivity.class);
            startActivity(intent);
        } else{
            Intent intent = new Intent(this, AttendanceTeacherActivity.class);
            startActivity(intent);
        }
    }
    public void goToProfile(View v){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public static String getDayStringOld(Date date, Locale locale) {
        DateFormat formatter = new SimpleDateFormat("EEEE", locale);
        return formatter.format(date);
    }

    public static String getTimeStringOld(Date date){
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        return formatter.format(date);
    }

    private static int generateCode(){
        int min_code = 1000;
        int max_code = 10000;
        return (int) (Math.random()*(max_code - min_code + 1) + min_code);
    }

    public void updateCode(){

        int nowTime = Integer.parseInt(getTimeStringOld(new Date(System.currentTimeMillis())).split(":")[0])*60 +
                Integer.parseInt(getTimeStringOld(new Date(System.currentTimeMillis())).split(":")[1]);

        if (nowTime - sessionManager.getCodeCreationTime() > codeWorkTimeSeconds / 60){
            sessionManager.setLessonCode(-1);
            sessionManager.setCodeCreationTime(0);
        }
    }

    public void startLesson(){

        LessonModel[] lesson_list = LessonTable.getLessons();

        // Узнаем, какой урок сейчас идет
        nowLessonId = -1;
        int nowTime = Integer.parseInt(getTimeStringOld(new Date(System.currentTimeMillis())).split(":")[0])*60 +
                Integer.parseInt(getTimeStringOld(new Date(System.currentTimeMillis())).split(":")[1]);
        for (LessonModel lessonModel : lesson_list) {
            // if lesson belongs to current teacher and is today
            if(lessonModel.getUserId() == sessionManager.getUserId() && lessonModel.getDay().equals(getDayStringOld(new Date(System.currentTimeMillis()), Locale.ENGLISH))){
                timeLesson = lessonModel.getStart_time();
                orderingLesson = Integer.parseInt(timeLesson.split(":")[0]) * 60 + Integer.parseInt(timeLesson.split(":")[1]);

                // if lesson start is close to now
                if (Math.abs(nowTime - orderingLesson) < startLessonRangeMinutes) {
                    nowLessonId = lessonModel.getId();
                }
            }
        }

        // If there is a lesson to start, generate code
        if (nowLessonId > 0 && sessionManager.getLessonCode() == -1){
            sessionManager.setLessonCode(generateCode());
            textViewCode.setText(sessionManager.getLessonCode().toString());
            sessionManager.setCodeCreationTime(nowTime);

            // set timer to delete code after n min
            Timer timer = new Timer();
            TimerTask task = new TimerTask(){
                @Override
                public void run(){
                    sessionManager.setLessonCode(-1);
                    timer.cancel();
                    Log.i("LESON_CODE", "CODE EXPIRED");
                }
            };

            timer.schedule(task, 1000*codeWorkTimeSeconds);
        } else{
            Toast.makeText(AttendanceTeacherActivity.this, "Сейчас не время начинать урок", Toast.LENGTH_LONG).show();
        }
    }

    public void generateAttendingList(){

        LessonModel[] lesson_list = LessonTable.getLessons();
        UserModel[] user_list = UserTable.getUsers();

        // Узнаем, какой урок сейчас идет
        nowLessonId = -1;
        int nowTime = Integer.parseInt(getTimeStringOld(new Date(System.currentTimeMillis())).split(":")[0])*60 +
                Integer.parseInt(getTimeStringOld(new Date(System.currentTimeMillis())).split(":")[1]);
        for (LessonModel lessonModel : lesson_list) {
            // if lesson belongs to current teacher and is today
            if(lessonModel.getUserId() == sessionManager.getUserId() && lessonModel.getDay().equals(getDayStringOld(new Date(System.currentTimeMillis()), Locale.ENGLISH))){
                timeLesson = lessonModel.getStart_time();
                orderingLesson = Integer.parseInt(timeLesson.split(":")[0]) * 60 + Integer.parseInt(timeLesson.split(":")[1]);

                // if lesson start is close to now
                if (Math.abs(nowTime - orderingLesson) < startLessonRangeMinutes) {
                    nowLessonId = lessonModel.getId();
                }
            }
        }

        // Получаем список присутствующих
        if (nowLessonId > 0){
            List<LessonAttendanceModel> at_list = LessonAttendanceTable.attendanceList;

            // Going through all attendance records
            for(int i = 0; i < at_list.size(); i++){
                // only now lesson
                if(at_list.get(i).getLessonId() == nowLessonId){
                    // Getting user name by id
                    for(UserModel user : user_list){
                        if (user.getId() == at_list.get(i).getUserId()){
                            names.add(user.getName());
                            numbers.add(i+1);
                        }
                    }
                }
            }

            //Упаковываем данные в Адаптер
            ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                    names.size());
            Map<String, Object> m;
            for (int i = 0; i < names.size(); i++) {
                m = new HashMap<String, Object>();
                m.put(ATTRIBUTE_NAME_NAMES, names.get(i));
                m.put(ATTRIBUTE_NAME_NUMBER, numbers.get(i));
                data.add(m);
            }
            String[] from = {ATTRIBUTE_NAME_NAMES, ATTRIBUTE_NAME_NUMBER};
            int[] to = {R.id.textViewStudentName, R.id.textViewNumber};

            SimpleAdapter adapter = new SimpleAdapter(AttendanceTeacherActivity.super.getBaseContext(), data, R.layout.student_item, from, to);

            //Выводим список студентов
            studentList = findViewById(R.id.listViewAttendance);
            studentList.setAdapter(adapter);
        } else{
            Toast.makeText(AttendanceTeacherActivity.this, "Сейчас не время начинать урок", Toast.LENGTH_LONG).show();
        }
    }
}