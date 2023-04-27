package com.example.i2study;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("AppKey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //  Is user authorized
    public void setIsAuthorized(boolean isAuthorized){
        editor.putBoolean("KEY_IS_AUTHORIZED",isAuthorized);
        editor.commit();
    }

    public boolean getIsAuthorised(){
        return sharedPreferences.getBoolean("KEY_IS_AUTHORIZED", false);
    }

    // USER ID
    public void setUserId(Integer userId){
        editor.putInt("KEY_USERID",userId);
        editor.commit();
    }

    public Integer getUserId(){
        return sharedPreferences.getInt("KEY_USERID", 0);
    }

    // USER LOGIN
    public void setLogin(String login){
        editor.putString("KEY_LOGIN",login);
        editor.commit();
    }

    public String getLogin(){
        return sharedPreferences.getString("KEY_LOGIN", "");
    }


    // USER NAME
    public void setUsername(String username){
        editor.putString("KEY_USERNAME",username);
        editor.commit();
    }

    public String getUsername(){
        return sharedPreferences.getString("KEY_USERNAME", "");
    }


    // USER ROLE
    public void setRole(String role){
        editor.putString("KEY_ROLE",role);
        editor.commit();
    }

    public String getRole(){
        return sharedPreferences.getString("KEY_ROLE", "");
    }


    // USER GROUP ID
    public void setUserGroupId(Integer userGroupId){
        editor.putInt("KEY_USER_GROUP_ID",userGroupId);
        editor.commit();
    }

    public Integer getUserGroupId(){
        return sharedPreferences.getInt("KEY_USER_GROUP_ID", 0);
    }


    public void setUserGroupName(String userGroupName){
        editor.putString("KEY_USER_GROUP_NAME", userGroupName);
        editor.commit();
    }

    public String getUserGroupName(){
        return  sharedPreferences.getString("KEY_USER_GROUP_NAME", "");
    }

    public void setUserProfilePic(String userProfilePic){
        editor.putString("KEY_USER_PROFILE_PIC", userProfilePic);
        editor.commit();
    }

    public String getUserProfilePic(){
        return sharedPreferences.getString("KEY_USER_PROFILE_PIC", "");
    }

    public void setToken(String token){
        editor.putString("KEY_TOKEN", token);
        editor.commit();
    }

    public String getToken(){
        return  sharedPreferences.getString("KEY_TOKEN", "");
    }

    public void setNameBuffer(String nameBuffer){
        editor.putString("KEY_NAME_BUFF", nameBuffer);
        editor.commit();
    }

    public String getNameBuffer(){
        return sharedPreferences.getString("KEY_NAME_BUFF", "");
    }

    public void setUserUniversityGeo(String geo) {
        editor.putString("KEY_GEO", geo);
        editor.commit();
    }

    public String getUserUniversityGeo(){
        return sharedPreferences.getString("KEY_GEO", "");
    }

    public void setLessonCode(Integer code){
        editor.putInt("LESSON_START_CODE", code);
        editor.commit();
    }

    public Integer getLessonCode(){
        return sharedPreferences.getInt("LESSON_START_CODE", -1);
    }

    public void setAttendancePerCent(Integer perCent){
        editor.putInt("ATTENDANCE_PER_CENT", perCent);
        editor.commit();
    }

    public Integer getAttendancePerCent(){
        return sharedPreferences.getInt("ATTENDANCE_PER_CENT", 0);
    }

    public void setCodeCreationTime(Integer perCent){
        editor.putInt("CODE_CREATION_TIME", perCent);
        editor.commit();
    }

    public Integer getCodeCreationTime(){
        return sharedPreferences.getInt("CODE_CREATION_TIME", 0);
    }
}
