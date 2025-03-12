package com.app.dododexapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class museum_list {

    private List<museum> museums;

    public museum_list(JSONArray array) {
        museums = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try{
                JSONObject json_element = array.getJSONObject(i);
                museum a_museum = new museum(json_element);
                museums.add(a_museum);
            }catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<museum> getMuseums() { return museums; }

}
