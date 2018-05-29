package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String NAME = "name";
    public static final String MAIN_NAME = "mainName";
    public static final String ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_URL = "image";
    public static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        //this parsing works on the assumption that all the data should be present in JSON to create the object
        //if some param were not there, the instance would not be created
        try {
            JSONObject sandwichDetails = new JSONObject(json);
            JSONObject nameDetails = sandwichDetails.optJSONObject(NAME);

            String sandwichName = nameDetails.optString(MAIN_NAME,null);
            JSONArray sandwichAlsoKnownAs = nameDetails.optJSONArray(ALSO_KNOWN_AS);
            String placeOfOrigin = sandwichDetails.optString(PLACE_OF_ORIGIN,null);
            String description = sandwichDetails.optString(DESCRIPTION,null);
            String imageUrl = sandwichDetails.optString(IMAGE_URL,null);
            JSONArray ingredientsArray = sandwichDetails.optJSONArray(INGREDIENTS);

            List<String> alsoKnownAsList = getJsonArrayAsList(sandwichAlsoKnownAs);
            List<String> ingredientsList = getJsonArrayAsList(ingredientsArray);

            return new Sandwich(sandwichName, alsoKnownAsList, placeOfOrigin, description, imageUrl, ingredientsList);
        } catch (JSONException e) {
            e.printStackTrace();
            //some data were not available. So we will return null
            return null;
        }
    }

    private static List<String> getJsonArrayAsList(JSONArray sandwichAlsoKnownAs) throws JSONException {
        ArrayList<String> stringList = new ArrayList<>();
        for (int i = 0; i < sandwichAlsoKnownAs.length(); i++) {
            stringList.add(sandwichAlsoKnownAs.getString(i));
        }
        return stringList;
    }
}
