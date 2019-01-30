/*
Johan Diepstraten 10774920
Here all existing users are loaded in from the online JSON file as a list of User objects.
*/
package com.example.johan.roommatehelper;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//    Get questions from online json file.
public class UsersRequest implements Response.ErrorListener, Response.Listener<JSONArray> {

//    Declare variables to use throughout the request.
    private Context context;
    Callback activity;

    public UsersRequest (Context context)  {
        this.context = context;
    }
    @Override

//    Forward possible error message.
    public void onErrorResponse(VolleyError error) {
        activity.gotUsersError(error.getMessage());
    }
    @Override
    public void onResponse(JSONArray response) {
        ArrayList<User> usersList = new ArrayList<User>();

//        Extract all needed values from JsonObject and make User objects for each user.
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject currentUser = response.getJSONObject(i);
                int userId = currentUser.getInt("id");
                String username = currentUser.getString("Username");
                String password = currentUser.getString("Password");
                String groupIdString = currentUser.getString("Group");
                int groupId = Integer.parseInt(groupIdString);
                usersList.add(new User(userId, username, password, groupId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        Send list to callback.
        activity.gotUsers(usersList);
    }

//    Send result of request back to the requesting activity.
    public interface Callback {
        void gotUsers(ArrayList<User> usersList);
        void gotUsersError(String message);
    }

//    Get jsonarray from right url.
    void getUsers(Callback activity)   {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                "https://ide50-johadiep.legacy.cs50.io:8080/users", this,
                this);
        queue.add(jsonArrayRequest);
        this.activity = activity;
    }
}
