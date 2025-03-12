package com.app.dododexapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder>  {

 private List<News> allNews;
 private Activity activity;

    public NewsAdapter(List<News> allNews, Activity activity) {
        this.allNews = allNews;
        this.activity=activity;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      LayoutInflater   inflater = LayoutInflater.from(parent.getContext());
        View newsCell = inflater.inflate(R.layout.recycler_view_new_cell,parent,false);
        NewsViewHolder cellViewHolder = new NewsViewHolder(newsCell);
        return cellViewHolder ;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
    News dataForThisCell = allNews.get(position);
    holder.showNews(dataForThisCell, activity);


    }

    @Override
    public int getItemCount() {
        return allNews.size();
    }


}
