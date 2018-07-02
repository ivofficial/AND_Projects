package gwg.ivo.android.bakingapp;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import gwg.ivo.android.bakingapp.model.Recipe;
import gwg.ivo.android.bakingapp.network.JsonAsyncTask;
import gwg.ivo.android.bakingapp.ui.RecipeDetailActivity;
import gwg.ivo.android.bakingapp.utils.RecipeListAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String JSON_FILE_NAME = "baking.json";

    private static List<Recipe> mRecipeList;
    public static boolean isTablet;
    public static boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.layout_tablet_main_activity) != null) isTablet = true;
        else isTablet = false;
        if (findViewById(R.id.layout_landscape_main_activity) != null) isLandscape = true;
        else isLandscape = false;
        JsonAsyncTask task = new JsonAsyncTask(this, JSON_FILE_NAME);
        try {
            mRecipeList = (List<Recipe>)task.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        populateUI();
    }

    private void populateUI() {
        if (mRecipeList == null || mRecipeList.size()==0) {
            return;
        }
        GridView gridView = (GridView) findViewById(R.id.recipe_list_grid_view);
        RecipeListAdapter adapter = new RecipeListAdapter(this, mRecipeList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchRecipeDetail(position);
            }
        });
    }

    private void launchRecipeDetail(int position) {
        if (mRecipeList == null || mRecipeList.size()==0 || position>=mRecipeList.size()) {
            return;
        }

        Recipe recipe = mRecipeList.get(position);
        Parcel out = Parcel.obtain();
        recipe.writeToParcel(out, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    public static void setRecipeList(List<Recipe> recipes) {
        mRecipeList = recipes;
    }

    private int getCount() {
        if (mRecipeList != null ) {
            return mRecipeList.size();
        }
        return -1;
    }

    public static List<Recipe> getRecipeList() {
        return mRecipeList;
    }

}
