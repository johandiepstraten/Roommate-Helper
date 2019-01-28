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

public class PostuserHelper implements Response.ErrorListener, Response.Listener {

//
    String username;
    String password;
    CallbackPost activity;

//    Request right online json file to post user in.
    public PostuserHelper(String username, String password, Context context, CallbackPost cb) {
        this.username = username;
        this.password = password;
        this.activity = cb;
        RequestQueue queue = Volley.newRequestQueue(context);
        PostRequest request = new PostRequest(Request.Method.POST,
                "https://ide50-johadiep.legacy.cs50.io:8080/users", this,
                this);
        queue.add(request);
    }

//     Print possible error.
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        activity.gotHelperError(error.getMessage());
    }

//     Inform gotHelper if post was succesfull.
    @Override
    public void onResponse(Object response) {
        activity.gotHelper("Succes!");
    }

//    Inform requesting activity with result of post through Callback.
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

//       Method to supply parameters to the update.
        @Override
        protected Map<String, String> getParams() {

            Map<String, String> params = new HashMap<>();
            params.put("Username", username);
            params.put("Password", password);
            params.put("Group", "0");
            return params;
        }
    }
}
