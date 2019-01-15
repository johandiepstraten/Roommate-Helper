package com.example.johan.roommatehelper;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupsRequest implements Response.ErrorListener, Response.Listener<JSONArray> {
    private Context context;
    Callback activity;

    public GroupsRequest (Context context)  {
        this.context = context;
    }
    @Override

//    forward possible error message
    public void onErrorResponse(VolleyError error) {
        activity.gotGroupsError(error.getMessage());
    }
    @Override
    public void onResponse(JSONArray response) {
        ArrayList<Group> groupsList = new ArrayList<Group>();

//        extract all needed values from JsonObject and make Group object for each group
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject currentGroup = response.getJSONObject(i);
                int groupId = currentGroup.getInt("id");
                String groupName = currentGroup.getString("GroupName");
                String groupPassword = currentGroup.getString("GroupPassword");
                ArrayList<String> groceryList = new ArrayList<String>();
                JSONArray groceries = currentGroup.getJSONArray("GroupGroceries");
                for (int j = 0; j < groceries.length(); j++) {
                    String grocery = groceries.getString(j);
                    groceryList.add(grocery);
                }
                ArrayList<String> groupMembers = new ArrayList<String>();
                JSONArray members = currentGroup.getJSONArray("GroupMembers");
                for (int k = 0; k < members.length(); k++) {
                    String member = members.getString(k);
                    groupMembers.add(member);
                }
                ArrayList<String> groupTasks = new ArrayList<String>();
                JSONArray tasks = currentGroup.getJSONArray("GroupTasks");
                for (int l = 0; l < tasks.length(); l++) {
                    String task = tasks.getString(l);
                    groupTasks.add(task);
                }
                groupsList.add(new Group(groupId, groupName, groupPassword, groceryList, groupMembers, null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        send list to callback
        activity.gotGroups(groupsList);
    }
    //    Send result of request back to HighscoreActivity
    public interface Callback {
        void gotGroups(ArrayList<Group> groupsList);
        void gotGroupsError(String message);
    }
    //    get jsonarray from right url
    void getGroups(Callback activity)   {
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://ide50-johadiep.legacy.cs50.io:8080/groups", this, this);
        queue.add(jsonArrayRequest);
        this.activity = activity;
    }
}
