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
import android.widget.Toast;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity implements GroupsRequest.Callback {

    private DrawerLayout Drawerlayout;
    User user;
    Group group;
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

//                HIER VERDERGAAN VOOR HET TOEVOEGEN VAN ADAPTERS ETC.
            }
        }
    }

    @Override
    public void gotGroupsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
