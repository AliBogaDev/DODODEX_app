package com.app.dododexapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Favourites extends AppCompatActivity {
    private Context context = this;
    public Intent main_activity;
    private static String user_token;
    private RecyclerView recyclerView;
    private ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favourites);
        main_activity = getIntent();
        // Obtén una referencia a SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

        // Recupera el token de sesión
        user_token = sharedPreferences.getString("token", null);

        // Verifica si el token de sesión está disponible
        if (user_token == null)
            Toast.makeText(context, "User Token is null", Toast.LENGTH_SHORT).show();

        progressbar = findViewById(R.id.progress_bar);

        // Show the button to go back
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);



        ImageButton profileButton = findViewById(R.id.profile_button);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Favourites.this, DatosPersonales.class);
                startActivity(intent);
            }
        });
        this.recyclerView = findViewById(R.id.recyclerView);
        List<dinosaur> dinosaur = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.2.2:8000/favourites/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progressbar.setVisibility(View.INVISIBLE);
                        DinosaurList dinosaurList = new DinosaurList(response); // Instantiate DinosaurList with the JSONArray response

                        Intent intent = getIntent();

                        // Recuperar la información del dinosaurio del Intent
                        String name = intent.getStringExtra("name");
                        String height = intent.getStringExtra("height");
                        String habitat = intent.getStringExtra("habitat");
                        String weight = intent.getStringExtra("weight");
                        String feeding = intent.getStringExtra("feeding");
                        String description = intent.getStringExtra("description");
                        String image = intent.getStringExtra("image");

                        FavouritesAdapter myAdapter = new FavouritesAdapter(dinosaurList); // Pass the list from DinosaurList to the adapter
                        recyclerView.setAdapter(myAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context)); // Use 'context' instead of 'this'

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressbar.setVisibility(View.INVISIBLE);
                if (error.networkResponse == null) {
                    Toast.makeText(context, "Connection could not be established", Toast.LENGTH_LONG).show();
                } else {
                    int serverCode = error.networkResponse.statusCode;
                    Toast.makeText(context, "Server error: " + serverCode, Toast.LENGTH_LONG).show();
                }
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token", user_token);
                return headers;
            }};
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

}
