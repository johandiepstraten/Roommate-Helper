package com.example.johan.roommatehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewaccountActivity extends AppCompatActivity implements PostuserHelper.CallbackPost {
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);
    }

    public void Create(View view) {
        String username = ((EditText)findViewById(R.id.createName)).getText().toString();
        String password = ((EditText)findViewById(R.id.createPassword)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.recreatePassword)).getText().toString();
        if (username.length() == 0 || password.length() == 0)  {
            Toast.makeText(this, "Fill in all boxes", Toast.LENGTH_SHORT).show();
        }   else if (!password.equals(password2))  {
            Toast.makeText(this, "passwords must be the same", Toast.LENGTH_SHORT).show();
        }   else    {
            PostuserHelper helper = new PostuserHelper(username, password, getApplicationContext(), NewaccountActivity.this);
//            Intent intent = new Intent(NewaccountActivity.this, OverviewActivity.class);
//            startActivity(intent);
        }
    }
//     send user to MainActivity if back button is pressed
    public void onBackPressed() {
        startActivity(new Intent(NewaccountActivity.this, MainActivity.class));
    }
//    if submit was succesfull send user to OverviewActivity
    @Override
    public void gotHelper(String message) {
        startActivity(new Intent(NewaccountActivity.this, OverviewActivity.class));
    }
//    show user possible error message
    @Override
    public void gotHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
