package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject root = new JSONObject(json);
        String placeOfOrigin = root.getString("placeOfOrigin");
        String description = root.getString("description");
        String image = root.getString("image");
        JSONObject nameObj= root.getJSONObject("name");
        String mainName = nameObj.getString("mainName");
       JSONArray alsoKnownAsArray = nameObj.getJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = jsonArrayToListConverter(alsoKnownAsArray);
        JSONArray ingredientsArray = root.getJSONArray("ingredients");
        List<String> ingredients = jsonArrayToListConverter(ingredientsArray);

        return new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
    }

    public static List<String> jsonArrayToListConverter(JSONArray jsonArray) throws JSONException {
        List<String> stringList = new ArrayList<>(jsonArray.length());
        for (int i =0;i<jsonArray.length();i++){
            stringList.add(jsonArray.getString(i));
        }
        return stringList;
    }
}
