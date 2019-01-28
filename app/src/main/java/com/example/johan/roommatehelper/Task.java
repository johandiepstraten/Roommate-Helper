package com.example.johan.roommatehelper;

import java.io.Serializable;

public class Task implements Serializable {

//    Declare variables to use throughout the class.
    private String taskName;
    private String taskDescription;
    private int taskDays;
    private int responsibleUser;
    private long initialTime;
    private long  finishTime;

//    Initiate class for task when new task is added.
    public Task(String taskName, String taskDescription, int taskDays, int responsibleUser, long initialTime, long finishTime)  {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskDays = taskDays;
        this.responsibleUser = responsibleUser;
        this.initialTime = initialTime;
        this.finishTime = finishTime;
    }
//    Initiate getters.
    public String getTaskName() {
        return taskName;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public int getTaskDays() {
        return taskDays;
    }
    public int getResponsibleUser() {
        return responsibleUser;
    }
    public long getInitialTime() {
        return initialTime;
    }
    public long getFinishTime() {
        return finishTime;
    }

//    Initiate setters.
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public void setTaskDays(int taskDays) {
        this.taskDays = taskDays;
    }
    public void setResponsibleUser(int responsibleUser) {
        this.responsibleUser = responsibleUser;
    }
    public void setInitialTime(long initialTime) {
        this.initialTime = initialTime;
    }
    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
