package com.example.johan.roommatehelper;

import java.io.Serializable;

public class Task implements Serializable {
    private String taskName;
    private String taskDescription;
    private int taskDays;
    private String responsibleUser;

//    Initiate class for task when new task is added.
    public Task(String taskName, String taskDescription, int taskDays, String responsibleUser)  {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDays = taskDays;
        this.responsibleUser = responsibleUser;
    }
//    Initiate getters
    public String getTaskName() {

        return taskName;
    }

    public String getTaskDescription() {

        return taskDescription;
    }

    public int getTaskDays() {

        return taskDays;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

//    Initiate setters
    public void setTaskName(String taskName) {

        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskDays(int taskDays) {

        this.taskDays = taskDays;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }
}
