package com.app.dododexapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class dino_list {

    private List<dino> dinos;

    public dino_list(JSONArray array) {
        dinos = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try{
                JSONObject json_element = array.getJSONObject(i);
                dino a_dino = new dino(json_element);
                dinos.add(a_dino);
            }catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<dino> get_dinos() { return dinos; }

}
