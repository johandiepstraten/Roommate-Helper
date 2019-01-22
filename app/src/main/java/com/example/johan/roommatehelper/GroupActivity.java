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

public class GroupActivity extends AppCompatActivity {

    private DrawerLayout Drawerlayout;

//    Set toolbar with title and drawerlayout.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("Group name");

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
                                Intent overview_intent = new Intent(GroupActivity.this, OverviewActivity.class);
                                startActivity(overview_intent);
                                break;
                            case R.id.nav_group:
                                break;
                            case R.id.nav_shopping:
                                Intent shopping_intent = new Intent(GroupActivity.this, ShoppingActivity.class);
                                startActivity(shopping_intent);
                                break;
                            case R.id.nav_account:
                                Intent account_intent = new Intent(GroupActivity.this, AccountActivity.class);
                                startActivity(account_intent);
                                break;
                            case R.id.nav_settings:
                                Intent settings_intent = new Intent(GroupActivity.this, SettingsActivity.class);
                                startActivity(settings_intent);
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

//    Send user to TasksviewActivity if button is selected.
    public void tasksview(View view) {
        Intent intent = new Intent(GroupActivity.this, TasksviewActivity.class);
        startActivity(intent);
    }

//    Send user to OverviewActivity if back button is pressed.
    public void onBackPressed() {
        startActivity(new Intent(GroupActivity.this, OverviewActivity.class));
    }
//
//    Send user to JongroupActiviity if button is pressed.
    public void joingroup(View view) {
        Intent intent = new Intent(GroupActivity.this, JoingroupActivity.class);
        startActivity(intent);
    }
}

