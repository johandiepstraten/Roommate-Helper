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
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

//    Declare variables to use throughout the activity.
    User user;
    Group group;
    private DrawerLayout Drawerlayout;

//    Set up toolbar and drawermenu.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("Info");

//        Get info about current user and group.
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        group = (Group) intent.getSerializableExtra("loggedInGroup");

//        Set info about app.
        TextView infoText = (TextView) findViewById(R.id.appInfo);
        infoText.setText("Roommate helper is an app that let's you create a group with your " +
                "friends in order to divide tasks and keep track of a shared grocery list. " +
                "Everyone in the group will see the same grocery list and is able to update" +
                " it whenever needed. Tasks are equelly divided when added to the group. " +
                "The app will keep track of which tasks you have to do and which tasks you have " +
                "completed. In the 'My Group' screen you can see an overview of all tasks. " +
                "Pay attention to the fact that the planning of all tasks will be reset each time" +
                " a new task is added or a change in group mambers is made.");

//        Send user to chosen activity in the navigation drawer.
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
                        switch(id)    {
                            case R.id.nav_tasks:
                                Intent overviewIntent = new Intent(SettingsActivity.this,
                                        OverviewActivity.class);
                                overviewIntent.putExtra("loggedInUser", user);
                                startActivity(overviewIntent);
                                break;
                            case R.id.nav_group:
                                Intent groupIntent = new Intent(SettingsActivity.this,
                                        GroupActivity.class);
                                groupIntent.putExtra("loggedInUser", user);
                                groupIntent.putExtra("loggedInGroup", group);
                                startActivity(groupIntent);
                                break;
                            case R.id.nav_shopping:
                                Intent shoppingIntent = new Intent(SettingsActivity.this,
                                        ShoppingActivity.class);
                                shoppingIntent.putExtra("loggedInUser", user);
                                shoppingIntent.putExtra("loggedInGroup", group);
                                startActivity(shoppingIntent);
                                break;
                            case R.id.nav_account:
                                Intent accountIntent = new Intent(SettingsActivity.this,
                                        AccountActivity.class);
                                accountIntent.putExtra("loggedInUser", user);
                                accountIntent.putExtra("loggedInGroup", group);
                                startActivity(accountIntent);
                                break;
                            case R.id.nav_settings:
                                break;
                        }
                        return true;
                    }
                });
    }

//    Open drawermenu.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Drawerlayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    Send user back to overviewactivity if back button is pressed.
    public void onBackPressed() {
        Intent intent = new Intent(SettingsActivity.this, OverviewActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);    }
}
