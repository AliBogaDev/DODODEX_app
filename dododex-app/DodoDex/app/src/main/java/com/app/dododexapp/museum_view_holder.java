package com.app.dododexapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class museum_view_holder extends RecyclerView.ViewHolder {

    private TextView museum_name;
    private ImageView museum_image;
    private museum museum;

    public museum_view_holder(@NonNull View itemView) {

        super(itemView);
        museum_name = itemView.findViewById(R.id.museum_text_recycler_view);
        museum_image = itemView.findViewById(R.id.museum_img);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, museum_info_activity.class);
                intent.putExtra("id", museum.get_id());
                context.startActivity(intent);
            }
        });
    }

    public void bindMuseum(museum museum) {
        this.museum_name.setText(museum.get_name());
        Glide.with(itemView).load(museum.get_imageURL()).into(museum_image);
        this.museum = museum;
    }
}
