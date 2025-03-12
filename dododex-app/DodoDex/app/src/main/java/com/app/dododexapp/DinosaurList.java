package com.app.dododexapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DinosaurList {
    private List<dinosaur> FavouriteList;


    public DinosaurList(JSONArray array){
        FavouriteList = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject jsonElement = array.getJSONObject(i);
                dinosaur aDinosaur = new dinosaur(jsonElement);
                FavouriteList.add(aDinosaur);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public List<dinosaur> getFavouriteList() { return FavouriteList; }
}
