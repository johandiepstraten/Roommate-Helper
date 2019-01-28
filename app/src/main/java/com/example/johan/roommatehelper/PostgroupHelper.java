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

public class PostgroupHelper implements Response.ErrorListener, Response.Listener {

//    Declare variables to use throughout helper.
    String groupName;
    String groupPassword;
    ArrayList<String> members;
    ArrayList<String> groceries;
    ArrayList<String> tasks;
    ArrayList<String> memberIds;
    PostgroupHelper.CallbackPost activity;

//    Request right online json file to post group in.
    public PostgroupHelper(String groupName, String groupPassword, ArrayList<String> members,
                           ArrayList<String> memberIds, Context context,
                           PostgroupHelper.CallbackPost cb) {
        this.groupName = groupName;
        this.groupPassword = groupPassword;
        this.members = members;
        this.activity = cb;
        this.memberIds = memberIds;
        RequestQueue queue = Volley.newRequestQueue(context);
        PostgroupHelper.PostRequest request = new PostgroupHelper.PostRequest(Request.Method.POST,
                "https://ide50-johadiep.legacy.cs50.io:8080/groups", this,
                this);
        queue.add(request);
    }

//     Print possible error.
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        activity.gotHelperError(error.getMessage());
    }

//     Inform gotHelper if request was succesfull.
    @Override
    public void onResponse(Object response) {
        activity.gotHelper("Succes!");
    }


//    Inform requesting activity with result of request through Callback.
    public interface CallbackPost {
        void gotHelper(String message);
        void gotHelperError(String message);
    }

    public class PostRequest extends StringRequest {

//       Constructor.
        public PostRequest(int method, String url, Response.Listener<String> listener,
                           Response.ErrorListener errorListener) {
            super(method, url, listener, errorListener);
        }

//       Method to supply parameters to the request.
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

