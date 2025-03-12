package com.app.dododexapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class dino_viewholder extends RecyclerView.ViewHolder{

    private TextView dino_name;
    private ImageView dino_image;
    private dino dino;

    public dino_viewholder(@NonNull View itemView) {

        super(itemView);
        dino_name = itemView.findViewById(R.id.textRecyclerView);
        dino_image = itemView.findViewById(R.id.imgDinosaur);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, era_options_activity.class);
                context.startActivity(intent);
            }
        });
    }

    public void bind_dino(com.app.dododexapp.dino dino) {
        this.dino_name.setText(dino.get_name());
        Glide.with(dino_image).load(dino.get_imageURL()).into(dino_image);
        this.dino = dino;
    }
}
