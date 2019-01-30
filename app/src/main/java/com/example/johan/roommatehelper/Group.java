package com.example.johan.roommatehelper;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
/*
Johan Diepstraten 10774920
in this class everything that belongs in the group is defined.
*/
public class Group implements Serializable {
    private int groupId;
    private String groupName;
    private String groupPassword;
    private ArrayList<String> groceryList;
    private ArrayList<String> groupMembers;
    private ArrayList<Task> groupTasks;
    private ArrayList<Integer> memberIds;

//    Initiate class object for group when group is created.
    public Group(int groupId, String groupName, String groupPassword, ArrayList<String> groceryList,
                 ArrayList<String> groupMembers, ArrayList<Task> groupTasks,
                 ArrayList<Integer> memberIds) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupPassword = groupPassword;
        this.groceryList = groceryList;
        this.groupMembers = groupMembers;
        this.groupTasks = groupTasks;
        this.memberIds = memberIds;
    }

//    Initiate getters.
    public int getGroupId() { return groupId; }

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

    public ArrayList<Integer> getMemberIds() { return memberIds; }

    //    Initiate setters.
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
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
    public void setMemberIds(ArrayList<Integer> memberIds) {
        this.memberIds = memberIds;
    }
}
