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

public class NewsViewHolder extends RecyclerView.ViewHolder {

    private  News news;
    private ImageView newsPhoto;
    private TextView newsDescription, NewsUrl ;
    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);
        newsPhoto = (ImageView) itemView.findViewById(R.id.my_image);
        newsDescription =(TextView) itemView.findViewById(R.id.my_textDescription);
        NewsUrl = (TextView) itemView.findViewById(R.id.my_textUrl);


    }
    public  void showNews (News news, Activity activity){
        this.newsDescription.setText(news.getTextNewsDescription());
        this.NewsUrl.setText(news.getTextNewsUrl());
        Glide.with(itemView.getContext())
                .load(news.getUrlImage())
                .into(newsPhoto);
        this.news = news;
    }

}
