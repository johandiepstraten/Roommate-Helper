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
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class JoingroupActivity extends AppCompatActivity implements GroupsRequest.Callback, PutuserHelper.CallbackPut, PutgroupHelper.CallbackPut {

//    Declare variables to be used throughout the activity.
    private DrawerLayout Drawerlayout;
    User user;
    Group group;
    ArrayList<Group> groups;

//    Set toolbar with title and drawerlayout.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingroup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Join Group");

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        Log.d("hierhebbenwe", "Joingroupactivity" + user);
        GroupsRequest groupRequest = new GroupsRequest(this);
        groupRequest.getGroups(this);
    }
    @Override
    public void gotGroups(ArrayList<Group> groupsList) {
        Log.d("hierhebbenwe", "JoingroupActivity gotgroups aangeroepen");
        groups = groupsList;
        Toast.makeText(this, "groups loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotGroupsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void onBackPressed() {
        startActivity(new Intent(JoingroupActivity.this, MainActivity.class));
    }

    public void Join(View view) {
        String groupName = ((EditText)findViewById(R.id.groupName)).getText().toString();
        String groupPassword = ((EditText)findViewById(R.id.groupPassword)).getText().toString();
        for(int i = 0; i < groups.size(); i++)   {
            Group currentGroup = groups.get(i);
            String currentGroupName = currentGroup.getGroupName();
            if (groupName.equals(currentGroupName))    {
                String currentGroupPassword = currentGroup.getGroupPassword();
                if (groupPassword.equals(currentGroupPassword))   {
                    ArrayList<String> joinedGroup = currentGroup.getGroupMembers();
                    joinedGroup.add(user.getUser_name());
                    currentGroup.setGroupMembers(joinedGroup);
                    group = currentGroup;
                    PutgroupHelper groupHelper = new PutgroupHelper(currentGroup, getApplicationContext(), JoingroupActivity.this);
                } else  {
                    Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        }
        Toast.makeText(this, "Username unknown", Toast.LENGTH_SHORT).show();
    }

    public void create_group(View view) {
        Intent intent = new Intent(JoingroupActivity.this, CreategroupActivity.class);
        Log.d("hierhebbenwe", " JoingroupActivity test");
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
    }
    //    Notify the user that the newly created group is added to its account and send user back to OverviewActivity.
    @Override
    public void gotputHelper(String message) {
        Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(JoingroupActivity.this, OverviewActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
    }
    //    Notify user if something went wrong with assigning the group to its account.
    @Override
    public void gotputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotgroupputHelper(String message) {
        Toast.makeText(this, "Group updated", Toast.LENGTH_SHORT).show();
        user.setGroup_id(group.getGroupId());
        PutuserHelper userHelper = new PutuserHelper(user, getApplicationContext(), JoingroupActivity.this);
    }

    @Override
    public void gotgroupputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
