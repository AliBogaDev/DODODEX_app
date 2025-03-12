package com.app.dododexapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import com.android.volley.toolbox.Volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dino_found_activity extends AppCompatActivity {

    //private ListView list_view;
    private dino_list_dino_found dino_list_dino_found;
    private dinosaur dinosaur;
    private RecyclerView recycler_view;
    ArrayAdapter<String> array_adapter;
    ProgressBar progress_bar;
    private RequestQueue queue;
    private static String user_token;
    private ConstraintLayout main_layout;
    private SearchView search_view;
    //Context main_context;
    Context context = this;
    private List<dinosaur> dinosaur_filtered_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dino_found);
        search_view = findViewById(R.id.search_view);
        //list_view = findViewById(R.id.list_view);
        array_adapter = new ArrayAdapter<String>(this, R.layout.recycler_cell);
        //list_view.setAdapter(array_adapter);
        progress_bar = findViewById(R.id.progress_bar);
        recycler_view = findViewById(R.id.recycler_view);
        main_layout = findViewById(R.id.main_layout);
        queue = Volley.newRequestQueue(this);
        dinosaur_filtered_list = new ArrayList<>();
        get_dinos();

        search_view.clearFocus();
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter_list(newText);
                return false;
            }
        });

     /*   SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);
        user_token = sharedPreferences.getString("token", null);
        if (user_token == null) {
            Toast.makeText(main_context, "User Token is null", Toast.LENGTH_SHORT).show();
        }*/
    }

    private void filter_list(String newText) {
        List<dinosaur> filtered_list = new ArrayList<>();
        dinosaur_filtered_list.clear();
        List<dinosaur> full_dinosaur_list = dino_list_dino_found.get_dinos();
        dinosaur_filtered_list.addAll(full_dinosaur_list);

        for(dinosaur dinosaur : dinosaur_filtered_list) {
            if(dinosaur.get_name().toLowerCase().contains(newText.toLowerCase())) {
                filtered_list.add(dinosaur);
            }
        }
        dino_adapter_dino_found adapter = (dino_adapter_dino_found) recycler_view.getAdapter();
        if(adapter != null) {
            adapter.set_filtered_list(filtered_list);
            }
    }

    private void get_dinos() {

        progress_bar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.2.2:8000/search_dinosaur",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progress_bar.setVisibility(View.INVISIBLE);
                        //Snackbar.make(main_layout, "List received", Snackbar.LENGTH_SHORT).show();
                        set_dinos(new dino_list_dino_found(response));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_bar.setVisibility(View.INVISIBLE);
                if(error.networkResponse == null){
                    Snackbar.make(main_layout, "Error conex", Snackbar.LENGTH_SHORT).show();
                } else {
                    int serverCode = error.networkResponse.statusCode;
                    Snackbar.make(main_layout, "Unable to establish connection", Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(context, "Server replied with "+serverCode, Toast.LENGTH_SHORT).show();
                }
            }
        }
        ){
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token","f2d2c1c566f3cfe66b48");
                return headers;
            }
        };
        this.queue.add(request);
    }

    public void set_dinos(dino_list_dino_found dinos) {
        this.dino_list_dino_found = dinos;
        dino_adapter_dino_found my_adapter = new dino_adapter_dino_found(dino_list_dino_found, this);
        recycler_view.setAdapter(my_adapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

}