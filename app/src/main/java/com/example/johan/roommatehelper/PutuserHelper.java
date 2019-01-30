/*
Johan Diepstraten 10774920
Here an existing user is updated in the online JSON file.
*/
package com.example.johan.roommatehelper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PutuserHelper implements Response.ErrorListener, Response.Listener {

//    Declare variables to use throughout helper.
    User user;
    String username;
    String password;
    int group;
    CallbackPut activity;

//    request right online json file to update user in.
//    Prepare user data to the right format to send to JSONfile.
    public PutuserHelper(User user, Context context, CallbackPut cb) {
        this.user = user;
        this.activity = cb;
        int userId = user.getUserId();
        username = user.getUser_name();
        password = user.getUser_password();
        group = user.getGroup_id();
        String userIdString = Integer.toString(userId);
        RequestQueue queue = Volley.newRequestQueue(context);
        PutuserHelper.PutRequest request = new PutuserHelper.PutRequest(Request.Method.PUT,
                "https://ide50-johadiep.legacy.cs50.io:8080/users/" + userIdString,
                this, this);
        queue.add(request);
    }

//     Print possible error.
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        activity.gotputHelperError(error.getMessage());
    }

//     Inform requesting activity if update was succesfull.
    @Override
    public void onResponse(Object response) {
        activity.gotputHelper("Succes!");
    }

    public interface CallbackPut {
        void gotputHelper(String message);
        void gotputHelperError(String message);
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
            params.put("Username", username);
            params.put("Password", password);
            params.put("Group", Integer.toString(group));
            return params;
        }
    }
}
