package com.example.i2study.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.i2study.http.ApiClient;
import com.example.i2study.http.response.GetUniversityResponse;
import com.example.i2study.http.response.GroupByIdResponse;
import com.example.i2study.R;
import com.example.i2study.SessionManager;
import com.example.i2study.localDatabase.dataModels.GroupModel;
import com.example.i2study.localDatabase.dataModels.SubjectModel;
import com.example.i2study.localDatabase.dataModels.UserModel;
import com.example.i2study.localDatabase.dataTables.GroupTable;
import com.example.i2study.localDatabase.dataTables.UserTable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLogin, editTextPassword;
    private Button buttonLogIn;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogIn = findViewById(R.id.buttonLogIn);

        //Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn(view);
            }
        });

        //If user already logged in
        if (sessionManager.getIsAuthorised()){
            //Create Intent (Where to go)
            Intent intentActual = new Intent(this, ActualActivity.class);
            //Go to Actual page
            startActivity(intentActual);
            //finish activity
            finish();
        }
    }

    public void logIn(View v){
        //Create Intent (Where to go)
        Intent intentActual = new Intent(this, ActualActivity.class);
        Intent intentAdmin = new Intent(this, AdminUsersActivity.class);

        UserModel[] user_list = UserTable.getUsers();
        GroupModel[] group_list = GroupTable.getGroups();

        String login = editTextLogin.getText().toString();
        String password = editTextPassword.getText().toString();

        //Go through all users
        for (UserModel userModel : user_list) {

            // if user exists
            if (userModel.getLogin().equals(login) && userModel.getPassword().equals(password)){
                // set user data
                sessionManager.setIsAuthorized(true);
                sessionManager.setLogin(userModel.getLogin());
                sessionManager.setUsername(userModel.getName());
                sessionManager.setUserId(userModel.getId());
                sessionManager.setUserGroupId(userModel.getGroupId());
                sessionManager.setRole(userModel.getRole());
                sessionManager.setUserProfilePic(userModel.getProfile_pic());
                sessionManager.setAttendancePerCent(userModel.getAtt_per_cent());

                for(GroupModel groupModel : group_list){
                    if(groupModel.getId() == sessionManager.getUserGroupId()){
                        sessionManager.setUserGroupName(groupModel.getName());
                    }
                }



                //If user role is admin
                if(sessionManager.getRole().equals("ADMIN")){
                    //Go to Admin page;
                    startActivity(intentAdmin);
                }else{
                    //Go to Actual page;
                    startActivity(intentActual);
                }
                //finish activity
                finish();
                break;
            }
        }
        // if log in failed
        if(!sessionManager.getIsAuthorised()){
            Toast.makeText(MainActivity.this, "This user does not exist", Toast.LENGTH_LONG).show();
        }
    }

}