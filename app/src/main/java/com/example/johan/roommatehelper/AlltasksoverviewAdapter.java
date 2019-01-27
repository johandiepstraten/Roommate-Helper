package com.example.johan.roommatehelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class AlltasksoverviewAdapter extends ArrayAdapter<Task> {

//    Declare variables to use throughout adapter.
    private ArrayList<Task> allTasks;
    private Context context;
    long dayinMillis = 86400000;

    //  Initiate adapter
    public AlltasksoverviewAdapter(Context context, int resource, ArrayList<Task> allTasks) {
        super(context, resource, allTasks);
        this.allTasks = allTasks;
        this.context = context;
    }
    //    Set adapter to view information about task.
    public View getView(int position, View listView, ViewGroup parent) {
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.row_alltasks, parent, false);
        }
        Task currentTask = allTasks.get(position);
        String taskName = currentTask.getTaskName();
        ((TextView) listView.findViewById(R.id.nameofTask)).setText(taskName);
        String taskDescription = currentTask.getTaskDescription();
        ((TextView) listView.findViewById(R.id.descriptionofTask)).setText(taskDescription);
        long currentTime = System.currentTimeMillis();
        long finishTime = currentTask.getFinishTime();
        long timeSinceFinish = currentTime - finishTime;
        long timeDays = timeSinceFinish/dayinMillis;
        int timeDaysInt = (int)timeDays;
        int days = currentTask.getTaskDays();
        int currentCleaningWeek = timeDaysInt/days;

        long timeSinceInitial = currentTime - currentTask.getInitialTime();

        long daysPassed = timeSinceInitial/dayinMillis;
        int daysPassedInt = (int)daysPassed;
        int daysLeft = days - daysPassedInt;
        int cleaningWeek = daysPassedInt/days;
        if (currentCleaningWeek != cleaningWeek || currentTask.getFinishTime() == 0) {
            ((TextView) listView.findViewById(R.id.taskCompleted)).setText(daysLeft +
                    " days left to complete task.");
        } else {
            ((TextView) listView.findViewById(R.id.taskCompleted)).setText("Task completed!");
        }
        return listView;
    }
}
