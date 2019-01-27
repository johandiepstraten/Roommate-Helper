package com.example.johan.roommatehelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity implements PutgroupHelper.CallbackPut {

    User user;
    Group group;
    Task task;
    long dayinMillis = 86400000;
    private DrawerLayout Drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        group = (Group) intent.getSerializableExtra("loggedInGroup");
        task = (Task) intent.getSerializableExtra("clickedTask");

        ((TextView) findViewById(R.id.descriptionInfo)).setText(task.getTaskDescription());
        long initialTime = task.getInitialTime();
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - initialTime;
        long daysPassed = timePassed/dayinMillis;
        int daysInt = (int)daysPassed;
        int daysLeft = task.getTaskDays() - daysInt;
        ((TextView) findViewById(R.id.deadlineInfo)).setText(daysLeft + " days left to complete task.");



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle(task.getTaskName());



        Drawerlayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        Drawerlayout.closeDrawers();
                        int id = menuItem.getItemId();
                        switch(id)    {
                            case R.id.nav_tasks:
                                Intent overviewIntent = new Intent(TaskActivity.this, OverviewActivity.class);
                                overviewIntent.putExtra("loggedInUser", user);
                                startActivity(overviewIntent);
                                break;
                            case R.id.nav_group:
                                Intent groupIntent = new Intent(TaskActivity.this, GroupActivity.class);
                                groupIntent.putExtra("loggedInUser", user);
                                groupIntent.putExtra("loggedInGroup", group);
                                startActivity(groupIntent);
                                break;
                            case R.id.nav_shopping:
                                Intent shoppingIntent = new Intent(TaskActivity.this, ShoppingActivity.class);
                                shoppingIntent.putExtra("loggedInUser", user);
                                shoppingIntent.putExtra("loggedInGroup", group);
                                startActivity(shoppingIntent);
                                break;
                            case R.id.nav_account:
                                Intent accountIntent = new Intent(TaskActivity.this, AccountActivity.class);
                                accountIntent.putExtra("loggedInUser", user);
                                accountIntent.putExtra("loggedInGroup", group);
                                startActivity(accountIntent);
                                break;
                            case R.id.nav_settings:
                                Intent settingsIntent = new Intent(TaskActivity.this, SettingsActivity.class);
                                settingsIntent.putExtra("loggedInUser", user);
                                settingsIntent.putExtra("loggedInGroup", group);
                                startActivity(settingsIntent);
                                break;
                        }
                        return true;
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Drawerlayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        Intent groupIntent = new Intent(TaskActivity.this, OverviewActivity.class);
        groupIntent.putExtra("loggedInUser", user);
        startActivity(groupIntent);    }

    public void completedTask(View view) {
        //        Source: https://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){

//                    When yes button is pressed.
                    case DialogInterface.BUTTON_POSITIVE:
                        long finishTime = System.currentTimeMillis();
                        task.setFinishTime(finishTime);
                        ArrayList<Task> taskList = group.getGroupTasks();
                        for(int i = 0; i<taskList.size(); i++) {
                            Task currentTask = taskList.get(i);
                            if(currentTask.getTaskDescription().equals(task.getTaskDescription())) {
                                taskList.set(i, task);
                            }
                        }
                        group.setGroupTasks(taskList);
                        PutgroupHelper groupHelper = new PutgroupHelper(group, getApplicationContext(), TaskActivity.this);
                        break;

//                    When No button is pressed.
                    case DialogInterface.BUTTON_NEGATIVE:
                        CheckBox checkbox = (CheckBox) findViewById(R.id.checkBox);
                        checkbox.setSelected(false);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure you to complete the task?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public void gotgroupputHelper(String message) {
        Toast.makeText(this, "Great Job", Toast.LENGTH_SHORT).show();
        Intent groupIntent = new Intent(TaskActivity.this, OverviewActivity.class);
        groupIntent.putExtra("loggedInUser", user);
        startActivity(groupIntent);
    }

    @Override
    public void gotgroupputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
