/*
Johan Diepstraten 10774920
In this activity the user can add tasks to the group.
*/
package com.example.johan.roommatehelper;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddtaskActivity extends AppCompatActivity implements PutgroupHelper.CallbackPut {

//    Declare variables to use throughout activity
    User user;
    Group group;
    Button disableButton;
    private DrawerLayout Drawerlayout;

//    Set toolbar with title and drawerlayout.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

//        Get info about current user en group.
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        group = (Group) intent.getSerializableExtra("loggedInGroup");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("Add Task");
        toolbar.setTitleTextColor(Color.WHITE);

        Drawerlayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

//                        Set item as selected to persist highlight.
                        menuItem.setChecked(true);

//                        Close drawer when item is tapped.
                        Drawerlayout.closeDrawers();
                        int id = menuItem.getItemId();

//                        Send user to selected activity.
                        switch(id)    {
                            case R.id.nav_tasks:
                                Intent overviewIntent = new Intent(AddtaskActivity.this, OverviewActivity.class);
                                overviewIntent.putExtra("loggedInUser", user);
                                startActivity(overviewIntent);
                                break;
                            case R.id.nav_group:
                                Intent groupIntent = new Intent(AddtaskActivity.this, GroupActivity.class);
                                groupIntent.putExtra("loggedInUser", user);
                                groupIntent.putExtra("loggedInGroup", group);
                                startActivity(groupIntent);
                                break;
                            case R.id.nav_shopping:
                                Intent shoppingIntent = new Intent(AddtaskActivity.this, ShoppingActivity.class);
                                shoppingIntent.putExtra("loggedInUser", user);
                                shoppingIntent.putExtra("loggedInGroup", group);
                                startActivity(shoppingIntent);
                                break;
                            case R.id.nav_account:
                                Intent accountIntent = new Intent(AddtaskActivity.this, AccountActivity.class);
                                accountIntent.putExtra("loggedInUser", user);
                                accountIntent.putExtra("loggedInGroup", group);
                                startActivity(accountIntent);
                                break;
                            case R.id.nav_settings:
                                Intent settingsIntent = new Intent(AddtaskActivity.this, SettingsActivity.class);
                                settingsIntent.putExtra("loggedInUser", user);
                                settingsIntent.putExtra("loggedInGroup", group);
                                startActivity(settingsIntent);
                                break;
                        }
                        return true;
                    }
                });
    }

//    Open drawermenu if selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Drawerlayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    Add input of user as new task to the task list.
    public void addnewtask(View view) {
        disableButton = findViewById(R.id.addTaskButton);
        disableButton.setEnabled(false);
        String taskName = ((EditText)findViewById(R.id.taskName)).getText().toString();
        String taskDescription = ((EditText)findViewById(R.id.taskDescription)).getText().toString();
        String taskNumberString = ((EditText)findViewById(R.id.taskNumber)).getText().toString();
        taskDescription = taskDescription.replace(",", "");
        taskName = taskName.replace(",", "");
        if(taskName.length() == 0 || taskDescription.length() == 0 || taskNumberString.length() ==0) {
            Toast.makeText(this, "Fill in all fields", Toast.LENGTH_SHORT).show();
            disableButton.setEnabled(true);
        } else {
            int taskNumber = Integer.parseInt(taskNumberString);
            long initialTime = System.currentTimeMillis();
            Task newTask = new Task(taskName, taskDescription, taskNumber, 0, initialTime, 0 );
            ArrayList<Task> tasks = group.getGroupTasks();
            ArrayList<Integer> memberIds = group.getMemberIds();
            tasks.add(newTask);
            for(int i = 0; i<tasks.size(); i++) {
                Task currentTask = tasks.get(i);
                currentTask.setInitialTime(initialTime);
                currentTask.setFinishTime(0);
                int responsibleUserId = memberIds.get(i%memberIds.size());
                currentTask.setResponsibleUser(responsibleUserId);
            }
            group.setGroupTasks(tasks);
            PutgroupHelper groupHelper = new PutgroupHelper(group, getApplicationContext(),
                    AddtaskActivity.this);
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        }
    }

//    Send user to GroupActivity when back button is pressed.
    public void onBackPressed() {
        Intent groupIntent = new Intent(AddtaskActivity.this, GroupActivity.class);
        groupIntent.putExtra("loggedInUser", user);
        groupIntent.putExtra("loggedInGroup", group);
        startActivity(groupIntent);
        }

//    Notify user if task is added and  clear text fields
    @Override
    public void gotgroupputHelper(String message) {
        Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show();
        EditText taskName = findViewById(R.id.taskName);
        EditText taskDescription = findViewById(R.id.taskDescription);
        EditText taskNumber = findViewById(R.id.taskNumber);
        taskName.getText().clear();
        taskDescription.getText().clear();
        taskNumber.getText().clear();
        disableButton.setEnabled(true);
    }

//    notify user if something went wrong with adding the task
    @Override
    public void gotgroupputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        disableButton.setEnabled(true);
    }
}
