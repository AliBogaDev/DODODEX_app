package com.app.dododexapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FavouritesAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {
    private DinosaurList FavouriteDinosaurList;
    public FavouritesAdapter(DinosaurList dinosaurs) {
        this.FavouriteDinosaurList = dinosaurs;
    }

    @Override
    public int getItemCount() {
        return this.FavouriteDinosaurList.getFavouriteList().size();
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cellView = inflater.inflate(R.layout.dinosaurios_view_holder, parent, false);
        FavouriteViewHolder cellViewHolder = new FavouriteViewHolder(cellView);
        return cellViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        dinosaur dataForThisCell = this.FavouriteDinosaurList.getFavouriteList().get(position);
        holder.showData(dataForThisCell);
    }
}
