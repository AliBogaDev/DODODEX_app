package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class museum_view_activity extends AppCompatActivity {

    private Context main_context = this;
    private RequestQueue queue;
    private ConstraintLayout main_layout;
    private museum_list museum_list;
    private RecyclerView recycler_view;
    private ProgressBar progress_bar;
    private static String user_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum_view);
        Context main_context = this;
        this.queue = Volley.newRequestQueue(this);
        this.main_layout = findViewById(R.id.museumViewActivity);
        this.progress_bar = findViewById(R.id.progressBar);
        this.recycler_view = findViewById(R.id.museums_recycler_view);
        get_museums();

        // Show the button to go back
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);


        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);
        user_token = sharedPreferences.getString("token", null);
        if (user_token == null) {
            Toast.makeText(main_context, "User Token is null", Toast.LENGTH_SHORT).show();
        }

    }
        private void get_museums () {
            progress_bar.setVisibility(View.VISIBLE);
            List<museum> museums = new ArrayList<>();

            JsonArrayRequest request = new JsonArrayRequest(
                    Request.Method.GET,
                    "http://10.0.2.2:8000/museums/",
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            progress_bar.setVisibility(View.INVISIBLE);
                            com.app.dododexapp.museum_list pppp = new museum_list(response);
                            set_museum_list(pppp);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progress_bar.setVisibility(View.INVISIBLE);
                    if (error.networkResponse == null) {
                        Snackbar.make(main_layout, "List error received", Snackbar.LENGTH_SHORT).show();
                    } else {
                        int serverCode = error.networkResponse.statusCode;
                        Snackbar.make(main_layout, "Unable to establish connection", Snackbar.LENGTH_SHORT).show();
                        Toast.makeText(main_context, "Server replied with " + serverCode, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            ) {
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("user-token", user_token);
                    return headers;
                }
            };
            this.queue.add(request);
        }

        public void set_museum_list (museum_list museums){
            this.museum_list = museums;
            museum_adapter my_adapter = new museum_adapter(this.museum_list, this);
            recycler_view.setAdapter(my_adapter);
            recycler_view.setLayoutManager(new LinearLayoutManager(main_context));
        }

        public com.app.dododexapp.museum_list get_museum_list () {
            return museum_list;
        }

    }
