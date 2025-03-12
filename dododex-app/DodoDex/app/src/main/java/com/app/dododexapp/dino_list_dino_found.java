package com.app.dododexapp;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dino_list_dino_found {

    private List<dinosaur> dinosaurs;

    public dino_list_dino_found(JSONArray array) {
        dinosaurs = new ArrayList<>();

        for(int i = 0; i < array.length(); i ++) {
            try{
                JSONObject json_element = array.getJSONObject(i);
                dinosaur a_dinosaur = new dinosaur(json_element);
                dinosaurs.add(a_dinosaur);
            }catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<dinosaur> get_dinos() { return dinosaurs; }
}
