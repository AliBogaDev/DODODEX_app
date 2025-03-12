package com.app.dododexapp;

import org.json.JSONException;
import org.json.JSONObject;

public class dinosaur {
    private int id;
    private String name;
    private String imageURL;

    public int get_id() {
        return id;
    }

    public String get_name() {
        return name;
    }

    public String get_imageURL() {
        return imageURL;
    }

    public dinosaur(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.imageURL = json.getString("image");
    }
}
