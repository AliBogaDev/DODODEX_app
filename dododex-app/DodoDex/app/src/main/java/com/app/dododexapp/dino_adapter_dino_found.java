package com.app.dododexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class dino_adapter_dino_found extends RecyclerView.Adapter<dino_view_holder_dino_found> {

    //private dino_list dino_list;
    private LayoutInflater inflater;
    private Context context;
    private List<dinosaur> dinosaur_filtered_list;
    //public void dino_adapter(List<dino> dino_filtered_list) { this.dino_filtered_list = dino_filtered_list; }

    public dino_adapter_dino_found(dino_list_dino_found dinos, Context context) {
         this.dinosaur_filtered_list = dinos.get_dinos();
         this.inflater = LayoutInflater.from(context);
         this.context = context;
         //this.dino_filtered_list = dino_filtered_list;

    }

    public void set_filtered_list(List<dinosaur> filtered_list) {
        this.dinosaur_filtered_list = filtered_list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public dino_view_holder_dino_found onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cell_view = inflater.inflate(R.layout.recycler_cell, parent, false);
        return new dino_view_holder_dino_found(cell_view);

    }

    @Override
    public void onBindViewHolder(@NonNull dino_view_holder_dino_found holder, int position) {
        dinosaur dinosaur_for_this_cell = this.dinosaur_filtered_list.get(position);
        holder.bind_dino(dinosaur_for_this_cell);
    }

    @Override
    public int getItemCount() { return dinosaur_filtered_list.size(); }

}
