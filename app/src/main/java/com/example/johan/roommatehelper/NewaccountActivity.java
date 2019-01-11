package com.example.johan.roommatehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NewaccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newaccount);
    }

    public void Create(View view) {
        Intent intent = new Intent(NewaccountActivity.this, OverviewActivity.class);
        startActivity(intent);
    }
    public void onBackPressed() {
        startActivity(new Intent(NewaccountActivity.this, MainActivity.class));
    }
}
