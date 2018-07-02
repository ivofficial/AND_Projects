package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mMainName = "";
        List<String> mAlsoKnownAs = new ArrayList<>();
        String mPlaceOfOrigin = "";
        String mDescription = "";
        String mImage = "";
        List<String> mIngredients = new ArrayList<>();

        String fallbackString = "Data Not Available";

        Sandwich mSandwich = new Sandwich();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jo = jsonObject.getJSONObject("name");

            mMainName = jo.optString("mainName", fallbackString);

            JSONArray akaJsonArray = jo.getJSONArray("alsoKnownAs");
            if (akaJsonArray != null && akaJsonArray.length()>0) {
                for (int i = 0; i<akaJsonArray.length(); i++) {
                    mAlsoKnownAs.add(akaJsonArray.getString(i));
                }
            }

            mPlaceOfOrigin = jsonObject.getString("placeOfOrigin");
            mDescription = jsonObject.getString("description");
            mImage = jsonObject.getString("image");

            JSONArray ingredientsJsonArray = jsonObject.getJSONArray("ingredients");
            if (ingredientsJsonArray != null && ingredientsJsonArray.length()>0) {
                for (int j=0; j<ingredientsJsonArray.length(); j++) {
                    mIngredients.add(ingredientsJsonArray.getString(j));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(mMainName);
        sandwich.setAlsoKnownAs(mAlsoKnownAs);
        sandwich.setPlaceOfOrigin(mPlaceOfOrigin);
        sandwich.setDescription(mDescription);
        sandwich.setImage(mImage);
        sandwich.setIngredients(mIngredients);

        return sandwich;
    }
}
