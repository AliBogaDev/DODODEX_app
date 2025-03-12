package com.app.dododexapp;

import org.json.JSONException;
import org.json.JSONObject;

public class DinoData {
    private int id;
    private String name;
    private String imageURL;
    private String dinosaur_enviroment;


    public DinoData(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("name");
        this.imageURL = json.getString("image");
        this.dinosaur_enviroment = json.getString("dinosaur_environment");
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public String getImageUrl() { return imageURL; }
    public String getDinosaur_enviroment() {return dinosaur_enviroment;}
}
