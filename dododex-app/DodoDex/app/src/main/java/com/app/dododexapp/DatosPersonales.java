package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.sql.DriverPropertyInfo;
import java.util.HashMap;
import java.util.Map;

public class DatosPersonales extends AppCompatActivity {
    public Intent main_activity;
    private static String user_token;

    private RequestQueue queue;
    private Context context = this;
    private EditText editTextNombre, editTextContraseña, editTextEmail, editTextTelefono;
    private TextView mostrarConstraseña;
    private boolean hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);
        this.queue = Volley.newRequestQueue(context);


        // Inicializar instancias de EditText
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        mostrarConstraseña = findViewById(R.id.mostrar);
        mostrarConstraseña.setOnClickListener(showListener);

        // Obtén una referencia a SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MiSharedPreferences", Context.MODE_PRIVATE);

        // Recupera el token de sesión
        user_token = sharedPreferences.getString("token", null);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        String url= "http://10.0.2.2:8000/user_data";

        // Hacer la solicitud GET para obtener los datos del usuario
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
        ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("user-token", user_token);
                return headers;
            }};

        this.queue.add(request);
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

        // Deshabilitar la edición de campos
        editTextNombre.setEnabled(false);
        editTextContraseña.setEnabled(false);
        editTextEmail.setEnabled(false);
        editTextTelefono.setEnabled(false);
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
}
