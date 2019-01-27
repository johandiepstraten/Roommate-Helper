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

public class MainActivity extends AppCompatActivity implements UsersRequest.Callback {

//    Declare variable to use throughout activity.
    ArrayList<User> users;

//    Request list of all users.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsersRequest user = new UsersRequest(this);
        user.getUsers(this);
    }

//    Notify user if users are loaded
    @Override
    public void gotUsers(ArrayList<User> usersList) {
        users = usersList;
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
    }

//    Notify user if loading users failed.
    @Override
    public void gotUsersError(String message) {
        Toast.makeText(this, "Server not running", Toast.LENGTH_LONG).show();
    }

//    Log in user if username and password are correct.
    public void Login(View view) {
        String username = ((EditText)findViewById(R.id.loginName)).getText().toString();
        String password = ((EditText)findViewById(R.id.loginPassword)).getText().toString();
        Boolean usernameFound = false;
        if (users.size() > 0)   {
            for(int i = 0; i < users.size(); i++)   {
                User currentUser = users.get(i);
                String currentUsername = currentUser.getUser_name();
                if (username.equals(currentUsername))    {
                    String currentPassword = currentUser.getUser_password();
                    usernameFound = true;
                    if (password.equals(currentPassword))   {
                        Log.d("hierhebbenwe", "mainactivity" + currentUser);

                        Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
                        intent.putExtra("loggedInUser", currentUser);
                        startActivity(intent);
                    } else  {
                        Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        if (usernameFound == false) {
            Toast.makeText(this, "Username unknown", Toast.LENGTH_SHORT).show();
        }
    }

//    Send user to NewaccountActivity.
    public void create_account(View view) {
        Intent intent = new Intent(MainActivity.this, NewaccountActivity.class);
        startActivity(intent);
    }

//    Close app if android back button is pressed in this screen
    public void onBackPressed() {
        Intent close = new Intent(Intent.ACTION_MAIN);
        close.addCategory(Intent.CATEGORY_HOME);
        close.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(close);
    }
}