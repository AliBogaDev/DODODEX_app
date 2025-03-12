package com.app.dododexapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class dino_view_holder_dino_found extends RecyclerView.ViewHolder {

    private TextView dino_name;
    private ImageView dino_image;
    private dinosaur dinosaur;

    public dino_view_holder_dino_found(@NonNull View itemView) {

        super(itemView);
        dino_name = itemView.findViewById(R.id.dino_name);
        dino_image = itemView.findViewById(R.id.dino_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, dino_found_activity.class);
                context.startActivity(intent);
            }
        });
    }

    public void bind_dino(dinosaur dinosaur) {
        this.dino_name.setText(dinosaur.get_name());
        Glide.with(itemView).load(dinosaur.get_imageURL()).into(dino_image);
        this.dinosaur = dinosaur;
    }
}

