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

public class OverviewActivity extends AppCompatActivity {

    private DrawerLayout Drawerlayout;
    User user;
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
        Log.d("hierhebbenwe", "oerviewactivity hetzelfde?" + current_user);
        Log.d("hierhebbenwe", "overviewactivity" + user);
        if (userGroup == 0) {
            Intent joingroup_intent = new Intent(OverviewActivity.this, JoingroupActivity.class);
            Log.d("hierhebbenwe", "oerviewactivity2 hetzelfde?" + current_user);
            Log.d("hierhebbenwe", "overviewactivity2" + user.getUser_name());
            joingroup_intent.putExtra("loggedInUser", user);
            startActivity(joingroup_intent);
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
                                Intent group_intent = new Intent(OverviewActivity.this, GroupActivity.class);
                                startActivity(group_intent);
                                break;
                            case R.id.nav_shopping:
                                Intent shopping_intent = new Intent(OverviewActivity.this, ShoppingActivity.class);
                                startActivity(shopping_intent);
                                break;
                            case R.id.nav_account:
                                Intent account_intent = new Intent(OverviewActivity.this, AccountActivity.class);
                                startActivity(account_intent);
                                break;
                            case R.id.nav_settings:
                                Intent settings_intent = new Intent(OverviewActivity.this, SettingsActivity.class);
                                startActivity(settings_intent);
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
}
