package com.app.dododexapp;


import static com.app.dododexapp.R.id.*;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

public class dinosaur_adapter_information extends PagerAdapter {
    private Context context ;
    LayoutInflater inflater;
    private List<dinosaur_information> dinosaurList;
    private dinosaur_information dinosaur;

    public dinosaur_adapter_information(Context context, List<dinosaur_information> dinosaurList) {
        this.context = context;
        this.dinosaurList = dinosaurList;
    }

    @Override
    public int getCount() {
        return dinosaurList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_pager_cell,container, false);
//aqui pinto lo que yo quiero que salga en la pantalla
        dinosaur_information dinosaur = dinosaurList.get(position);

        ImageView image_Dinosaur = view.findViewById(R.id.image_Dinosaur);
        Glide.with(context) // O usa 'this' si estás en una actividad
                .load(dinosaur.getImage()) // Cambia esto por la referencia correcta a tu imagen
                .into(image_Dinosaur);

        TextView nameDinosaur=  view.findViewById(R.id.name_Dinosaur);
        nameDinosaur.setText(dinosaur.getName());

        TextView heightDinosaur = view.findViewById(R.id.altura);
        heightDinosaur.setText(dinosaur.getHeight()+"");

        TextView habitatDinosaur= view.findViewById(R.id.habitad);
        habitatDinosaur.setText(dinosaur.getDinosaur_enviroment());

        TextView weightDinosaur=  view.findViewById(R.id.peso);
        weightDinosaur.setText(dinosaur.getWeight()+"");

        TextView feedingDinosaur=  view.findViewById(R.id.alimentacion);
        feedingDinosaur.setText(dinosaur.getFeeding());


        TextView informationText=  view.findViewById(R.id.information_text);
        informationText.setText(dinosaur.getDescription());

        //esto boton esta bien aqui, defido de esta nmanera
        ImageButton likeButton = view.findViewById(botton_like_recicler);
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Te gusta!", Toast.LENGTH_SHORT).show();
              //  Intent intent = new Intent(context, dinasour_information_main.class );
              //  context.startActivity(intent);

                Intent intent = new Intent(context, Favourites.class);

                // Agregar la información del dinosaurio como extras en el Intent
                intent.putExtra("name", dinosaur.getName());
                intent.putExtra("height", dinosaur.getHeight());
                intent.putExtra("habitat", dinosaur.getDinosaur_enviroment());
                intent.putExtra("weight", dinosaur.getWeight());
                intent.putExtra("feeding", dinosaur.getFeeding());
                intent.putExtra("description", dinosaur.getDescription());
                intent.putExtra("image", dinosaur.getImage());

                // Iniciar la actividad Favorites
                context.startActivity(intent);
            }
        });
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
