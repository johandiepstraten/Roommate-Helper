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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShoppingActivity extends AppCompatActivity implements PutgroupHelper.CallbackPut {

    User user;
    Group group;
    String removedGrocery;
    private DrawerLayout Drawerlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionbar.setTitle("Grocery List");

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("loggedInUser");
        group = (Group) intent.getSerializableExtra("loggedInGroup");
        final ArrayList listedGroceries = group.getGroceryList();
        if(listedGroceries.size() == 0) {
            TextView groceryInfo = findViewById(R.id.groceryInfo);
            groceryInfo.setText("No groceries at the moment.");
        }

        ShoppingAdapter adapter = new ShoppingAdapter(this, R.layout.row_grocery, listedGroceries);
        ListView listView = findViewById(R.id.shopView);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                removedGrocery = listedGroceries.get(position).toString();
                Log.d("hierhebbenwe", "shoppingactivity longclick1" + listedGroceries);
                listedGroceries.remove(position);
                group.setGroceryList(listedGroceries);
                PutgroupHelper groupHelper = new PutgroupHelper(group, getApplicationContext(), ShoppingActivity.this);
                Log.d("hierhebbenwe", "shoppingactivity longclick2" + listedGroceries);
                Log.d("hierhebbenwe", "shoppingactivity longclick3" + group.getGroceryList());


                return false;
            }
        });


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
                                Intent overviewIntent = new Intent(ShoppingActivity.this, OverviewActivity.class);
                                overviewIntent.putExtra("loggedInUser", user);
                                startActivity(overviewIntent);
                                break;
                            case R.id.nav_group:
                                Intent groupIntent = new Intent(ShoppingActivity.this, GroupActivity.class);
                                groupIntent.putExtra("loggedInUser", user);
                                groupIntent.putExtra("loggedInGroup", group);
                                startActivity(groupIntent);
                                break;
                            case R.id.nav_shopping:
                                break;
                            case R.id.nav_account:
                                Intent accountIntent = new Intent(ShoppingActivity.this, AccountActivity.class);
                                accountIntent.putExtra("loggedInUser", user);
                                accountIntent.putExtra("loggedInGroup", group);
                                startActivity(accountIntent);
                                break;
                            case R.id.nav_settings:
                                Intent settingsIntent = new Intent(ShoppingActivity.this, SettingsActivity.class);
                                settingsIntent.putExtra("loggedInUser", user);
                                settingsIntent.putExtra("loggedInGroup", group);
                                startActivity(settingsIntent);
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

    public void additem(View view) {
        Intent intent = new Intent(ShoppingActivity.this, AdditemActivity.class);
        intent.putExtra("loggedInUser", user);
        intent.putExtra("loggedInGroup", group);
        startActivity(intent);
    }
    public void onBackPressed() {
        Intent intent = new Intent(ShoppingActivity.this, OverviewActivity.class);
        intent.putExtra("loggedInUser", user);
        startActivity(intent);
    }

    @Override
    public void gotgroupputHelper(String message) {
        Toast.makeText(this, "You bought " + removedGrocery, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ShoppingActivity.this, ShoppingActivity.class);
        intent.putExtra("loggedInUser", user);
        intent.putExtra("loggedInGroup", group);
        startActivity(intent);
    }

    @Override
    public void gotgroupputHelperError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

