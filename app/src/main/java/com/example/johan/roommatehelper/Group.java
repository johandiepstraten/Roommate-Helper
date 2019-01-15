package com.example.johan.roommatehelper;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Group implements Serializable {
    private String groupName;
    private String groupPassword;
    private ArrayList<String> groceryList;
    private ArrayList<String> groupMembers;
    private ArrayList<Task> groupTasks;

//    Initiate class object for group when group is created.
    public Group(String groupName, String groupPassword, ArrayList<String> groceryList, ArrayList<String> groupMembers, ArrayList<Task> groupTasks) {
        this.groupName = groupName;
        this.groupPassword = groupPassword;
        this.groceryList = groceryList;
        this.groupMembers = groupMembers;
        this.groupTasks = groupTasks;
    }

//    Initiate getters

    public String getGroupName() {
        return groupName;
    }

    public String getGroupPassword() {
        return groupPassword;
    }

    public ArrayList<String> getGroceryList() {
        return groceryList;
    }

    public ArrayList<String> getGroupMembers() {
        return groupMembers;
    }

    public ArrayList<Task> getGroupTasks() {
        return groupTasks;
    }

//    Initiate setters

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupPassword(String groupPassword) {
        this.groupPassword = groupPassword;
    }

    public void setGroceryList(ArrayList<String> groceryList) {
        this.groceryList = groceryList;
    }

    public void setGroupMembers(ArrayList<String> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public void setGroupTasks(ArrayList<Task> groupTasks) {
        this.groupTasks = groupTasks;
    }
}
