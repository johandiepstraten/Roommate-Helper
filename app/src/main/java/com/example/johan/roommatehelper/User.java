package com.example.johan.roommatehelper;

import java.io.Serializable;

public class User implements Serializable {
    private String user_name, user_password, group_name;

//    Initiate class for user everytime someone creates an account
    public User(String user_name, String user_password, String group_name)  {
        this.user_name = user_name;
        this.user_password = user_password;
        this.group_name = group_name;
    }
//    Initiate getters
    public String getUser_name() {
        return user_name;
    }
    public String getUser_password() {
        return user_password;
    }
    public String getGroup_name() {
        return group_name;
    }
//    Initiate setters
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}