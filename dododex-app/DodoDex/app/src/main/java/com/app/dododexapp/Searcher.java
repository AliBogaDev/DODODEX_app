package com.app.dododexapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Searcher extends AppCompatActivity {

    private EditText searchTextDinosaurName;
    private Spinner epochSpinner, dietSpinner, environmentSpinner;
    private CheckBox epochCheck, dietCheck, environmentCheck;
    private RequestQueue queue;
    private Context context = this;
    private static String user_token;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searcher);

        // Show the button to go back
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        // Obtén una referencia a SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

        // Recupera el token de sesión
        user_token = sharedPreferences.getString("token", null);

        // Verifica si el token de sesión está disponible
        if (user_token == null)
            Toast.makeText(context, "User Token is null", Toast.LENGTH_SHORT).show();

        progressbar = findViewById(R.id.progress_bar);
        progressbar.setVisibility(View.INVISIBLE);

        searchTextDinosaurName = findViewById(R.id.searchTextDinosaurName);

        // Epoch Spinner configuration
        epochSpinner = findViewById(R.id.EpochSpinner);
        ArrayAdapter<CharSequence> epochAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.epoch_examples,
                android.R.layout.simple_spinner_item
        );
        epochAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        epochSpinner.setAdapter(epochAdapter);

        // Diet Spinner Configuration
        dietSpinner = findViewById(R.id.DietSpinner);
        ArrayAdapter<CharSequence> dietAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.diet_examples,
                android.R.layout.simple_spinner_item
        );
        dietAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dietSpinner.setAdapter(dietAdapter);

        // Enviroment Spinner Configuration
        environmentSpinner = findViewById(R.id.EnviromentSpinner);
        ArrayAdapter<CharSequence> environmentAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.enviroment_examples,
                android.R.layout.simple_spinner_item
        );
        environmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        environmentSpinner.setAdapter(environmentAdapter);

        // Checkboxes Configuration
        epochCheck = findViewById(R.id.EpochCheck);
        dietCheck = findViewById(R.id.DietCheck);
        environmentCheck = findViewById(R.id.EnviromentCheck);


        // Search button configuration
        Button searchButton = findViewById(R.id.searchButton);
        queue = Volley.newRequestQueue(this);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);

                // Build URL for the petition
                String apiUrl = "http://10.0.2.2:8000/search_dinosaur/?";
                StringBuilder queryString = new StringBuilder();

                // Add the filters to the URL if the checkboxes are marked
                if (epochCheck.isChecked()) {
                    queryString.append("epoch=").append(epochSpinner.getSelectedItem().toString()).append("&");
                }

                if (dietCheck.isChecked()) {
                    queryString.append("feeding=").append(dietSpinner.getSelectedItem().toString()).append("&");
                }

                if (environmentCheck.isChecked()) {
                    queryString.append("dinosaur_environment=").append(environmentSpinner.getSelectedItem().toString()).append("&");
                }

                String dinosaurName = searchTextDinosaurName.getText().toString().trim();
                if (!dinosaurName.isEmpty()) {
                    queryString.append("name=").append(dinosaurName).append("&");
                }
                if (queryString.length() > 0) {
                    queryString.deleteCharAt(queryString.length() - 1);
                }

                String fullUrl = apiUrl + queryString;

                JsonArrayRequest request = new JsonArrayRequest(
                        Request.Method.GET,
                        fullUrl,
                        null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                progressbar.setVisibility(View.INVISIBLE);
                                // Handling the server response
                                //String jsonResponse = response.toString();
                                //Intent intent = new Intent(Searcher.this, dino_found_activity.class);
                                //intent.putExtra("server_response: ", jsonResponse);
                                //startActivity(intent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            progressbar.setVisibility(View.INVISIBLE);
                           Toast.makeText(Searcher.this, "Connection could not be established", Toast.LENGTH_LONG).show();
                        } else {
                            progressbar.setVisibility(View.INVISIBLE);
                            int serverCode = error.networkResponse.statusCode;
                            if (serverCode == 404)  {
                                  //Intent intent = new Intent(Searcher.this, dino_found_activity.class);
                                //startActivity(intent);
                            } else {
                                Toast.makeText(Searcher.this, "Server KO: " + serverCode, Toast.LENGTH_LONG).show();
                            }
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
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(request);


            }
        });
    }
}
