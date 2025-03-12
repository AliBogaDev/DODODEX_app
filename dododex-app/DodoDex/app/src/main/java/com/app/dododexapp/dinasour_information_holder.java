package com.app.dododexapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class dinasour_information_holder extends RecyclerView.ViewHolder {
    private ImageButton buttonRetur;
    private ImageView dinosaurImage;

    private TextView dinosaurName;
    private  TextView dinosarHeigth;
    private  TextView dinosaurFeeding;
    private  TextView dinosaurWeight;
    private  TextView dinosaurHabitat;
    private  TextView dinosaurDescriptionLetter;
    private  TextView dinosaurTextDescription;
    private  ImageButton buttonLike;


    public dinasour_information_holder(@NonNull View itemView) {
        super(itemView); //lo inicializo sobre la vista
        dinosaurImage = itemView.findViewById(R.id.image_Dinosaur);
        dinosaurName =  itemView.findViewById(R.id.name_Dinosaur);
        dinosarHeigth = itemView.findViewById(R.id.heigth_Dinosaur);
        dinosaurFeeding =  itemView.findViewById(R.id.feeding_Dinosaur);
        dinosaurWeight = itemView.findViewById(R.id.weight_Dinosaur);
        dinosaurHabitat = itemView.findViewById(R.id.habitad_Dinosaur);
        dinosaurDescriptionLetter = itemView.findViewById(R.id.descriptionText_Dinosaur);
        dinosaurTextDescription = itemView.findViewById(R.id.information_text);
        buttonLike = itemView.findViewById(R.id.botton_like_recicler);

    }

}
