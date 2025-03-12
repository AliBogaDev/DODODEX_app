package com.app.dododexapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.jar.JarException;

public class News {

    private  int newsId;
   // private  String urlImage; !! cuidado que luego se ha de cambiar

   private  int urlImage;
    private  String textNewsDescription;
    private  String textNewsUrl;


    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public int getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(int urlImage) {
        this.urlImage = urlImage;
    }

    public String getTextNewsDescription() {
        return textNewsDescription;
    }

    public void setTextNewsDescription(String textNewsDescription) {
        this.textNewsDescription = textNewsDescription;
    }

    public String getTextNewsUrl() {
        return textNewsUrl;
    }

    public void setTextNewsUrl(String textNewsUrl) {
        this.textNewsUrl = textNewsUrl;
    }

    public News(int newsId, int urlImage, String textNewsDescription, String textNewsUrl) {
        this.newsId = newsId;
        this.urlImage = urlImage;
        this.textNewsDescription = textNewsDescription;
        this.textNewsUrl = textNewsUrl;
    }

    public  News (JSONObject jsonObject) throws JSONException{
        this.newsId = jsonObject.getInt("newsId");
        this.urlImage = Integer.parseInt(jsonObject.getString("image"));
        this.textNewsDescription = jsonObject.getString("description");
        this.textNewsUrl =  jsonObject.getString("newsUrl");
    }


}
