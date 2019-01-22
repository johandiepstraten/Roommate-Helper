package com.example.johan.roommatehelper;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//  Request all groups from online JSON file.
public class GroupsRequest implements Response.ErrorListener, Response.Listener<JSONArray> {

//    Declare variables to be used throughout the activity.
    private Context context;
    Callback activity;

    public GroupsRequest (Context context)  {
        this.context = context;
    }
    @Override

//    Forward possible error message.
    public void onErrorResponse(VolleyError error) {
        activity.gotGroupsError(error.getMessage());
    }
    @Override
    public void onResponse(JSONArray response) {
        ArrayList<Group> groupsList = new ArrayList<Group>();

//        Extract all needed values from JsonObject and make Group object for each group.
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject currentGroup = response.getJSONObject(i);
                int groupId = currentGroup.getInt("id");
                String groupName = currentGroup.getString("GroupName");
                String groupPassword = currentGroup.getString("GroupPassword");
                ArrayList<String> groceryList = new ArrayList<String>();
//                DIT GAAT MIS, DE ARRAYS STAAN ER IN ALS STRINGS, ZE MOETEN ER OF ALS ARRAYLIST IN OF DE STRING MOET MMAKKELIJK NAAR ARRAYLIST GECONVERT WORDEN.
                String groceries = currentGroup.getString("GroupGroceries");
                JSONArray groceriesArray = new JSONArray(groceries);
                for (int j = 0; j < groceriesArray.length(); j++) {
                    String grocery = groceriesArray.getString(j);
                    groceryList.add(grocery);
                }
                ArrayList<String> groupMembers = new ArrayList<String>();
                String members = currentGroup.getString("GroupMembers");
                JSONArray membersArray = new JSONArray(members);

                for (int k = 0; k < membersArray.length(); k++) {
                    String member = membersArray.getString(k);
                    groupMembers.add(member);
                }
                ArrayList<String> groupTasks = new ArrayList<String>();
                String tasks = currentGroup.getString("GroupTasks");
                JSONArray tasksArray = new JSONArray(tasks);
                for (int l = 0; l < tasksArray.length(); l++) {
                    String task = tasksArray.getString(l);
                    groupTasks.add(task);
                }
                groupsList.add(new Group(groupId, groupName, groupPassword, groceryList, groupMembers, null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("hierhebbenwe", "GroupsRequest fout" + e);
        }
//        Send list to callback.
        Log.d("hierhebbenwe", "GroupsRequest we gaan naar gotGroups");
        activity.gotGroups(groupsList);
    }
//    Send list of groups or error message back to activity that sent the request.
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
