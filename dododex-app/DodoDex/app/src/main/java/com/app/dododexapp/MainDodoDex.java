package com.app.dododexapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainDodoDex extends AppCompatActivity {
    private Context main_context = this;

    public Intent main_activity;
    private static String user_token;
    private DinoList dinos;
    private RecyclerView recyclerView;
    private String entorno = "", epoca = "";
    private Boolean pv = true;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dodo_dex);

        this.recyclerView = findViewById(R.id.reycler_view);

        // Obtén una referencia a SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

        // Recupera el token de sesión
        user_token = sharedPreferences.getString("token", null);

        // Verifica si el token de sesión está disponible
        if (user_token == null)
            Toast.makeText(main_context, "User Token is null", Toast.LENGTH_SHORT).show();


        progressbar = findViewById(R.id.progress_bar);

        if (pv)
            obtenerDinosaurios();

        BottomNavigationView bar = findViewById(R.id.bottomNavigation);

        Button btnTriasico = findViewById(R.id.btnTriasico);
        Button btnJurassico = findViewById(R.id.btnJurásico);
        Button btnCretacico = findViewById(R.id.btnCretacico);

        ImageButton btnLupa = findViewById(R.id.buttonLupa);
        ImageButton btnInfo = findViewById(R.id.buttonInfo);
        ImageButton btnConfig = findViewById(R.id.butonConfig);
        ImageButton btnPerfil = findViewById(R.id.buttonPerfil);


        // ****CLICS DE LOS BOTONES****

        //Al clicar el botón nos llevará a la actividad del perfil
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_context, DatosPersonales.class);
                startActivity(intent);
            }
        });

        //Al clicar el botón nos llevará a la actividad de la información
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainDodoDex.this, InformationMain.class);
                startActivity(intent);
            }
        });

        //Al clicar el botón nos llevará a la actividad de la configuración
        btnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_context, config.class);
                startActivity(intent);
            }
        });

        //Al clicar el botón nos llevará a la actividad del buscador
        btnLupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(main_context, Searcher.class);
                startActivity(intent);
            }
        });

        //BOTONES DE LA ÉPOCA A LA QUE PERTENECEN

        //Al clicarlo te lleva a la actividad donde los dinosaurios pertenecen al Triásico
        btnTriasico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( main_context, era_options_activity.class);
                startActivity(intent);
            }
        });

        //Al clicarlo te lleva a la actividad donde los dinosaurios pertenecen al Jurásico
        btnJurassico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( main_context, era_options_activity.class);
                startActivity(intent);
            }
        });

        //Al clicarlo te lleva a la actividad donde los dinosaurios pertenecen al Cretácico
        btnCretacico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( main_context, era_options_activity.class);
                startActivity(intent);
            }
        });

        //***NavigationBar***
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.item1) {
                    // Inicio
                    entorno = ""; // Sin filtrar por entorno
                } else if (item.getItemId() == R.id.item2) {
                    // Tierra
                    entorno = "Tierra";
                } else if (item.getItemId() == R.id.item3) {
                    // Agua
                    entorno = "Agua";
                } else if (item.getItemId() == R.id.item4) {
                    // Aire
                    entorno = "Aire";
                }
                obtenerDinosaurios();
                return true;
            }
        });

    }
    private void obtenerDinosaurios() {

        List<DinoData> dinosauriosFiltrados = new ArrayList<>();
        String url= "http://10.0.2.2:8000/search_dinosaur/";
        progressbar.setVisibility(View.VISIBLE);

        if (!entorno.equalsIgnoreCase(""))
                url = "http://10.0.2.2:8000/search_dinosaur/?dinosaur_environment=" + entorno;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Filtrar dinosaurios por entorno
                        try {
                            progressbar.setVisibility(View.INVISIBLE);
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject jsonElement = response.getJSONObject(i);
                                DinoData aDino = new DinoData(jsonElement);
                                dinosauriosFiltrados.add(aDino);

                            }

                            DinoAdapter myAdapter = new DinoAdapter(dinosauriosFiltrados, main_context);
                            recyclerView.setAdapter(myAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(main_context));

                        }catch (JSONException e){Toast.makeText(main_context, "Error: "+e, Toast.LENGTH_SHORT).show();}
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressbar.setVisibility(View.INVISIBLE);
                        // Manejar errores de la petición
                        if (error.networkResponse == null)
                            Toast.makeText(main_context, "Error" + error, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(main_context, "Dinosaurio no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
        ){

            // Cabecera personalizada donde se manda el token de usuario
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token", user_token);
                return headers;
            }};

        // Agregar la solicitud a la cola de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}