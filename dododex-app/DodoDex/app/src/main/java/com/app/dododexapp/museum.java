package com.app.dododexapp;

import org.json.JSONException;
import org.json.JSONObject;

public class museum {

    private int id;
    private String name;
    private String description;
    private String image_URL;
    private String location_URL;
    private String price;

    public museum(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.image_URL = json.getString("image");
        this.location_URL = json.getString("location");
        this.price = json.getString("price");
    }

    public int get_id() { return id; }
    public String get_name() { return name; }
    public String get_description() { return description; }
    public String get_imageURL() { return image_URL; }
    public String get_locationURL() { return location_URL; }
    public String get_price() { return price; }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage_URL(String image_URL) {
        this.image_URL = image_URL;
    }

    public void setLocation_URL(String location_URL) {
        this.location_URL = location_URL;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
