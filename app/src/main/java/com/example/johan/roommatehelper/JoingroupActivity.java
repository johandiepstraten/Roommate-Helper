package com.example.johan.roommatehelper;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class JoingroupActivity extends AppCompatActivity implements GroupsRequest.Callback{

    private DrawerLayout Drawerlayout;
    User user;
    ArrayList<Group> groups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joingroup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("Join Group");

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");

        GroupsRequest groupRequest = new GroupsRequest(this);
        groupRequest.getGroups(this);

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
                                Intent overview_intent = new Intent(JoingroupActivity.this, OverviewActivity.class);
                                startActivity(overview_intent);
                                break;
                            case R.id.nav_group:
                                Intent group_intent = new Intent(JoingroupActivity.this, GroupActivity.class);
                                startActivity(group_intent);
                                break;
                            case R.id.nav_shopping:
                                Intent shopping_intent = new Intent(JoingroupActivity.this, ShoppingActivity.class);
                                startActivity(shopping_intent);
                                break;
                            case R.id.nav_account:
                                Intent account_intent = new Intent(JoingroupActivity.this, AccountActivity.class);
                                startActivity(account_intent);
                                break;
                            case R.id.nav_settings:
                                Intent settings_intent = new Intent(JoingroupActivity.this, SettingsActivity.class);
                                startActivity(settings_intent);
                                break;
                        }
                        return true;
                    }
                });
    }
    @Override
    public void gotGroups(ArrayList<Group> groupsList) {
        groups = groupsList;
        Toast.makeText(this, "groups loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void gotGroupsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(JoingroupActivity.this, GroupActivity.class));
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
//                    PutGroupHelper groupHelper = new PutGroupHelper(currentGroup, getApplicationContext(), JoingroupActivity.this);
                    joinedGroup.add(user.getUser_name());
                    user.setGroup_name(currentGroup.getGroupName());
//                    PutUserHelper userHelper = new PutUserHelper(user, getApplicationContext(), JoingroupActivity.this);
                    Intent intent = new Intent(JoingroupActivity.this, OverviewActivity.class);
                    intent.putExtra("loggedInUser", user);
                    startActivity(intent);
                } else  {
                    Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
                }
            }
        }
        Toast.makeText(this, "Username unknown", Toast.LENGTH_SHORT).show();
    }

    public void create_group(View view) {
        Intent intent = new Intent(JoingroupActivity.this, CreategroupActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
    }
}
