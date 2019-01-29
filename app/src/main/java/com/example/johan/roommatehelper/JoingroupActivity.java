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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class JoingroupActivity extends AppCompatActivity implements GroupsRequest.Callback,
        PutuserHelper.CallbackPut, PutgroupHelper.CallbackPut {

//    Declare variables to be used throughout the activity.
    private DrawerLayout Drawerlayout;
    User user;
    Group group;
    ArrayList<Group> groups;
    Button disableButton;

//    Set toolbar with title and drawerlayout.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingroup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Join Group");
        toolbar.setTitleTextColor(Color.WHITE);

//        Get info about current user and request all groups.
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        GroupsRequest groupRequest = new GroupsRequest(this);
        groupRequest.getGroups(this);
    }

//    Recieve list of all groups.
    @Override
    public void gotGroups(ArrayList<Group> groupsList) {
        groups = groupsList;
        Toast.makeText(this, "groups loaded", Toast.LENGTH_SHORT).show();
    }

//    Notify user if requesting groups failed.
    @Override
    public void gotGroupsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

//    Send user back to mainactivity if backbutton pressed.
    public void onBackPressed() {
        startActivity(new Intent(JoingroupActivity.this, MainActivity.class));
    }

//    Put user in group if correct username and password are given.
    public void Join(View view) {
        disableButton = findViewById(R.id.joinGroupButton);
        disableButton.setEnabled(false);
        String groupName = ((EditText)findViewById(R.id.groupName)).getText().toString();
        String groupPassword = ((EditText)findViewById(R.id.groupPassword)).getText().toString();
        Boolean groupFound = false;
        for(int i = 0; i < groups.size(); i++)   {
            Group currentGroup = groups.get(i);
            String currentGroupName = currentGroup.getGroupName();
            if (groupName.equals(currentGroupName))    {
                groupFound = true;
                String currentGroupPassword = currentGroup.getGroupPassword();
                if (groupPassword.equals(currentGroupPassword))   {
                    ArrayList<String> joinedGroup = currentGroup.getGroupMembers();
                    joinedGroup.add(user.getUser_name());
                    currentGroup.setGroupMembers(joinedGroup);
                    ArrayList<Integer> joinedGroupIds = currentGroup.getMemberIds();
                    joinedGroupIds.add(user.getUserId());
                    currentGroup.setMemberIds(joinedGroupIds);
                    group = currentGroup;
                    PutgroupHelper groupHelper = new PutgroupHelper(currentGroup,
                            getApplicationContext(), JoingroupActivity.this);
                } else  {
                    Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
                    disableButton.setEnabled(true);
                }
            }
        }
        if(!groupFound) {
            Toast.makeText(this, "Username unknown", Toast.LENGTH_SHORT).show();
            disableButton.setEnabled(true);
        }
    }

//    Send user to creategroup activity
    public void create_group(View view) {
        Intent intent = new Intent(JoingroupActivity.this, CreategroupActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
    }

//    Notify the user that the newly created group is added to its account
//    and send user back to OverviewActivity.
    @Override
    public void gotputHelper(String message) {
        Toast.makeText(this, "User updated", Toast.LENGTH_SHORT).show();
        disableButton.setEnabled(true);
        Intent intent = new Intent(JoingroupActivity.this, OverviewActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
    }

    //    Notify user if something went wrong with assigning the group to its account.
    @Override
    public void gotputHelperError(String message) {
        disableButton.setEnabled(true);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    Notify user if group is updated and update user with groupId
    @Override
    public void gotgroupputHelper(String message) {
        Toast.makeText(this, "Group updated", Toast.LENGTH_SHORT).show();
        user.setGroup_id(group.getGroupId());
        PutuserHelper userHelper = new PutuserHelper(user, getApplicationContext(),
                JoingroupActivity.this);
    }

//    Notify user if updating group failed.
    @Override
    public void gotgroupputHelperError(String message) {
        disableButton.setEnabled(true);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
