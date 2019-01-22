package com.example.johan.roommatehelper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PutuserHelper implements Response.ErrorListener, Response.Listener {
    User user;
    String username;
    String password;
    int group;
    CallbackPut activity;

    //    request right online json file to put score in
    public PutuserHelper(User user, Context context, CallbackPut cb) {
        this.user = user;
        this.activity = cb;
        int userId = user.getUserId();
        username = user.getUser_name();
        password = user.getUser_password();
        group = user.getGroup_id();
        String userIdString = Integer.toString(userId);
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("hierhebbenwe", "PutuserHelper https://ide50-johadiep.legacy.cs50.io:8080/users/" + userIdString);
//        id van user moet ook meegegeven worden als 8080/users/userid
        PutuserHelper.PutRequest request = new PutuserHelper.PutRequest(Request.Method.PUT, "https://ide50-johadiep.legacy.cs50.io:8080/users/" + userIdString, this, this);
        queue.add(request);
        Log.d("hierhebbenwe", "Putuserhelper komen we hier?");
    }

    //     print possible error
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        activity.gotputHelperError(error.getMessage());
    }

    //     inform gotHelper if request was succesfull
    @Override
    public void onResponse(Object response) {
        activity.gotputHelper("Succes!");
    }

    //    inform resultactivity with result of request through Callback
    public interface CallbackPut {
        void gotputHelper(String message);

        void gotputHelperError(String message);
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
            Log.d("hierhebbenwe", "putuserhelper hebben we de goede groupid?" + group);
            params.put("Username", username);
            params.put("Password", password);
            params.put("Group", Integer.toString(group));
            return params;
        }
    }
}
