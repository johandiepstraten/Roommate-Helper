package com.example.johan.roommatehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NewaccountActivity extends AppCompatActivity implements PostuserHelper.CallbackPost,
        UsersRequest.Callback {

//    Declare variables to use throughout activity.
    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);
    }

//    Create account if user input is correct.
    public void Create(View view) {
        username = ((EditText)findViewById(R.id.createName)).getText().toString();
        password = ((EditText)findViewById(R.id.createPassword)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.recreatePassword)).getText().toString();
        if (username.length() == 0 || password.length() == 0)  {
            Toast.makeText(this, "Fill in all boxes", Toast.LENGTH_SHORT).show();
        }   else if (!password.equals(password2))  {
            Toast.makeText(this, "passwords must be the same", Toast.LENGTH_SHORT).show();
        }   else    {
            PostuserHelper helper = new PostuserHelper(username, password, getApplicationContext(),
                    NewaccountActivity.this);
        }
    }

//     send user to MainActivity if back button is pressed
    public void onBackPressed() {
        startActivity(new Intent(NewaccountActivity.this, MainActivity.class));
    }

//    Request users if new user is posted.
    @Override
    public void gotHelper(String message) {
        UsersRequest user = new UsersRequest(this);
        user.getUsers(this);
    }

//    Show user possible error message
    @Override
    public void gotHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

//    if submit was succesfull send user to OverviewActivity
    @Override
    public void gotUsers(ArrayList<User> usersList) {
        for(int i = 0; i < usersList.size(); i++) {
            User currentUser = usersList.get(i);
            if(currentUser.getUser_name().equals(username) && currentUser.getUser_password()
                    .equals(password)) {
                Intent intent = new Intent(NewaccountActivity.this,
                        OverviewActivity.class);
                intent.putExtra("loggedInUser", currentUser);
                startActivity(intent);
            }
        }
    }

//    Notivy user if user request failed.
    @Override
    public void gotUsersError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
