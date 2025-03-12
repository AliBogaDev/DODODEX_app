package com.app.dododexapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class json_object_request_with_authentication_dino_found extends JsonObjectRequest {

    private final Context context;

    public json_object_request_with_authentication_dino_found(String url, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener, Context context) {
        super(url, listener, errorListener);
        this.context = context;
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        SharedPreferences preferences = context.getSharedPreferences("DODODEX_APP_PREFS", Context.MODE_PRIVATE);
        String session_token = preferences.getString("VALID_TOKEN", null);

        if (session_token == null) {
            throw new AuthFailureError();
        }

        HashMap<String, String>my_headers = new HashMap<>();
        my_headers.put("SessionToken", session_token);
        return my_headers;
    }
}
