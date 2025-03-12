package com.app.dododexapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InformationMain extends AppCompatActivity {
private static  String user_token;


    private Button  museum,map, favorites, news;
    TextView information;
    private Context context=this;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_main);

        museum = findViewById(R.id.bottom_museo);
        map= findViewById(R.id.button_mapa);
        favorites = findViewById(R.id.button_favoritos);
        news = findViewById(R.id.button_noticias);




        ActionBar actionBar = getSupportActionBar();
        /**
         * @param actionBar es para la flecha del retun**/
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);


        /**@map dirige al mapa de dinosaurios**/
        map.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               Intent myIntent = new Intent(InformationMain.this, Maps.class);
               context.startActivity(myIntent);

            }

        });
        /**@museum  dirige al museo de dinosaurios**/
        museum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent myIntent = new Intent(InformationMain.this, museum_view_activity.class);
            context.startActivity(myIntent);

                
            }

        });
        /**@favorites dirige a los dinosaurios favoritos**/
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent( InformationMain.this ,Favourites.class);
                context.startActivity(myIntent);

            }

        });
        /**@news dirige a las noticias de  dinosaurios**/
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * @param MewsMain  es la clase que da las noticias**/

                Intent myIntent = new Intent(InformationMain.this, NewsMain.class);
                context.startActivity(myIntent);

            }

        });


    }
}