package com.app.dododexapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;

public class DinoViewHolder extends RecyclerView.ViewHolder{

    private TextView dinoName;
    private ImageView dinoImage;
    private DinoData dino;

    public DinoViewHolder(@NonNull View itemView) {
        super(itemView);
        dinoName = itemView.findViewById(R.id.textRecyclerView);
        dinoImage = itemView.findViewById(R.id.imgDinosaur);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                // Aquí puedes agregar lógica para manejar el clic en el elemento del RecyclerView

                Intent intent = new Intent(context, dinasour_information_main.class);
                int id = dino.getId();
                intent.putExtra("DinoId", id);
                context.startActivity(intent);
            }
        });
    }

    public void bindDino(DinoData dino) {
        this.dinoName.setText(dino.getName());

        // Cargar la imagen con Glide
        Glide.with(itemView)
                .load(dino.getImageUrl())
                .into(dinoImage);

        this.dino = dino;
    }
}
