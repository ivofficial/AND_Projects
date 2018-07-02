package gwg.ivo.android.bakingapp.network;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import gwg.ivo.android.bakingapp.model.Ingredient;
import gwg.ivo.android.bakingapp.model.Recipe;
import gwg.ivo.android.bakingapp.model.Step;

public class ParseRecipeJson {

    private static final String TAG = "ParseRecipeJson";

    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_INGREDIENTS_LIST = "ingredients";
    private static final String INGREDIENT_NAME = "ingredient";
    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_MEASURE = "measure";
    private static final String RECIPE_STEPS_LIST = "steps";
    private static final String STEP_ID = "id";
    private static final String STEP_SHORT_DESCRIPTION = "shortDescription";
    private static final String STEP_DESCRIPTION = "description";
    private static final String STEP_VIDEO_URL = "videoURL";
    private static final String STEP_THUMBNAIL_URL = "thumbnailURL";
    private static final String RECIPE_SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";

    public static List<Recipe> parseRecipeListFromJson(String json) {
        List<Recipe> mRecipeList = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(json);
            mRecipeList = getRecipeListFromJson(jsonArray);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return mRecipeList;
    }

    private static List<Recipe> getRecipeListFromJson(JSONArray jsonArray) throws JSONException {
        List<Recipe> recipes = new ArrayList<>();
        if (jsonArray != null && jsonArray.length()>0){
            Recipe recipe;
            Parcel in = Parcel.obtain();
            JSONObject recipeJson;
            JSONArray ingredientsJsonArray;
            JSONArray stepsJsonArray;
            for (int i=0; i < jsonArray.length(); i++) {
                recipe = new Recipe(in);
                recipeJson = new JSONObject(jsonArray.get(i).toString());
                recipe.setId(recipeJson.getString(RECIPE_ID));
                recipe.setName(recipeJson.getString(RECIPE_NAME));
                ingredientsJsonArray = recipeJson.getJSONArray(RECIPE_INGREDIENTS_LIST);
                recipe.setIngredientsList(getIngredientsListFromJsonArray(ingredientsJsonArray));
                stepsJsonArray = recipeJson.getJSONArray(RECIPE_STEPS_LIST);
                recipe.setStepsList(getStepsListFromJsonArray(stepsJsonArray));
                recipe.setServings(recipeJson.getString(RECIPE_SERVINGS));
                recipe.setImage(recipeJson.getString(RECIPE_IMAGE));
                recipes.add(recipe);
            }
            in.recycle();
        }

        return recipes;
    }

    private static List<Ingredient> getIngredientsListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Ingredient> ingredients = new ArrayList<>();
        if( jsonArray != null && jsonArray.length()>0) {
            Ingredient ingredient;
            Parcel in = Parcel.obtain();
            JSONObject ingredientJsonObject;
            for (int i=0; i<jsonArray.length(); i++) {
                ingredientJsonObject = jsonArray.getJSONObject(i);
                if (ingredientJsonObject != null) {
                    ingredient = new Ingredient(in);
                    ingredient.setIngredient(ingredientJsonObject.getString(INGREDIENT_NAME));
                    ingredient.setMeasure(ingredientJsonObject.getString(INGREDIENT_MEASURE));
                    ingredient.setQuantity(ingredientJsonObject.getString(INGREDIENT_QUANTITY));
                    ingredients.add(ingredient);
                }
            }
            in.recycle();
        }

        return ingredients;
    }

    private static List<Step> getStepsListFromJsonArray (JSONArray jsonArray) throws JSONException {
        List<Step> steps = new ArrayList<>();
        if (jsonArray != null && jsonArray.length() > 0) {
            Step step;
            Parcel in = Parcel.obtain();
            JSONObject stepJsonObject;
            for( int i=0; i<jsonArray.length(); i++) {
                stepJsonObject = jsonArray.getJSONObject(i);
                if (stepJsonObject != null) {
                    step = new Step(in);
                    step.setId(stepJsonObject.getString(STEP_ID));
                    step.setShortDescription(stepJsonObject.getString(STEP_SHORT_DESCRIPTION));
                    step.setDescription(stepJsonObject.getString(STEP_DESCRIPTION));
                    step.setVideoUrl(stepJsonObject.getString(STEP_VIDEO_URL));
                    step.setThumbnailUrl(stepJsonObject.getString(STEP_THUMBNAIL_URL));
                    steps.add(step);
                }
            }
            in.recycle();
        }

        return steps;
    }

}
