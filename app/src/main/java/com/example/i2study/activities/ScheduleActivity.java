package com.example.i2study.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.i2study.R;
import com.example.i2study.SessionManager;
import com.example.i2study.http.ApiClient;
import com.example.i2study.http.response.GetLessonResponse;
import com.example.i2study.localDatabase.dataModels.LessonModel;
import com.example.i2study.localDatabase.dataModels.SubjectModel;
import com.example.i2study.localDatabase.dataTables.LessonTable;
import com.example.i2study.localDatabase.dataTables.SubjectTable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {

    private TextView name;
    private ImageButton imageButtonPoints, imageButtonSchedule, imageButtonActual, imageButtonAttendance, imageButtonProfile;
    private ListView listView;

    private Map<String, Integer> week_days = new HashMap<>();

    private SessionManager sessionManager;

    List<Integer> orderingList = new ArrayList<>();
    List<String> dayList = new ArrayList<>();
    List<String> lessonList = new ArrayList<>();
    List<String> timeList = new ArrayList<>();
    List<String> roomList = new ArrayList<>();
    List<String> numberList = new ArrayList<>();

    final String ATTRIBUTE_NAME_DAY = "days";
    final String ATTRIBUTE_NAME_LESSON = "lessons";
    final String ATTRIBUTE_NAME_TIME = "times";
    final String ATTRIBUTE_NAME_ROOM = "rooms";
    final String ATTRIBUTE_NAME_NUMBER = "numbers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        imageButtonPoints = findViewById(R.id.imageButtonPoints);
        imageButtonSchedule = findViewById(R.id.imageButtonSchedule);
        imageButtonActual = findViewById(R.id.imageButtonActual);
        imageButtonAttendance = findViewById(R.id.imageButtonAttendance);
        imageButtonProfile = findViewById(R.id.imageButtonProfile);


        {
            week_days.put("Monday", 1);
            week_days.put("Tuesday", 2);
            week_days.put("Wednesday", 3);
            week_days.put("Thursday", 4);
            week_days.put("Friday", 5);
            week_days.put("Saturday", 6);
            week_days.put("Sunday", 7);
        }

        sessionManager = new SessionManager(getApplicationContext());

        generateDays();

        name = findViewById(R.id.textViewName);
        String txtName =  sessionManager.getUsername();
        name.setText(txtName);

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

    public void generateDays(){

        LessonModel[] lesson_list = LessonTable.getLessons();
        SubjectModel[] subject_list = SubjectTable.getSubjects();

        // Перекладываем нужные данные из массива уроков по спискам
        for (LessonModel lessonModel : lesson_list) {
            if (sessionManager.getRole().equals("STUDENT") && lessonModel.getGroupId() == sessionManager.getUserGroupId()
                    || sessionManager.getRole().equals("TEACHER") && lessonModel.getUserId() == sessionManager.getUserId()){
                dayList.add(lessonModel.getDay());

                for(SubjectModel subjectModel : subject_list){
                    if(subjectModel.getId() == lessonModel.getSubjectId()){
                        lessonList.add(subjectModel.getName());
                    }
                }

                timeList.add(lessonModel.getStart_time());
                roomList.add(lessonModel.getRoom());
            }
        }

        // Подготавливаем списки для нумерации и сортировки по времени начала урока
        for (int i = 0; i < timeList.size(); i++){
            numberList.add(String.valueOf(i+1));
            orderingList.add(week_days.get(dayList.get(i))*24*60 +
                    Integer.valueOf(timeList.get(i).split(":")[0])*60 +
                    Integer.valueOf(timeList.get(i).split(":")[1]));
        }

        //Сортируем данные в порядке возрастания времени
        boolean isSorted = false;
        int buf_int;
        String buf_str;
        while(!isSorted) {
            isSorted = true;
            for (int i = 0; i < timeList.size()-1; i++) {
                if(orderingList.get(i) > orderingList.get(i+1)){
                    isSorted = false;

                    buf_str = dayList.get(i);
                    dayList.set(i, dayList.get(i+1));
                    dayList.set(i+1, buf_str);

                    buf_str = lessonList.get(i);
                    lessonList.set(i, lessonList.get(i+1));
                    lessonList.set(i+1, buf_str);

                    buf_str = timeList.get(i);
                    timeList.set(i, timeList.get(i+1));
                    timeList.set(i+1, buf_str);

                    buf_str = roomList.get(i);
                    roomList.set(i, roomList.get(i+1));
                    roomList.set(i+1, buf_str);

                    buf_int = orderingList.get(i);
                    orderingList.set(i, orderingList.get(i+1));
                    orderingList.set(i+1, buf_int);
                }
            }
        }
        //Переопределение номеров и вывод дней только в начале
        String day_buf = dayList.get(0);
        String num_buf = numberList.get(0);
        for (int i = 1; i < timeList.size(); i++){
            if (dayList.get(i).equals(day_buf)){
                dayList.set(i, "");
                numberList.set(i, String.valueOf(Integer.parseInt(num_buf) + 1));
                num_buf = String.valueOf(Integer.parseInt(num_buf) + 1);
            } else {
                day_buf = dayList.get(i);
                numberList.set(i, String.valueOf(1));
                num_buf = "1";
            }
        }


        //Упаковываем данные в Адаптер
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                lessonList.size());
        Map<String, Object> m;
        for (int i = 0; i < lessonList.size(); i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_LESSON, lessonList.get(i));
            m.put(ATTRIBUTE_NAME_TIME, timeList.get(i));
            m.put(ATTRIBUTE_NAME_ROOM, roomList.get(i));
            m.put(ATTRIBUTE_NAME_NUMBER, numberList.get(i));
            m.put(ATTRIBUTE_NAME_DAY, dayList.get(i));
            data.add(m);
        }
        String[] from = {ATTRIBUTE_NAME_LESSON, ATTRIBUTE_NAME_TIME, ATTRIBUTE_NAME_ROOM, ATTRIBUTE_NAME_NUMBER, ATTRIBUTE_NAME_DAY};
        int[] to = {R.id.textViewLesson, R.id.textViewTime, R.id.textViewRoom, R.id.textViewNumber, R.id.textViewDay};

        SimpleAdapter adapter = new SimpleAdapter(ScheduleActivity.super.getBaseContext(), data, R.layout.schedule_item, from, to);
        //Выводим список уроков
        listView = findViewById(R.id.listViewSchedule);
        listView.setAdapter(adapter);
    }
}