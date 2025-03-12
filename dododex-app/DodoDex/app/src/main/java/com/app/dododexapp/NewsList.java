package com.app.dododexapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsList {
    private List<News> news;

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public NewsList(List<News> news) {
        this.news = news;
    }

    public  NewsList(JSONArray array) {
        news = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonObject = array.getJSONObject(i);
                News aNews = new News(jsonObject);
                news.add(aNews);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

    }

}





