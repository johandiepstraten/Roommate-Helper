/*
Johan Diepstraten 10774920
Here an existing group is updated in the online JSON file.
*/
package com.example.johan.roommatehelper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PutgroupHelper implements Response.ErrorListener, Response.Listener {

//    Declare variables to use throughout helper.
    Group group;
    String groupName;
    String groupPassword;
    ArrayList<String> members;
    ArrayList<String> groceries;
    ArrayList<String> tasks = new ArrayList<String>();
    ArrayList<String> memberIds = new ArrayList<String>();
    PutgroupHelper.CallbackPut activity;

//    Request right online json file to update group in.
//    Set all information from the group object to the right format to be able to put in in the
//    online JSONfile.
    public PutgroupHelper(Group group, Context context, PutgroupHelper.CallbackPut cb) {
        this.group = group;
        this.activity = cb;
        groupName = group.getGroupName();
        groupPassword = group.getGroupPassword();
        members = group.getGroupMembers();
        groceries = group.getGroceryList();
        ArrayList<Task> allTasks = group.getGroupTasks();
        for(int i = 0; i < allTasks.size(); i++) {
            ArrayList<String> currentTask = new ArrayList<String>();
            Task thisTask = allTasks.get(i);
            String taskName = thisTask.getTaskName();
            String taskDescription = thisTask.getTaskDescription();
            int taskDays = thisTask.getTaskDays();
            String taskDaysString = Integer.toString(taskDays);
            int responsibleUser = thisTask.getResponsibleUser();
            String responsibleUserString = Integer.toString(responsibleUser);
            long initialTime = thisTask.getInitialTime();
            String initialTimeString = Long.toString(initialTime);
            long finishTime = thisTask.getFinishTime();
            String finishTimeString = Long.toString(finishTime);
            currentTask.add(taskName);
            currentTask.add(taskDescription);
            currentTask.add(taskDaysString);
            currentTask.add(responsibleUserString);
            currentTask.add(initialTimeString);
            currentTask.add(finishTimeString);
            tasks.add(currentTask.toString());
        }
        ArrayList<Integer> memberIdList = group.getMemberIds();
        for(int j = 0; j<memberIdList.size(); j++) {
            int currentMemberId = memberIdList.get(j);
            String currentMemberIdString = Integer.toString(currentMemberId);
            memberIds.add(currentMemberIdString);
        }
        int groupId = group.getGroupId();
        String groupIdString = Integer.toString(groupId);
        RequestQueue queue = Volley.newRequestQueue(context);
        PutgroupHelper.PutRequest request = new PutgroupHelper.PutRequest(Request.Method.PUT,
                "https://ide50-johadiep.legacy.cs50.io:8080/groups/" + groupIdString,
                this, this);
        queue.add(request);
    }

//     Print possible error.
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        activity.gotgroupputHelperError(error.getMessage());
    }

//     Inform requesting activity if update was succesfull.
    @Override
    public void onResponse(Object response) {
        activity.gotgroupputHelper("Succes!");
    }

    public interface CallbackPut {
        void gotgroupputHelper(String message);
        void gotgroupputHelperError(String message);
    }

    public class PutRequest extends StringRequest {

//       Constructor.
        public PutRequest(int method, String url, Response.Listener<String> listener,
                          Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

//       Method to supply parameters to the update.
        @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<>();
            params.put("GroupName", groupName);
            params.put("GroupPassword", groupPassword);
            JSONArray groceryArray = new JSONArray(groceries);
            params.put("GroupGroceries", groceryArray.toString());
            JSONArray memberArray = new JSONArray(members);
            params.put("GroupMembers", memberArray.toString());
            JSONArray taskArray = new JSONArray(tasks);
            params.put("GroupTasks", taskArray.toString());
            JSONArray memberIdArray = new JSONArray(memberIds);
            params.put("MemberIds", memberIdArray.toString());
            return params;
        }
    }
}
