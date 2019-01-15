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
    String username;
    String password;
    CallbackPut activity;

    //    request right online json file to put score in
    public PutuserHelper(String username, String password, Context context, CallbackPut cb) {
        this.username = username;
        this.password = password;
        this.activity = cb;
        RequestQueue queue = Volley.newRequestQueue(context);
//        id van user moet ook meegegeven worden als 8080/users/userid
        PutuserHelper.PutRequest request = new PutuserHelper.PutRequest(Request.Method.PUT, "https://ide50-johadiep.legacy.cs50.io:8080/users", this, this);
        queue.add(request);
    }

    //     print possible error
    @Override
    public void onErrorResponse(VolleyError error) {
        error.printStackTrace();
        activity.gotHelperError(error.getMessage());
    }

    //     inform gotHelper if request was succesfull
    @Override
    public void onResponse(Object response) {
        activity.gotHelper("Succes!");
    }

    //    inform resultactivity with result of request through Callback
    public interface CallbackPut {
        void gotHelper(String message);

        void gotHelperError(String message);
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
            params.put("Username", username);
            params.put("Password", password);
            params.put("Group", null);
            return params;
        }
    }
}
