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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {

    Group group;
    User user;
    private DrawerLayout Drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

//        Set up toolbar with title and drawerlayout.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("My Account");

//        Get information about current user and group.
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        group = (Group) intent.getSerializableExtra("loggedInGroup");

        String accountName = user.getUser_name();
        String accountGroup = group.getGroupName();
        TextView nameAccount = (TextView)findViewById(R.id.accountName);
        TextView groupAccount = (TextView)findViewById(R.id.accountGroup);
        nameAccount.setText("Name: " + accountName);
        groupAccount.setText("Group: " + accountGroup);

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
                                Intent overviewIntent = new Intent(AccountActivity.this,
                                        OverviewActivity.class);
                                overviewIntent.putExtra("loggedInUser", user);
                                startActivity(overviewIntent);
                                break;
                            case R.id.nav_group:
                                Intent groupIntent = new Intent(AccountActivity.this,
                                        GroupActivity.class);
                                groupIntent.putExtra("loggedInUser", user);
                                groupIntent.putExtra("loggedInGroup", group);
                                startActivity(groupIntent);
                                break;
                            case R.id.nav_shopping:
                                Intent shoppingIntent = new Intent(AccountActivity.this,
                                        ShoppingActivity.class);
                                shoppingIntent.putExtra("loggedInUser", user);
                                shoppingIntent.putExtra("loggedInGroup", group);
                                startActivity(shoppingIntent);
                                break;
                            case R.id.nav_account:
                                break;
                            case R.id.nav_settings:
                                Intent settingsIntent = new Intent(AccountActivity.this,
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

//    Open drawermenu if selected by user.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Drawerlayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    Send user to EditaccountActivity if selected.
    public void editaccount(View view) {
        Intent intent = new Intent(AccountActivity.this, EditaccountActivity.class);
        intent.putExtra("loggedInUser", user);
        intent.putExtra("loggedInGroup", group);
        startActivity(intent);
    }

//    Send user back to OverviewActivity if backbutton is pressed.
    public void onBackPressed() {
        Intent intent = new Intent(AccountActivity.this, OverviewActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
    }

//    Log out user.
    public void logOut(View view) {

//        Start AlertDialog if user wants to log out.
//        Source: https://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){

//                    When yes button is pressed.
                    case DialogInterface.BUTTON_POSITIVE:
                        Intent intent = new Intent(AccountActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        break;

//                    When No button is pressed.
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("Are you sure you want to Log out?").setPositiveButton("Yes",
                dialogClickListener).setNegativeButton("No", dialogClickListener).show();
    }
}
