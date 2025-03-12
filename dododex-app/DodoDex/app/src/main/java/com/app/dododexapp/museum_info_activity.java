package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class museum_info_activity extends AppCompatActivity {

    private Context museum_info_context = this;
    private ConstraintLayout museum_info_layout;
    private ProgressBar progress_bar;
    private RequestQueue queue;
    private Intent intent;
    private int id;
    private TextView text_view_nombre;
    private TextView text_view_direccion;
    private TextView text_view_info;
    private ImageView imagen_museo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show the button to go back
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);


        intent = getIntent();
        id = intent.getIntExtra("id", -1);
        setContentView(R.layout.activity_museum_info);
        this.museum_info_layout = findViewById(R.id.museum_info_activity);
        this.progress_bar = findViewById(R.id.progress_bar);
        this.queue = Volley.newRequestQueue(this);
        text_view_nombre = findViewById(R.id.nombre_museo);
        text_view_info = findViewById(R.id.texto_Descripcion_General);
        text_view_direccion = findViewById(R.id.texto_Direccion);
        imagen_museo = findViewById(R.id.imagen_museo);
        get_museums();
    }

    private void get_museums() {
        progress_bar.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                "http://10.0.2.2:8000/museums/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        progress_bar.setVisibility(View.INVISIBLE);
                        museum_list list = new museum_list(response);
                        for (museum museo : list.getMuseums()){
                            if (museo.get_id() == id){
                                setmuseo(museo);
                                break;
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress_bar.setVisibility(View.INVISIBLE);
                if(error.networkResponse == null) {
                    Snackbar.make(museum_info_layout, "List error received", Snackbar.LENGTH_SHORT).show();
                } else {
                    int serverCode = error.networkResponse.statusCode;
                    Snackbar.make(museum_info_layout, "Unable to establish connection", Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(museum_info_context, "Server replied with " + serverCode, Toast.LENGTH_SHORT).show();
                }
            }
        }
        ){
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token", "f2d2c1c566f3cfe66b48");
                return headers;
            }
        };
        this.queue.add(request);
    }

    private void setmuseo(museum museum){

        text_view_nombre.setText(museum.get_name());
        text_view_direccion.setText(museum.get_locationURL());
        text_view_info.setText(museum.get_description());
        Glide.with(this).load(museum.get_imageURL()).into(imagen_museo);
    }
}