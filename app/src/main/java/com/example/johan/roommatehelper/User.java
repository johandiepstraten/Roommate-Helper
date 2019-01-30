/*
Johan Diepstraten 10774920
This class defines all attributes of a user.
*/
package com.example.johan.roommatehelper;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String user_name;
    private String user_password;
    private int group_id;

//    Initiate class for user everytime someone creates an account.
    public User(int userId, String user_name, String user_password, int group_id)  {
        this.userId = userId;
        this.user_name = user_name;
        this.user_password = user_password;
        this.group_id = group_id;
    }

//    Initiate getters.
    public int getUserId() { return userId; }
    public String getUser_name() {
        return user_name;
    }
    public String getUser_password() {
        return user_password;
    }
    public int getGroup_id() {
        return group_id;
    }

//    Initiate setters.
    public void setUserId(int userId) { this.userId = userId; }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}