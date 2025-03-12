package com.app.dododexapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dinasour_information_list {
    private List<dinosaur_information> dinosaurs;

    public List<dinosaur_information> getDinosaurs() {
        return dinosaurs;
    }

    public void setDinosaurs(List<dinosaur_information> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }

    public dinasour_information_list(List<dinosaur_information> dinosaurs) {
        this.dinosaurs = dinosaurs;
    }

    public dinasour_information_list(JSONArray jsonArrayDino){
        dinosaurs =  new ArrayList<>();
        for  (int i=0;i<jsonArrayDino.length();i++){
            try {
                JSONObject jsonObject = jsonArrayDino.getJSONObject(i);
                dinosaur_information arrayDinosaur = new dinosaur_information(jsonObject);
                dinosaurs.add(arrayDinosaur);
            }catch (JSONException e){
                throw  new RuntimeException(e);
            }
        }
    }

}
