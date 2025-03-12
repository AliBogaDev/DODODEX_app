package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class sign_up extends AppCompatActivity {

    //Variable creation

    private final Context context = sign_up.this;
    public EditText email, name, password, rpassword;
    public String semail, sname, spassword, srpassword;
    ProgressBar progressBar;
    private boolean check_info = true;
    private RequestQueue queue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        // Show the button to go back
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        //Variable asignation
        queue = Volley.newRequestQueue(context);

        email = findViewById(R.id.sign_up_edittext_email);
        name = findViewById(R.id.sign_up_edittext_username);
        password = findViewById(R.id.sign_up_edittext_password);
        rpassword = findViewById(R.id.sign_up_edittext_rpassword);

        Button register = findViewById(R.id.sign_up_register_button);

        // When the user press the button, we send a post to register and sessions endpoints.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Set check_info to true before check it
                check_info = true;

                semail = email.getText().toString();
                sname = name.getText().toString();
                spassword = password.getText().toString();
                srpassword = rpassword.getText().toString();

                // In case of the function returns false we dont send the request
                check_info = check_inputs();

                if (check_info) {
                    send_post_register();
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    // Check the user input
    private boolean check_inputs(){

        if(sname.equals("")) {
            Toast.makeText(context, "Introduce un nombre de usuario", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (spassword.equals("") || srpassword.equals("")) {
            Toast.makeText(context, "Introduce todos los datos", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if (!spassword.equals(srpassword)) {

            password.setText("");
            rpassword.setText("");
            Toast.makeText(context, "Las contraseñas han de ser iguales", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;

    }

    private void send_post_register(){

        // Create a body to send on the request
        JSONObject body = new JSONObject();
        try {

            body.put("name", sname);
            body.put("email", semail);
            body.put("password", spassword);

        }
        catch (Exception e){throw new RuntimeException(e);}

        // Create the request
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/register/",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // If the response are 200 ok, we send the next request to log in on the app
                        send_log_request();

                    }

                },
                new Response.ErrorListener() {

                    // Otherwise, if the response are bad, we divide it in, null and bad request. If it's null, we throw a toast to indicate it, otherwise,
                    // we show the error that server throws.
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error.networkResponse == null) {
                            Toast.makeText(context, "No se pudo alcanzar al servidor", Toast.LENGTH_LONG).show();
                        } else {
                            try{

                            progressBar.setVisibility(View.INVISIBLE);
                            String data = new String (error.networkResponse.data, StandardCharsets.UTF_8);
                            JSONObject json_error_data = new JSONObject(data);

                            Toast.makeText(context, "Error: " + json_error_data.optString("error"), Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }

                    }
                }
        );

        this.queue.add(request);

    }

    // Function to send the log request
    private void send_log_request(){

        JSONObject body = new JSONObject();

        try {
            body.put("email", semail);
            body.put("password", spassword);

        } catch (Exception e){throw new RuntimeException(e);}

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/sessions/",
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressBar.setVisibility(View.INVISIBLE);
                        String receivedToken;
                        try {
                            receivedToken = response.getString("token");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // Obtén una referencia a SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);
                        // Crea un editor de SharedPreferences para realizar cambios
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        // Almacena el token de sesión
                        editor.putString("token", receivedToken);
                        // Guarda los cambios
                        editor.apply();
                        
                        Intent intent = new Intent(sign_up.this, MainDodoDex.class);
                        startActivity(intent);
                        finishAffinity();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.INVISIBLE);
                        if (error.networkResponse == null) {
                            Toast.makeText(context, "No se pudo alcanzar al servidor en la segunda peticion", Toast.LENGTH_LONG).show();
                        } else {
                            try{

                                String data = new String (error.networkResponse.data, StandardCharsets.UTF_8);
                                JSONObject json_error_data = new JSONObject(data);

                                Toast.makeText(context, "Error: " + json_error_data.optString("error"), Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                        }

                    }
                });

        this.queue.add(request);

    }
}