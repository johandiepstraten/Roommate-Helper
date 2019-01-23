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

public class AdditemActivity extends AppCompatActivity implements PutgroupHelper.CallbackPut {

    Group group;
    User user;
    String newItem;
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
                                Intent overviewIntent = new Intent(AdditemActivity.this, OverviewActivity.class);
                                overviewIntent.putExtra("loggedInUser", user);
                                startActivity(overviewIntent);
                                break;
                            case R.id.nav_group:
                                Intent groupIntent = new Intent(AdditemActivity.this, GroupActivity.class);
                                groupIntent.putExtra("loggedInUser", user);
                                groupIntent.putExtra("loggedInGroup", group);
                                startActivity(groupIntent);
                                break;
                            case R.id.nav_shopping:
                                Intent shoppingIntent = new Intent(AdditemActivity.this, ShoppingActivity.class);
                                shoppingIntent.putExtra("loggedInUser", user);
                                shoppingIntent.putExtra("loggedInGroup", group);
                                startActivity(shoppingIntent);
                                break;
                            case R.id.nav_account:
                                Intent accountIntent = new Intent(AdditemActivity.this, AccountActivity.class);
                                accountIntent.putExtra("loggedInUser", user);
                                accountIntent.putExtra("loggedInGroup", group);
                                startActivity(accountIntent);
                                break;
                            case R.id.nav_settings:
                                Intent settingsIntent = new Intent(AdditemActivity.this, SettingsActivity.class);
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

//    Add input of user as new item to the grocery list.
    public void addnewitem(View view) {
        newItem = ((EditText)findViewById(R.id.addGroceries)).getText().toString();
        ArrayList groceryList = group.getGroceryList();
        groceryList.add(newItem);
        PutgroupHelper groupHelper = new PutgroupHelper(group, getApplicationContext(), AdditemActivity.this);
        }

//    Send user back to ShoppingActivity if back button is pressed.
    public void onBackPressed() {
        Intent shoppingIntent = new Intent(AdditemActivity.this, ShoppingActivity.class);
        shoppingIntent.putExtra("loggedInUser", user);
        shoppingIntent.putExtra("loggedInGroup", group);
        startActivity(shoppingIntent);    }

    @Override
    public void gotgroupputHelper(String message) {
        Toast.makeText(this, newItem + " added to shopping list", Toast.LENGTH_SHORT).show();
        EditText groceryItem = findViewById(R.id.addGroceries);
        groceryItem.getText().clear();
    }

    @Override
    public void gotgroupputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
