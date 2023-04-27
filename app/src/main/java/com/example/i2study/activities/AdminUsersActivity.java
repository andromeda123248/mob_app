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
import com.example.i2study.http.request.RegistrationRequest;
import com.example.i2study.http.response.RegistrationResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminUsersActivity extends AppCompatActivity {

    private EditText editTextLogin, editTextPassword, editTextRole, editTextName, editTextGroupNumber;
    private Button buttonRegIn, buttonLessons;
    private ImageButton imageButtonProfile;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_users);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextRole = findViewById(R.id.editTextRole);
        editTextName = findViewById(R.id.editTextName);
        editTextGroupNumber = findViewById(R.id.editTextGroupNumber);
        imageButtonProfile = findViewById(R.id.imageButtonProfile);
        buttonRegIn = findViewById(R.id.buttonRegIn);
        buttonLessons = findViewById(R.id.buttonGoToLessons);

        //Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        imageButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProfile(view);
            }
        });

        buttonLessons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAdminLessons(view);
            }
        });

        buttonRegIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regIn(view);
            }
        });
    }

    public void goToProfile(View v){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void goToAdminLessons(View v){
        Intent intent = new Intent(this, AdminLessonsActivity.class);
        startActivity(intent);
    }

    public void regIn(View v){

        //Set username, password and role
        RegistrationRequest registrationRequest = new RegistrationRequest();
        registrationRequest.setLogin(editTextLogin.getText().toString());
        registrationRequest.setPassword(editTextPassword.getText().toString());
        registrationRequest.setRole(editTextRole.getText().toString());
        registrationRequest.setName(editTextName.getText().toString());
        registrationRequest.setGroupId(Integer.parseInt(editTextGroupNumber.getText().toString()));

        Call<RegistrationResponse> registrationResponseCall = ApiClient.getUserService().userRegistration(registrationRequest);
        registrationResponseCall.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {

                if(response.isSuccessful()){
                    Toast.makeText(AdminUsersActivity.this, "Успешно добавлен пользователь", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(AdminUsersActivity.this, "Не получилось добавить пользователя", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                Toast.makeText(AdminUsersActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}