package com.example.johan.roommatehelper;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity implements GroupsRequest.Callback,
        PutgroupHelper.CallbackPut {

    private DrawerLayout Drawerlayout;
    User user;
    Group group;
    long dayinMillis = 86400000;
    long currentTime;
    int cleaningWeek;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("My Tasks");

        Intent intent = getIntent();
        User current_user = (User) intent.getSerializableExtra("loggedInUser");
        user = current_user;
        int userGroup = user.getGroup_id();
        Log.d("hierhebbenwe", "overviewactivity hetzelfde?" + current_user);
        Log.d("hierhebbenwe", "overviewactivity" + user);
        if (userGroup == 0) {
            Intent joingroup_intent = new Intent(OverviewActivity.this, JoingroupActivity.class);
            Log.d("hierhebbenwe", "oerviewactivity2 hetzelfde?" + current_user);
            Log.d("hierhebbenwe", "overviewactivity2" + user.getUser_name());
            joingroup_intent.putExtra("loggedInUser", user);
            startActivity(joingroup_intent);
        } else {
            GroupsRequest groupRequest = new GroupsRequest(this);
            groupRequest.getGroups(this);
        }

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
                                break;
                            case R.id.nav_group:
                                Intent groupIntent = new Intent(OverviewActivity.this, GroupActivity.class);
                                groupIntent.putExtra("loggedInUser", user);
                                groupIntent.putExtra("loggedInGroup", group);
                                startActivity(groupIntent);
                                break;
                            case R.id.nav_shopping:
                                Intent shoppingIntent = new Intent(OverviewActivity.this, ShoppingActivity.class);
                                shoppingIntent.putExtra("loggedInUser", user);
                                shoppingIntent.putExtra("loggedInGroup", group);
                                startActivity(shoppingIntent);
                                break;
                            case R.id.nav_account:
                                Intent accountIntent = new Intent(OverviewActivity.this, AccountActivity.class);
                                accountIntent.putExtra("loggedInUser", user);
                                accountIntent.putExtra("loggedInGroup", group);
                                startActivity(accountIntent);
                                break;
                            case R.id.nav_settings:
                                Intent settingsIntent = new Intent(OverviewActivity.this, SettingsActivity.class);
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

    public void showtask(View view) {
        Intent intent = new Intent(OverviewActivity.this, TaskActivity.class);
        startActivity(intent);
    }
    //    close app if android back button is pressed in this screen
    public void onBackPressed() {
        Intent close = new Intent(Intent.ACTION_MAIN);
        close.addCategory(Intent.CATEGORY_HOME);
        close.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(close);
    }

    @Override
    public void gotGroups(ArrayList<Group> groupsList) {
        for(int i = 0; i < groupsList.size(); i++)  {
            Group currentGroup = groupsList.get(i);
            if(currentGroup.getGroupId() == user.getGroup_id()) {
                group = currentGroup;
                Toast.makeText(this, "ready", Toast.LENGTH_LONG).show();
                if(group.getGroupTasks().size() > 0) {
                    ArrayList<Task> taskList = group.getGroupTasks();
                    Log.d("hierhebbenwe", "werkt het inladen" + taskList.get(0).getTaskDescription());
                    currentTime = System.currentTimeMillis();
                    long initialTime = taskList.get(0).getInitialTime();
                    long timeSinceInitial = currentTime - initialTime;

//                    One day is 86400000 milliseconds.
                    long timeDays = timeSinceInitial/dayinMillis;
                    int timeDaysInt = (int)timeDays;
                    ArrayList<Integer> memberIds = group.getMemberIds();
                    for(int j = 0; j<taskList.size(); j++) {
                        Task currentTask = taskList.get(j);
                        int days = currentTask.getTaskDays();
                        Log.d("hierhebbenwe", "overviewactivity taskname: " + currentTask.getTaskName());
                        cleaningWeek = timeDaysInt/days;
                        int userTurn = j + cleaningWeek;
                        int responsibleUserId = memberIds.get(userTurn%memberIds.size());
                        currentTask.setResponsibleUser(responsibleUserId);
                        Log.d("hierhebbenwe", "overviewactivity cleaningweek: " + cleaningWeek);
                        Log.d("hierhebbenwe", "overviewactivity userTurn: " + userTurn);
                        Log.d("hierhebbenwe", "overviewactivity responsibleUserId: " + responsibleUserId);
                        Log.d("hierhebbenwe", "overviewactivity j: " + j);

                    }
                    PutgroupHelper groupHelper = new PutgroupHelper(group, getApplicationContext(), OverviewActivity.this);
                }
            }
        }
    }

    @Override
    public void gotGroupsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void gotgroupputHelper(String message) {
        Toast.makeText(this, "responsibilities updated", Toast.LENGTH_LONG).show();
        ArrayList<String> listedGroceries = group.getGroceryList();
        ShoppingAdapter adapter = new ShoppingAdapter(this, R.layout.row_grocery, listedGroceries);
        ListView listView = findViewById(R.id.groceryOverView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent shoppingIntent = new Intent(OverviewActivity.this, ShoppingActivity.class);
                shoppingIntent.putExtra("loggedInUser", user);
                shoppingIntent.putExtra("loggedInGroup", group);
                startActivity(shoppingIntent);
            }
        });
        ArrayList<Task> myTasks = new ArrayList<Task>();
        ArrayList<Task> allTasks = group.getGroupTasks();
        for (int i = 0; i<allTasks.size(); i++) {
            Task currentTask = allTasks.get(i);
            if(currentTask.getResponsibleUser() == user.getUserId()) {
                long finishTime = currentTask.getFinishTime();
                long timeSinceFinish = currentTime - finishTime;
                long timeDays = timeSinceFinish/dayinMillis;
                int timeDaysInt = (int)timeDays;
                int days = currentTask.getTaskDays();
                int currentCleaningWeek = timeDaysInt/days;
                if (currentCleaningWeek != cleaningWeek || currentTask.getFinishTime() == 0) {
                    myTasks.add(currentTask);
                }
            }
        }
        TaskoverviewAdapter taskAdapter = new TaskoverviewAdapter(this, R.layout.row_mytask, myTasks);
        ListView taskListView = findViewById(R.id.taskOverView);
        taskListView.setAdapter(taskAdapter);
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task clickedTask = (Task) parent.getItemAtPosition(position);
                Intent taskIntent = new Intent(OverviewActivity.this, TaskActivity.class);
                taskIntent.putExtra("loggedInUser", user);
                taskIntent.putExtra("loggedInGroup", group);
                taskIntent.putExtra("clickedTask", clickedTask);
                startActivity(taskIntent);
            }
        });
    }

    @Override
    public void gotgroupputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
