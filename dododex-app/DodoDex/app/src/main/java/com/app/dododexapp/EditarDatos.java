package com.app.dododexapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditarDatos extends AppCompatActivity {
    private Context context = this;
    private boolean hide;
    public Intent main_activity;
    private RequestQueue queue;
    private EditText editTextNombre;
    private EditText editTextContraseña;
    private EditText editTextEmail;
    private EditText editTextTelefono;
    private TextView mostrarContraseña;
    private Button btnGuardarDatos;
    private static String user_token;
    private SharedPreferences sharedPreferences;
    private ProgressBar progress_bar;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios_datos);


        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);
        user_token = sharedPreferences.getString("token", null);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        btnGuardarDatos = findViewById(R.id.btnGuardarDatos);
        progress_bar = findViewById(R.id.progress_bar);

        //Para mostrar la contraseña
        mostrarContraseña = findViewById(R.id.mostrarContraseña);
        mostrarContraseña.setOnClickListener(showListener);

        obtener_datos();


        btnGuardarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editar_datos();
            }
        });

    }

    private void obtener_datos() {
        // Mostrar la ProgressBar
        progress_bar.setVisibility(View.VISIBLE);

        // Crear un temporizador (Handler) para ocultar la ProgressBar después de 2 segundos (2000 milisegundos)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Ocultar la ProgressBar después de 2 segundos
                progress_bar.setVisibility(View.INVISIBLE);

                // Resto del código para realizar la solicitud GET
                queue = Volley.newRequestQueue(context);
                String url = "http://10.0.2.2:8000/user_data";

                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Procesar la respuesta JSON y actualizar la interfaz de usuario
                                try {
                                    Usuario usuario = parsearRespuesta(response);
                                    mostrarDatosUsuario(usuario);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar errores de la solicitud
                        if (error.networkResponse == null) {
                            Toast.makeText(context, "No se ha establecido la conexión", Toast.LENGTH_SHORT).show();
                        } else {
                            int serverCode = error.networkResponse.statusCode;
                            Toast.makeText(context, "Estado del servidor: " + serverCode, Toast.LENGTH_SHORT).show();
                        }
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
        }, 2000); // 2000 milisegundos = 2 segundos de retraso antes de ocultar la ProgressBar
    }

    private void editar_datos() {

        queue = Volley.newRequestQueue(context);
        String url = "http://10.0.2.2:8000/user_data/";

        JSONArray requestBody = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", editTextNombre.getText().toString());
            jsonObject.put("email", editTextEmail.getText().toString());
            jsonObject.put("password", editTextContraseña.getText().toString());
            jsonObject.put("telephone", editTextTelefono.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestBody.put(jsonObject);
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.PUT,
                url,
                requestBody,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Se obtienen los datos del usuario registrado
                        obtener_datos();
                        Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();

                        // Datos actualizados correctamente, volvemos a la pantalla anterior después de un retraso
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Crear un Intent para volver a la pantalla anterior

                                //Intent intent = new Intent(context, Config.class);
                                //startActivity(intent);

                                // Finalizar esta actividad para que no se pueda volver atrás con el botón "Atrás"
                                finish();
                            }
                        }, 2000); // 2000 milisegundos = 2 segundos de retraso


                        //Deshabilitada la edición hasta que no se salga de la pantalla
                        deshabilitar_edicion();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
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

            Volley.newRequestQueue(context).add(request);

        }

    // Método para convertir la respuesta JSON en un objeto Usuario
    private Usuario parsearRespuesta(JSONObject response) throws Exception {
        String nombreUsuario = response.getString("name");
        String contraseña = response.getString("password");
        String email = response.getString("email");
        String telefono = response.optString("telephone", null);

        return new Usuario(nombreUsuario, contraseña, email, telefono);
    }

    // Método para mostrar los datos del usuario en la interfaz de usuario
    private void mostrarDatosUsuario(Usuario usuario) {
        editTextNombre.setText(usuario.getNombreUsuario());
        editTextContraseña.setText(usuario.getContraseña());
        editTextEmail.setText(usuario.getEmail());
        editTextTelefono.setText(usuario.getTelefono());
    }

    private void deshabilitar_edicion(){
        editTextNombre.setEnabled(false);
        editTextContraseña.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextTelefono.setEnabled(false);
        btnGuardarDatos.setEnabled(false);
    }


    //Método para mostrar la contraseña
    private View.OnClickListener showListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (hide) {
                hide = false;
                editTextContraseña.setTransformationMethod(null);
            } else {
                hide = true;
                editTextContraseña.setTransformationMethod(new PasswordTransformationMethod());
            }
        }
    };

    //Método
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        setContentView(R.layout.activity_cambio_datos_horizontal);
    }

}
