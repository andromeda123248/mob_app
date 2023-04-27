package com.example.i2study.localDatabase.dataModels;


public class UserModel {
    private int id;
    private String login;
    private String name;
    private String role;
    private int groupId;
    private String password;
    private String profile_pic;
    private int att_per_cent;

    public static UserModel createUser(int id, String login, String password, String name, String role, int groupId, String profile_pic, int att_per_cent){
        UserModel user = new UserModel();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setRole(role);
        user.setGroupId(groupId);
        user.setProfile_pic(profile_pic);
        user.setAtt_per_cent(att_per_cent);
        return user;
    }

    public int getAtt_per_cent() {
        return att_per_cent;
    }

    public void setAtt_per_cent(int att_per_cent) {
        this.att_per_cent = att_per_cent;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }
}
