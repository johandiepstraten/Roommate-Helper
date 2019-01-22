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
import android.widget.Toast;

public class AdditemActivity extends AppCompatActivity {
    private DrawerLayout Drawerlayout;

//    Set toolbar with title and drawerlayout.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additem);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("Add Item");


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
                                Intent overview_intent = new Intent(AdditemActivity.this, OverviewActivity.class);
                                startActivity(overview_intent);
                                break;
                            case R.id.nav_group:
                                Intent group_intent = new Intent(AdditemActivity.this, GroupActivity.class);
                                startActivity(group_intent);
                                break;
                            case R.id.nav_shopping:
                                Intent shopping_intent = new Intent(AdditemActivity.this, ShoppingActivity.class);
                                startActivity(shopping_intent);
                                break;
                            case R.id.nav_account:
                                Intent account_intent = new Intent(AdditemActivity.this, AccountActivity.class);
                                startActivity(account_intent);
                                break;
                            case R.id.nav_settings:
                                Intent settings_intent = new Intent(AdditemActivity.this, SettingsActivity.class);
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

//    Add input of user as new item to the grocery list.
    public void addnewitem(View view) {
        Toast.makeText(this, "Item added to shopping list", Toast.LENGTH_SHORT).show();
    }

//    Send user back to ShoppingActivity if back button is pressed.
    public void onBackPressed() {
        startActivity(new Intent(AdditemActivity.this, ShoppingActivity.class));
    }
}
