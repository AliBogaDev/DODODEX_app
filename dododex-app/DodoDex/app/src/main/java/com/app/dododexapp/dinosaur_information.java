package com.app.dododexapp;

import org.json.JSONException;
import org.json.JSONObject;

public class dinosaur_information {
 /**Atributos**/
    private  int  idDino;

    private  String image;
    private  String name;
    private  double  height;
    private  String feeding;
    private double weight;
    private String dinosaur_enviroment;
    private  String description;
    private  String epoch;


    public int getIdDino() {
        return idDino;
    }

    public void setIdDino(int idDino) {
        this.idDino = idDino;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getFeeding() {
        return feeding;
    }

    public void setFeeding(String feeding) {
        this.feeding = feeding;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDinosaur_enviroment() {
        return dinosaur_enviroment;
    }

    public void setDinosaur_enviroment(String dinosaur_enviroment) {
        this.dinosaur_enviroment = dinosaur_enviroment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEpoch() {
        return epoch;
    }

    public void setEpoch(String epoch) {
        this.epoch = epoch;
    }

    public dinosaur_information(int idDino, String image, String name, double height, String feeding, double weight, String dinosaur_environment, String description, String epoch) {
        this.idDino = idDino;
        this.image = image;
        this.name = name;
        this.height = height;
        this.feeding = feeding;
        this.weight = weight;
        this.dinosaur_enviroment = dinosaur_environment;
        this.description = description;
        this.epoch = epoch;
    }

    public dinosaur_information(JSONObject jsonObject) throws JSONException{

        this.idDino=jsonObject.getInt("id");
        this.name= jsonObject.getString("name");
        this.description= jsonObject.getString("description");
        this.image= jsonObject.getString("image");
        this.dinosaur_enviroment= jsonObject.getString("dinosaur_environment");
        this.epoch= jsonObject.getString("epoch");
        this.height= jsonObject.getDouble("height");
        this.weight= jsonObject.getDouble("weight");
        this.feeding = jsonObject.getString("feeding");

    }
}
