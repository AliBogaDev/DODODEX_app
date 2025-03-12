package com.app.dododexapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;


public class Login extends AppCompatActivity {
    private Button regButton;
    private Button loginButton;
    private EditText editTextCorreoElectronico;
    private EditText editTextPassword;
    private RequestQueue queueForRequests;
    private Context context = this;
    private RequestQueue queue;
    private ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queueForRequests = Volley.newRequestQueue(this);
        regButton = findViewById(R.id.crear_cuenta_button_link);
        loginButton = findViewById(R.id.login_cuenta_button);
        editTextCorreoElectronico = findViewById(R.id.edit_text_correo_electronico);
        editTextPassword = findViewById(R.id.edit_text_password);
        progress_bar = findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.INVISIBLE);

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, sign_up.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress_bar.setVisibility(View.VISIBLE);
                sendLoginRequest();

            }
        });
        queue = Volley.newRequestQueue(this);
    }

    private void sendLoginRequest() {



        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("email", editTextCorreoElectronico.getText().toString());
            requestBody.put("password", editTextPassword.getText().toString());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "http://10.0.2.2:8000/sessions/",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String receivedToken;
                        progress_bar.setVisibility(View.INVISIBLE);
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

                        Intent intent = new Intent(Login.this, MainDodoDex.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress_bar.setVisibility(View.INVISIBLE);
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            Toast.makeText(Login.this, "Código de respuesta: " + statusCode, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Login.this, "Error de conexión", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        queueForRequests.add(request);
    }
}