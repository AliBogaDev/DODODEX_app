package com.app.dododexapp;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;


public class FavouriteViewHolder extends RecyclerView.ViewHolder {
    private dinosaur dinosaur;
    private TextView textView;
    private ImageView Image;
    public FavouriteViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.textRecyclerView);
        Image = itemView.findViewById(R.id.imgDinosaur);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dinosaurId = dinosaur.get_id();
                Context context = view.getContext();
                // Send the id and changes to the inforamtion of the dinosaur screen
                //Intent intent = new Intent(context, .class);
                //intent.putExtra("id", DinosaurId);
                //context.startActivity(intent);
            }
        });
    }
    public void showData(dinosaur dinosaur) {
        this.dinosaur = dinosaur;
        if (dinosaur != null) {
            this.textView.setText(dinosaur.get_name());
            if (dinosaur.get_imageURL() != null && !dinosaur.get_imageURL().isEmpty()) {
                Glide.with(itemView)
                        .load(dinosaur.get_imageURL())
                        .into(Image);
            }
        } else {
            // Handle the case where Dinosaur object is null
            // For example, you can set default text or image
            this.textView.setText("Unknown");
        }
    }
}
