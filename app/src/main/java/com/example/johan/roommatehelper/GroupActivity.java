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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupActivity extends AppCompatActivity implements PutgroupHelper.CallbackPut {

//    declare variable to use throughout the activity.
    User user;
    Group group;
    private DrawerLayout Drawerlayout;
    Boolean inGroup;

//    Set toolbar with title and drawerlayout.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

//        Get info about current user and group.
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        group = (Group) intent.getSerializableExtra("loggedInGroup");
        String groupName = group.getGroupName();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle(groupName);

//        Show all group members
        ArrayList members = group.getGroupMembers();
        StringBuilder groupMembers = new StringBuilder("Roommates: ");
        for(int i = 0; i<members.size(); i++)   {
            if (i != (members.size()-1))    {
                groupMembers.append(members.get(i) + ", ");
            } else  {
                groupMembers.append(members.get(i) + ".");
            }
        }
        TextView showMembers = (TextView)findViewById(R.id.groupMembers);
        showMembers.setText(groupMembers);

        inGroup = true;

//        Set adapter to show all tasks in group.
        final ArrayList<Task> allTasks = group.getGroupTasks();
        AlltasksoverviewAdapter taskAdapter = new AlltasksoverviewAdapter(this,
                R.layout.row_alltasks, allTasks);
        ListView taskListView = findViewById(R.id.allTasksView);
        taskListView.setAdapter(taskAdapter);

//        Delete task from group when longclicked
        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                allTasks.remove(position);
                group.setGroupTasks(allTasks);
                PutgroupHelper groupHelper = new PutgroupHelper(group, getApplicationContext(),
                        GroupActivity.this);
                return false;
            }
        });


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
                                Intent overviewIntent = new Intent(GroupActivity.this,
                                        OverviewActivity.class);
                                overviewIntent.putExtra("loggedInUser", user);
                                startActivity(overviewIntent);
                                break;
                            case R.id.nav_group:
                                break;
                            case R.id.nav_shopping:
                                Intent shoppingIntent = new Intent(GroupActivity.this,
                                        ShoppingActivity.class);
                                shoppingIntent.putExtra("loggedInUser", user);
                                shoppingIntent.putExtra("loggedInGroup", group);
                                startActivity(shoppingIntent);
                                break;
                            case R.id.nav_account:
                                Intent accountIntent = new Intent(GroupActivity.this,
                                        AccountActivity.class);
                                accountIntent.putExtra("loggedInUser", user);
                                accountIntent.putExtra("loggedInGroup", group);
                                startActivity(accountIntent);
                                break;
                            case R.id.nav_settings:
                                Intent settingsIntent = new Intent(GroupActivity.this,
                                        SettingsActivity.class);
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

//    Send user to AddtaskActivity if button is selected.
    public void addTask(View view) {
        Intent intent = new Intent(GroupActivity.this, AddtaskActivity.class);
        intent.putExtra("loggedInUser", user);
        intent.putExtra("loggedInGroup", group);
        startActivity(intent);
    }

//    Send user to OverviewActivity if back button is pressed.
    public void onBackPressed() {
        Intent intent = new Intent(GroupActivity.this, OverviewActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
    }

//    Delete user from group.
    public void exitGroup(View view) {

//        Source: https://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){

//                    When yes button is pressed.
                    case DialogInterface.BUTTON_POSITIVE:
                        user.setGroup_id(0);
                        ArrayList<String> memberNames = group.getGroupMembers();
                        int nameIndex = memberNames.indexOf(user.getUser_name());
                        memberNames.remove(nameIndex);
                        group.setGroupMembers(memberNames);
                        ArrayList<Integer> memberIds = group.getMemberIds();
                        int idIndex = memberIds.indexOf(user.getUserId());
                        memberIds.remove(idIndex);
                        group.setMemberIds(memberIds);
                        inGroup = false;
                        PutgroupHelper groupHelper = new PutgroupHelper(group, getApplicationContext(),
                                GroupActivity.this);
                        break;

//                    When No button is pressed.
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

//        Open AlertDialog when user wants to leave group.
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure you want to leave this group?").setPositiveButton("Yes",
                dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

//    If group is updated, send user back to groupActivity if still in the group. Else, send user
//    back to overviewactivity.
    @Override
    public void gotgroupputHelper(String message) {
        if(inGroup) {
            Toast.makeText(this, "Task removed", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(GroupActivity.this, GroupActivity.class);
            intent.putExtra("loggedInUser", user);
            intent.putExtra("loggedInGroup", group);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Removed from group", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(GroupActivity.this, OverviewActivity.class);
            intent.putExtra("loggedInUser", user);
            startActivity(intent);
        }

    }

//    Notify user if updating the group failed.
    @Override
    public void gotgroupputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

