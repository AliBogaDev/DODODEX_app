package com.app.dododexapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.ActionBar;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class config extends AppCompatActivity {
    
    // Variable definitions
    private Button edit_personal_data, close_session, delete_account;
    
    //Variables for switch dark mode and light mode
    private Switch dark_mode;
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_MODE = "dark_mode";

    private SharedPreferences sharedPreferences;
    private String user_token = "";
    private RequestQueue queue;
    private ProgressBar progressBar;
    
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);


        // Get the sharedpreferences
        sharedPreferences =  getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

        // Get the user_token
        user_token = sharedPreferences.getString("token", null);

        // Check if the user_token is null
        if (user_token == null)
            Toast.makeText(config.this, "User Token is null", Toast.LENGTH_SHORT).show();


        // Show the button to go back
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        
        
        // Set values for variables
        queue = Volley.newRequestQueue(config.this);
        
        
        edit_personal_data = findViewById(R.id.edit_personal_data_config_activity);
        dark_mode = findViewById(R.id.dark_mode_config_activity);
        close_session = findViewById(R.id.close_session_config_activity);
        delete_account = findViewById(R.id.delete_account_config_activity);
        
        
        switch_dark_mode();
        edit_personal_data_func();
        close_session_func();
        delete_account_func();
        
    }
    
    private void switch_dark_mode() {
        
        // Load dark mode state from shared preferences
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkMode = preferences.getBoolean(PREF_DARK_MODE, false);
        
        // Set the switch state based on the current dark mode
        dark_mode.setChecked(isDarkMode);
        
        // Handle switch change
        dark_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save dark mode state to shared preferences
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                editor.putBoolean(PREF_DARK_MODE, isChecked);
                editor.apply();
                
                // Dynamically change the app theme
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                
                // Recreate the activity to apply the theme change immediately
                recreate();
            }
        });
        
    }
    private void edit_personal_data_func(){
        
        // When the user clicks on edit personal data, we show a new activity to him
        edit_personal_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent intent = new Intent(config.this, EditarDatos.class);
                startActivity(intent);
            }
        });
        
    }
    private void close_session_func(){
        
        // When the user clisks on close session button, we throw a new request to the server to delete the user`s session
        close_session.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // We inform to the user he is clossing the session and he will be redirect to the login page
                AlertDialog.Builder builder = new AlertDialog.Builder(config.this);
                builder.setMessage("Are you sure you want to log off?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        close_session_request();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                
            }
        });
        
    }
    private void close_session_request(){
        progressBar.setVisibility(View.VISIBLE);
        
        // The request is a DELETE without a body, on backend we dont need it in this case.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                "http://10.0.2.2:8000/session/",
                null,
                new Response.Listener<JSONObject>() {
                    
                    // If the request is ok, we return the user to the login screen
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.INVISIBLE);

                        SharedPreferences.Editor editor = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE).edit();
                        editor.clear().apply();
                        startActivity(new Intent(config.this, Login.class));
                        finishAffinity();
                        
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.VISIBLE);
                  
                        
                        if (error.networkResponse == null)
                            Toast.makeText(config.this, "Can not connect to server", Toast.LENGTH_SHORT).show();
                        else{
                            
                            try {
                                // Get the data from the server
                                String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                                
                                // Change the data to a json object
                                JSONObject errorJson = new JSONObject(responseBody);
                                
                                // Take the error with the correct value, in this case, "error"
                                String errorMessage = errorJson.getString("error_message");
                                
                                Toast.makeText(config.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                                
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            
                        }
                    }
                }) {
                
                // Add a modified header overwriting the getHeaders method
                @Override
                public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token", user_token);
                return headers;
            }
        };
        
        this.queue.add(jsonObjectRequest);
    
    }
    private void delete_account_func(){
        
        delete_account.setOnClickListener(new View.OnClickListener() {

            // We inform to the user he is deletting his account
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(config.this);
                builder.setMessage("Are you sure you want to delete account?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        delete_account_request();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
        
    }
    private void delete_account_request(){
        progressBar.setVisibility(View.VISIBLE);

        // Send de DELETE request to delete the user account, as the other request, we dont need to have a body so it can be null
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.DELETE,
                "http://10.0.2.2:8000/delete_session/",
                null,
                new Response.Listener<JSONObject>() {
                    
                    // If the request is ok, we return the user to the login screen
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.INVISIBLE);

                        SharedPreferences.Editor editor = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE).edit();
                        editor.clear().apply();
                        startActivity(new Intent(config.this, Login.class));
                        finishAffinity();
                        
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.INVISIBLE);
                        if (error.networkResponse == null)
                            Toast.makeText(config.this, "Cant connect to server", Toast.LENGTH_SHORT).show();
                        else{
                            
                            try {
                                // Get the data from the server
                                String responseBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);
                                
                                // Change the data to a json object
                                JSONObject errorJson = new JSONObject(responseBody);
                                
                                // Take the error with the correct value, in this case, "error"
                                String errorMessage = errorJson.getString("error_message");
                                
                                // Showing a message to the user with the error
                                Toast.makeText(config.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                                
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            
                        }

                    }
                }) {
            
            // Add a modified header overwriting the getHeaders method
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token", user_token);
                return headers;
            }
        };
        
        this.queue.add(jsonObjectRequest);

    }
}