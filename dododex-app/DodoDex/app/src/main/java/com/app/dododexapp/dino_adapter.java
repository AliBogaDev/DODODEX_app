package com.app.dododexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class dino_adapter extends RecyclerView.Adapter<dino_viewholder>{

    private dino_list dino_list;
    private LayoutInflater inflater;
    private Context context;

    public dino_adapter(dino_list dinos, Context context) {
        this.dino_list = dinos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public dino_viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellView = inflater.inflate(R.layout.dinosaurios_view_holder, parent, false);
        return new dino_viewholder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull dino_viewholder holder, int position) {
        dino dino_for_this_cell = this.dino_list.get_dinos().get(position);
        holder.bind_dino(dino_for_this_cell);
    }

    @Override
    public int getItemCount() {
        return dino_list.get_dinos().size();
    }
}

