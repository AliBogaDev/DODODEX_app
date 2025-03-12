package com.app.dododexapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DinoList {
    private List<DinoData> dinos;

    public DinoList(JSONArray array){
        dinos = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            try{
                JSONObject jsonElement = array.getJSONObject(i);
                DinoData aDino = new DinoData(jsonElement);
                dinos.add(aDino);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<DinoData> getDinosaurList() {
        return dinos;
    }
}
