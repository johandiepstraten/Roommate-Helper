/*
Johan Diepstraten 10774920
This request loads in all information about existing groups and returns a list of group objects.
*/
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
import java.util.Arrays;
import java.util.List;

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
                ArrayList<Task> groupTasks = new ArrayList<Task>();
                String tasks = currentGroup.getString("GroupTasks");

                JSONArray tasksArray = new JSONArray(tasks);
                for (int l = 0; l < tasksArray.length(); l++) {
                    String currentTask = tasksArray.getString(l);
                    String newCurrentTask = currentTask.substring(1, currentTask.length()-1);
                    List<String> taskArray = new ArrayList<String>(Arrays.asList(newCurrentTask
                            .split(", ")));
                    String taskName = taskArray.get(0);
                    String taskDescription = taskArray.get(1);
                    String taskDaysString = taskArray.get(2);
                    int taskDays = Integer.parseInt(taskDaysString);
                    String responsibleUserString = taskArray.get(3);
                    int responsibleUser = Integer.parseInt(responsibleUserString);
                    String initialTimeString = taskArray.get(4);
                    long initialTime = Long.parseLong(initialTimeString);
                    String finishTimeString = taskArray.get(5);
                    long finishTime = Long.parseLong(finishTimeString);
                    Task task = new Task(taskName, taskDescription, taskDays, responsibleUser,
                            initialTime, finishTime);
                    groupTasks.add(task);
                }
                ArrayList<Integer> memberIds = new ArrayList<Integer>();
                String memberIdsList = currentGroup.getString("MemberIds");
                JSONArray membersIdArray = new JSONArray(memberIdsList);

                for (int m = 0; m < membersIdArray.length(); m++) {
                    String memberIdString = membersIdArray.getString(m);
                    int memberId = Integer.parseInt(memberIdString);
                    memberIds.add(memberId);
                }
                groupsList.add(new Group(groupId, groupName, groupPassword, groceryList, groupMembers, groupTasks, memberIds));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Send list to callback.
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
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest("https://ide50-johadiep.legacy.cs50.io:8080/groups",
                        this, this);
        queue.add(jsonArrayRequest);
        this.activity = activity;
    }
}
