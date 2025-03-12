package com.app.dododexapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class museum_adapter extends RecyclerView.Adapter<museum_view_holder> {

    private museum_list museums_to_show;
    private LayoutInflater inflater;
    private Context context;

    public museum_adapter(museum_list museums, Context context) {
        this.museums_to_show = museums;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public museum_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cell_view = inflater.inflate(R.layout.recycler_view_cell, parent, false);
        return new museum_view_holder(cell_view);
    }

    @Override
    public void onBindViewHolder(@NonNull museum_view_holder holder, int position) {
        museum museum_for_this_cell = this.museums_to_show.getMuseums().get(position);
        holder.bindMuseum(museum_for_this_cell);
    }

    @Override
    public int getItemCount() {
        return museums_to_show.getMuseums().size();
    }
}
