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

public class EditaccountActivity extends AppCompatActivity implements PutuserHelper.CallbackPut,
        PutgroupHelper.CallbackPut {

//    Declare variables to use throughout activity.
    Group group;
    User user;
    String oldUsername;
    private DrawerLayout Drawerlayout;

//    Set toolbar with title and drawerlayout.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaccount);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("Edit Account");

//        Get info about current user and group.
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        group = (Group) intent.getSerializableExtra("loggedInGroup");

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
                                Intent overviewIntent = new Intent(EditaccountActivity.this,
                                        OverviewActivity.class);
                                overviewIntent.putExtra("loggedInUser", user);
                                startActivity(overviewIntent);
                                break;
                            case R.id.nav_group:
                                Intent groupIntent = new Intent(EditaccountActivity.this,
                                        GroupActivity.class);
                                groupIntent.putExtra("loggedInUser", user);
                                groupIntent.putExtra("loggedInGroup", group);
                                startActivity(groupIntent);
                                break;
                            case R.id.nav_shopping:
                                Intent shoppingIntent = new Intent(EditaccountActivity.this,
                                        ShoppingActivity.class);
                                shoppingIntent.putExtra("loggedInUser", user);
                                shoppingIntent.putExtra("loggedInGroup", group);
                                startActivity(shoppingIntent);
                                break;
                            case R.id.nav_account:
                                Intent accountIntent = new Intent(EditaccountActivity.this,
                                        AccountActivity.class);
                                accountIntent.putExtra("loggedInUser", user);
                                accountIntent.putExtra("loggedInGroup", group);
                                startActivity(accountIntent);
                                break;
                            case R.id.nav_settings:
                                Intent settingsIntent = new Intent(EditaccountActivity.this,
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

//    Update user object if save button is clicked.
    public void saveaccount(View view) {
        String username = ((EditText)findViewById(R.id.newUsername)).getText().toString();
        String oldPassword = ((EditText)findViewById(R.id.oldPassword)).getText().toString();
        String password = ((EditText)findViewById(R.id.newPassword)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.repeatNewPassword)).getText().toString();
        if (username.length() == 0 || oldPassword.length() == 0 || password.length() == 0 ||
                password2.length() == 0)  {
            Toast.makeText(this, "Fill in all boxes", Toast.LENGTH_SHORT).show();
        }   else if (!password.equals(password2))  {
            Toast.makeText(this, "passwords must be the same", Toast.LENGTH_SHORT).show();
        }   else if (!oldPassword.equals(user.getUser_password())) {
            Toast.makeText(this, "Old password incorrect", Toast.LENGTH_SHORT).show();
        }   else    {
            oldUsername = user.getUser_name();
            user.setUser_name(username);
            user.setUser_password(password);
            PutuserHelper userHelper = new PutuserHelper(user, getApplicationContext(),
                    EditaccountActivity.this);

        }
    }

//    Send user to AccountActivity if backbutton is pressed.
    public void onBackPressed() {
        Intent accountIntent = new Intent(EditaccountActivity.this,
                AccountActivity.class);
        accountIntent.putExtra("loggedInUser", user);
        accountIntent.putExtra("loggedInGroup", group);
        startActivity(accountIntent);    }

//     Update groupmembers in group when user is updated.
    @Override
    public void gotputHelper(String message) {
        ArrayList members = group.getGroupMembers();
        for(int i = 0; i < members.size(); i++) {
            String currentMember = members.get(i).toString();
            if (currentMember.equals(oldUsername))  {
                members.remove(i);
                members.add(user.getUser_name());
            }
        }
        group.setGroupMembers(members);
        PutgroupHelper groupHelper = new PutgroupHelper(group, getApplicationContext(),
                EditaccountActivity.this);

    }

//    Notivy user if userupdate failed.
    @Override
    public void gotputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    clear all fields if group is updated.
    @Override
    public void gotgroupputHelper(String message) {
        Toast.makeText(this, "Account saved", Toast.LENGTH_SHORT).show();
        EditText userName = findViewById(R.id.newUsername);
        userName.getText().clear();
        EditText oldPassword = findViewById(R.id.oldPassword);
        oldPassword.getText().clear();
        EditText newPassword = findViewById(R.id.newPassword);
        newPassword.getText().clear();
        EditText repeatPassword = findViewById(R.id.repeatNewPassword);
        repeatPassword.getText().clear();
    }

//    Notify user if group update failed.
    @Override
    public void gotgroupputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
