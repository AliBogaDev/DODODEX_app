package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class era_options_activity extends AppCompatActivity {

    Context context = this;
    private RequestQueue queue;
    private ConstraintLayout main_layout;
    private dino_list dino_list;
    private RecyclerView recycler_view;
    private ProgressBar progress_bar;
    private String period="", habitat="";
    private static String user_token;
    public Intent main_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_era_options);
        Context main_context =this;
        main_activity = getIntent();
        this.queue = Volley.newRequestQueue(this);
        this.main_layout = findViewById(R.id.dino_view_activity);
        this.progress_bar = findViewById(R.id.progress_bar);
        this.recycler_view = findViewById(R.id.recycler_view_era_options);
        Spinner period_spinner = findViewById(R.id.period_spinner);
        Spinner habitat_spinner = findViewById(R.id.habitat_spinner);
        ArrayAdapter<CharSequence> period_adapter = ArrayAdapter.createFromResource(this, R.array.periods, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> habitat_adapter = ArrayAdapter.createFromResource(this, R.array.habitats, android.R.layout.simple_spinner_item);
        period_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        habitat_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        period_spinner.setAdapter(period_adapter);
        habitat_spinner.setAdapter(habitat_adapter);

        // Show the button to go back
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);


        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);
        user_token = sharedPreferences.getString("token", null);
        if (user_token == null) {
            Toast.makeText(main_context, "User Token is null", Toast.LENGTH_SHORT).show();
        }

        period_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                period = parent.getItemAtPosition(position).toString();
                get_dinos();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        habitat_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                habitat = parent.getItemAtPosition(position).toString();
                get_dinos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void get_dinos() {
        String url = "http://10.0.2.2:8000/search_dinosaur/?epoch=";

        if (period.equalsIgnoreCase("Jurásico")) {
            url += "Jurasico";
        }else if(period.equalsIgnoreCase("Cretácico")) {
            url += "Cretacico";
        }else if(period.equalsIgnoreCase("Triásico")) {
            url += "Triasico";
        }

        if (habitat.equalsIgnoreCase("Acuático")) {
            url += "&dinosaur_environment=Agua";
        }else if(habitat.equalsIgnoreCase("Aéreo")) {
            url += "&dinosaur_environment=Aire";
        }else if(habitat.equalsIgnoreCase("Terrestre")) {
            url += "&dinosaur_environment=Tierra";
        }

        progress_bar.setVisibility(View.VISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progress_bar.setVisibility(View.INVISIBLE);
                        set_dinos(new dino_list(response));
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
                headers.put("user-token", user_token);
                return headers;
            }
        };
        this.queue.add(request);
    }

    public void set_dinos(dino_list dinos) {
        this.dino_list = dinos;
        dino_adapter my_adapter = new dino_adapter(dino_list, this);
        recycler_view.setAdapter(my_adapter);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    public dino_list get_dino_list() { return dino_list; }
}
