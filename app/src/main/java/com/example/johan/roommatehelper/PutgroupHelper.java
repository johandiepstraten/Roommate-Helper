package com.example.johan.roommatehelper;

import android.content.Context;
import android.util.Log;

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
    Group group;
    String groupName;
    String groupPassword;
    ArrayList<String> members;
    ArrayList<String> groceries;
    ArrayList<String> tasks;
    PutgroupHelper.CallbackPut activity;

    //    request right online json file to put score in
    public PutgroupHelper(Group group, Context context, PutgroupHelper.CallbackPut cb) {
        this.group = group;
        this.activity = cb;
        groupName = group.getGroupName();
        groupPassword = group.getGroupPassword();
        members = group.getGroupMembers();
        groceries = group.getGroceryList();
//        tasks = group.getGroupTasks();
        int groupId = group.getGroupId();
        String groupIdString = Integer.toString(groupId);
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("hierhebbenwe", "PutgroupHelper https://ide50-johadiep.legacy.cs50.io:8080/groups/" + groupIdString);
//        id van user moet ook meegegeven worden als 8080/users/userid
        PutgroupHelper.PutRequest request = new PutgroupHelper.PutRequest(Request.Method.PUT, "https://ide50-johadiep.legacy.cs50.io:8080/groups/" + groupIdString, this, this);
        queue.add(request);
        Log.d("hierhebbenwe", "Putgrouphelper komen we hier?");
    }

    //     print possible error
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        activity.gotgroupputHelperError(error.getMessage());
    }

    //     inform gotHelper if request was succesfull
    @Override
    public void onResponse(Object response) {
        activity.gotgroupputHelper("Succes!");
    }

    //    inform resultactivity with result of request through Callback
    public interface CallbackPut {
        void gotgroupputHelper(String message);

        void gotgroupputHelperError(String message);
    }

    public class PutRequest extends StringRequest {

        //       Constructor
        public PutRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

        //       Method to supply parameters to the request
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
            return params;
        }
    }
}
