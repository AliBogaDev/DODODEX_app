package com.app.dododexapp;

import org.json.JSONException;
import org.json.JSONObject;

public class dino {

    private int id;
    private String name;
    private String description;
    private String image_URL;
    private String environment;
    private String epoch;
    private double height;
    private double weight;

    public dino(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.image_URL = json.getString("image");
        this.environment = json.getString("dinosaur_environment");
        this.epoch = json.getString("epoch");
        this.height = json.getDouble("height");
        this.weight = json.getDouble("weight");
    }

    public int get_id() { return id; }
    public String get_name() { return name; }
    public String get_description() { return description; }
    public String get_imageURL() { return image_URL; }
    public String get_environment() { return environment; }
    public String get_epoch() { return epoch; }
    public double get_height() { return height; }
    public double get_weight() { return weight; }

}
