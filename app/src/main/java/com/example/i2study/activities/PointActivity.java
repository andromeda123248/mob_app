package com.example.i2study.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import com.example.i2study.R;
import com.example.i2study.SessionManager;
import com.example.i2study.localDatabase.dataModels.PointModel;
import com.example.i2study.localDatabase.dataModels.SubjectModel;
import com.example.i2study.localDatabase.dataTables.PointTable;
import com.example.i2study.localDatabase.dataTables.SubjectTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PointActivity extends AppCompatActivity {

    private TextView name, perCent;
    private ImageButton imageButtonPoints, imageButtonSchedule, imageButtonActual, imageButtonAttendance, imageButtonProfile;
    private Spinner spinnerSubj;
    private ListView listView;
    private ProgressBar chart;

    List<String> markNameList = new ArrayList<>();
    List<String> markResultList = new ArrayList<>();

    final String ATTRIBUTE_NAME_MARK = "marks";
    final String ATTRIBUTE_NAME_RESULTS = "results";

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        imageButtonPoints = findViewById(R.id.imageButtonPoints);
        imageButtonSchedule = findViewById(R.id.imageButtonSchedule);
        imageButtonActual = findViewById(R.id.imageButtonActual);
        imageButtonAttendance = findViewById(R.id.imageButtonAttendance);
        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        spinnerSubj = findViewById(R.id.spinnerChooseSub);
        chart = findViewById(R.id.progressBarChart);

        sessionManager = new SessionManager(getApplicationContext());

        perCent = findViewById(R.id.textViewPerCent);
        perCent.setText(sessionManager.getAttendancePerCent().toString() + "%");
        
        chart.setProgress(sessionManager.getAttendancePerCent());

        name = findViewById(R.id.textViewName);
        String txtName = sessionManager.getUsername();
        name.setText(txtName);

        SubjectModel[] subject_list = SubjectTable.getSubjects();
        String[] subject_name_list = new String[subject_list.length];
        for(int i = 0; i < subject_list.length; i++){
            subject_name_list[i] = subject_list[i].getName();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subject_name_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubj.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем id выбранного предмета
                String item = (String)parent.getItemAtPosition(position);
                for (SubjectModel subjectModel : subject_list) {
                    if (subjectModel.getName().equals(item)) {
                        showMarks(subjectModel.getId());
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        spinnerSubj.setOnItemSelectedListener(itemSelectedListener);

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

    public void showMarks(int subId){
        PointModel[] point_list = PointTable.getPoints();

        //clear mark lists
        markNameList.clear();
        markResultList.clear();

        //collect marks of this student and of this subject
        for(PointModel point : point_list){
            if(point.getUserId() == sessionManager.getUserId() && point.getSubjectId() == subId){
                markNameList.add(point.getName());
                markResultList.add(Integer.toString(point.getScore()) + "/" + Integer.toString(point.getMax_score()));
            }
        }

        //Упаковываем данные в Адаптер
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                markNameList.size());
        Map<String, Object> m;
        for (int i = 0; i < markNameList.size(); i++) {
            m = new HashMap<String, Object>();
            m.put(ATTRIBUTE_NAME_MARK, markNameList.get(i));
            m.put(ATTRIBUTE_NAME_RESULTS, markResultList.get(i));
            data.add(m);
        }
        String[] from = {ATTRIBUTE_NAME_MARK, ATTRIBUTE_NAME_RESULTS};
        int[] to = {R.id.textViewWorkName, R.id.textViewNumberOfPoints};

        SimpleAdapter adapter = new SimpleAdapter(PointActivity.super.getBaseContext(), data, R.layout.point_item, from, to);
        //Выводим список уроков
        listView = findViewById(R.id.listViewAttendance);
        listView.setAdapter(adapter);

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
}