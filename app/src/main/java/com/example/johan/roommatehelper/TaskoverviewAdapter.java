package com.example.johan.roommatehelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskoverviewAdapter extends ArrayAdapter<Task> {

//    Declare variables to use throughout the adapter.
    private ArrayList<Task> myTasks;
    private Context context;
    long DAY_IN_MILLIS = 86400000;

//  Initiate adapter.
    public TaskoverviewAdapter(Context context, int resource, ArrayList<Task> myTasks) {
        super(context, resource, myTasks);
        this.myTasks = myTasks;
        this.context = context;
    }
//    Set adapter to view personal tasks.
    public View getView(int position, View listView, ViewGroup parent) {
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.row_mytask, parent,
                    false);
        }
        Task currentTask = myTasks.get(position);
        String taskName = currentTask.getTaskName();
        ((TextView) listView.findViewById(R.id.taskNameView)).setText(taskName);
        long initialTime = currentTask.getInitialTime();
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - initialTime;
        long daysPassed = timePassed/DAY_IN_MILLIS;
        int daysInt = (int)daysPassed;
        int daysLeft = currentTask.getTaskDays() - daysInt;
        ((TextView) listView.findViewById(R.id.taskDeadlineView)).setText(daysLeft + " days.");

        return listView;
    }
}