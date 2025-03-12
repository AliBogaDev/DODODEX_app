package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class dinasour_information_main extends AppCompatActivity{


    private Context context= this;
    ViewPager viewPager;
    dinosaur_adapter_information adapter;

    private int idDinoOriginal;
    private RequestQueue queue;
    public Intent main_activity;
    private static  String user_token;
    private boolean after_original_dino_did_load, before_original_dino_did_load, stop;

    private String IpPc="http://10.0.2.2:8000/dinosaurs/";

    ArrayList<dinosaur_information>dinosaurs = new ArrayList<>();

    /**
     * Sólo para cargar dinosaurios que no son el original
     * @param idDinosaur
     */
    private  void  requestDinosaur( int  idDinosaur, load_dinosaur_listener listener){
        JsonObjectRequest request  = new JsonObjectRequest(
                Request.Method.GET,
                IpPc+idDinosaur+"/",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            dinosaur_information dinosaur = new dinosaur_information(response);
                            meterDinoEnArray(dinosaur);

                            if (listener != null)
                                listener.after_load_dinosaur(true);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null)
                    listener.after_load_dinosaur(false);
                System.out.println("");

            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token", user_token);
                return headers;
            }
        };

        queue.add(request);
    }

    private void requestOriginalDinosaur(){
        JsonObjectRequest request  = new JsonObjectRequest(
                Request.Method.GET,
                IpPc+(idDinoOriginal)+"/",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            dinosaur_information dinosaur = new dinosaur_information(response);
                            dinosaurs.add(dinosaur);
                            meterDinoEnArray(dinosaur);
                            requestDinosaur(dinosaur.getIdDino() + 1, new load_dinosaur_listener() {
                                @Override
                                public void after_load_dinosaur(boolean success) {
                                    after_original_dino_did_load = true;
                                    if (before_original_dino_did_load){
                                        if(idDinoOriginal != 1)
                                            viewPager.setCurrentItem(1);
                                        stop = true;

                                    }
                                }
                            });
                            requestDinosaur(dinosaur.getIdDino() - 1, new load_dinosaur_listener() {
                                @Override
                                public void after_load_dinosaur(boolean success) {
                                    before_original_dino_did_load = true;
                                    if (after_original_dino_did_load){
                                        if (idDinoOriginal != 1) {
                                            viewPager.setCurrentItem(1);
                                            stop = true;
                                        }

                                    }
                                }
                            });

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token", user_token);
                return headers;
            }
        };
        queue.add(request);
    }

    /**
     * Se asume que el dino original ya está metido
     * @param dino
     *
     */
    private void meterDinoEnArray(dinosaur_information dino){

            if (dino.getIdDino() == dinosaurs.get(dinosaurs.size() - 1).getIdDino()+1) {
                dinosaurs.add(dino);
            } else if (dino.getIdDino() == dinosaurs.get(0).getIdDino() - 1) {
                dinosaurs.add(0, dino);

            }
           pintarDinos();
        }


    private void pintarDinos(){
        //int actual = viewPager.getCurrentItem();
        // guarda la posicion actual en una variable
        adapter = new dinosaur_adapter_information(context, dinosaurs);
        viewPager.setAdapter(adapter);
        //viewPager.setCurrentItem(actual);
        //setCurrent en la posicion
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinosaur_information);
        ActionBar actionBar = getSupportActionBar();
        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

        // Recupera el token de sesión
        user_token = sharedPreferences.getString("token", null);

        // Verifica si el token de sesión está disponible
        if (user_token == null)
            Toast.makeText(context, "User Token is null", Toast.LENGTH_SHORT).show();

        /**
         * @param actionBar es para la flecha del retun**/
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        //esto por defecto
        idDinoOriginal = getIntent().getIntExtra("DinoId",3);
        queue= Volley.newRequestQueue(this);

        requestOriginalDinosaur();

        viewPager = findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Aquí puedes implementar lo que quieras que ocurra cuando la página se desplace.

            }   @Override
            public void onPageSelected(int position) {
                if (position == dinosaurs.size() - 1 && position != viewPager.getCurrentItem()) {
                    // Si se desplaza a la última página y no es la página actual
                    requestDinosaur(dinosaurs.get(position).getIdDino() + 1, new load_dinosaur_listener() {
                        @Override
                        public void after_load_dinosaur(boolean success) {
                            if (success) {
                                viewPager.setCurrentItem(position + 1);
                            }
                        }
                    });
                } else if (position == 0 && !stop) {
                    // Si se desplaza a la primera página y no se ha detenido el desplazamiento
                    requestDinosaur(dinosaurs.get(position).getIdDino() - 1, new load_dinosaur_listener() {
                        @Override
                        public void after_load_dinosaur(boolean success) {
                            if (success) {
                                viewPager.setCurrentItem(position - 1);
                            }
                        }
                    });
                }
            }



            @Override
            public void onPageScrollStateChanged(int state) {
                // Aquí puedes implementar lo que quieras que ocurra cuando cambie el estado de desplazamiento.

            }
        });


        
    }


    interface load_dinosaur_listener{

        void after_load_dinosaur(boolean success);


    }

}
