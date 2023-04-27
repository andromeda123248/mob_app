package com.example.i2study.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.i2study.R;
import com.example.i2study.SessionManager;
import com.example.i2study.http.ApiClient;
import com.example.i2study.http.request.AddLessonRequest;
import com.example.i2study.http.response.AddLessonResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLessonsActivity extends AppCompatActivity {

    private ImageButton imageButtonProfile;
    private Button buttonUsers, buttonAddLesson;
    private EditText editTextRoom, editTextDay, editTextStartTime,
            editTextIsOnline, editTextLessonType, editTextUserId,
            editTextGroupId, editTextSubjectId;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lessons);

        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        buttonUsers = findViewById(R.id.buttonGoToUsers);
        buttonAddLesson = findViewById(R.id.buttonAddLesson);
        editTextRoom = findViewById(R.id.editTextRoom);
        editTextDay = findViewById(R.id.editTextDay);
        editTextStartTime = findViewById(R.id.editTextStartTime);
        editTextIsOnline = findViewById(R.id.editTextIsOnline);
        editTextLessonType = findViewById(R.id.editTextLessonType);
        editTextUserId = findViewById(R.id.editTextUserId);
        editTextGroupId = findViewById(R.id.editTextGroupId);
        editTextSubjectId = findViewById(R.id.editTextSubjectId);

        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile(view);
            }
        });

        buttonUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAdminUsers(view);
            }
        });

        buttonAddLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLesson(view);
            }
        });
    }

    public void goToProfile(View v){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void goToAdminUsers(View v){
        Intent intent = new Intent(this, AdminUsersActivity.class);
        startActivity(intent);
    }

    public void addLesson(View v){

        sessionManager = new SessionManager(getApplicationContext());

        // set data for request
        AddLessonRequest addLessonRequest = new AddLessonRequest();
        addLessonRequest.setRoom(editTextRoom.getText().toString());
        addLessonRequest.setDay(editTextDay.getText().toString());
        addLessonRequest.setStart_time(editTextStartTime.getText().toString());
        addLessonRequest.setIs_online(Boolean.valueOf(editTextIsOnline.getText().toString()));
        addLessonRequest.setLesson_type(editTextLessonType.getText().toString());
        addLessonRequest.setUserId(Integer.parseInt(editTextUserId.getText().toString()));
        addLessonRequest.setGroupId(Integer.parseInt(editTextGroupId.getText().toString()));
        addLessonRequest.setSubjectId(Integer.parseInt(editTextSubjectId.getText().toString()));

        Call<AddLessonResponse> addLessonResponseCall = ApiClient.getLessonService().createLesson(addLessonRequest, "Bearer " + sessionManager.getToken());
        addLessonResponseCall.enqueue(new Callback<AddLessonResponse>() {
            @Override
            public void onResponse(Call<AddLessonResponse> call, Response<AddLessonResponse> response) {

                if(response.isSuccessful()){
                    Toast.makeText(AdminLessonsActivity.this, "Успешно добавлен урок", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AdminLessonsActivity.this, "Не получилось добавить урок", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddLessonResponse> call, Throwable t) {
                Toast.makeText(AdminLessonsActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}