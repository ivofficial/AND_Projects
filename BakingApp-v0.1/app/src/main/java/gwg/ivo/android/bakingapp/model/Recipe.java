package gwg.ivo.android.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {

    private String mId;
    private String mName;
    private List<Ingredient> mIngredientsList;
    private List<Step> mStepsList;
    private String mServings;
    private String mImage;

    public Recipe(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mIngredientsList = in.createTypedArrayList(Ingredient.CREATOR);
        mStepsList = in.createTypedArrayList(Step.CREATOR);
        mServings = in.readString();
        mImage = in.readString();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public List<Ingredient> getIngredientsList() {
        return mIngredientsList;
    }

    public void setIngredientsList(List<Ingredient> ingredientsList) {
        this.mIngredientsList = new ArrayList<>();
        this.mIngredientsList = ingredientsList;
    }

    public List<Step> getStepsList() {
        return mStepsList;
    }

    public void setStepsList(List<Step> stepsList) {
        this.mStepsList = new ArrayList<>();
        this.mStepsList = stepsList;
    }

    public String getServings() {
        return mServings;
    }

    public void setServings(String servings) {
        this.mServings = servings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        this.mImage = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mName);
        dest.writeTypedList(mIngredientsList);
        dest.writeTypedList(mStepsList);
        dest.writeString(mServings);
        dest.writeString(mImage);
    }
}
