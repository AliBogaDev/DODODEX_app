package com.app.dododexapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DinoAdapter extends RecyclerView.Adapter<DinoViewHolder>{
    private List<DinoData> dinosaurList;
    private LayoutInflater inflater;
    private Context context;

    public DinoAdapter(List<DinoData> dinos, Context context) {
        this.dinosaurList = dinos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public DinoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cellView = inflater.inflate(R.layout.dinosaurios_view_holder, parent, false);
        return new DinoViewHolder(cellView);
    }

    @Override
    public void onBindViewHolder(@NonNull DinoViewHolder holder, int position) {
        DinoData dinoForThisCell = this.dinosaurList.get(position);
        holder.bindDino(dinoForThisCell);
    }

    @Override
    public int getItemCount() {
        return dinosaurList.size();
    }
}
