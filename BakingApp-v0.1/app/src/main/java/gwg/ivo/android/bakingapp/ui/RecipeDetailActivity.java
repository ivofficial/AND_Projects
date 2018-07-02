package gwg.ivo.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gwg.ivo.android.bakingapp.MainActivity;
import gwg.ivo.android.bakingapp.R;
import gwg.ivo.android.bakingapp.model.Ingredient;
import gwg.ivo.android.bakingapp.model.Recipe;
import gwg.ivo.android.bakingapp.model.Step;

public class RecipeDetailActivity extends AppCompatActivity implements StepsListFragment.OnFragmentInteractionListener{

    public static final String EXTRA_RECIPE = "extra_recipe";
    private static final String EXTRA_POSITION = "extra_position";
    private static final String INGREDIENTS_STEP = "INGREDIENTS";

    private static Recipe mRecipe;
    private static StepsListFragment mStepsListFragment;
    private static IngredientsDetailFragment mIngredientsDetailFragment;
    private static StepDetailsFragment mStepDetailsFragment;
    private static FragmentManager mFragmentManager;
    private static Context mContext;
    private static int mPosition;
    private static Window mWindow;
    private static ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        mWindow = getWindow();
        mActionBar = getSupportActionBar();
        mPosition = -1;
        mContext = this;
        mIngredientsDetailFragment = null;
        mStepDetailsFragment = null;
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(EXTRA_RECIPE);
            mPosition = savedInstanceState.getInt(EXTRA_POSITION);
            mStepsListFragment = getStepsListFragment(mRecipe);
            if (MainActivity.isTablet) {
                mFragmentManager.beginTransaction()
                        .add(R.id.steps_list_fragment, mStepsListFragment)
                        .commit();
            } else {
                mFragmentManager.beginTransaction()
                        .add(R.id.fragment_placeholder, mStepsListFragment)
                        .commit();
            }
            updateUI(mRecipe, mFragmentManager);
        } else {
            mRecipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
            mStepsListFragment = getStepsListFragment(mRecipe);
            if (mRecipe == null) {
                return;
            }
            if (MainActivity.isTablet) {
                mFragmentManager.beginTransaction()
                        .add(R.id.steps_list_fragment, mStepsListFragment)
                        .commit();
            } else {
                mFragmentManager.beginTransaction()
                        .add(R.id.fragment_placeholder, mStepsListFragment)
                        .commit();
            }
        }
        TextView testTV = (TextView) findViewById(R.id.recipe_detail_test_tv);
        testTV.setText(mRecipe.getName());
    }

    private static void updateUI(Recipe recipe, FragmentManager fragmentManager) {
        if (recipe == null || fragmentManager == null) return;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (MainActivity.isTablet) {
            if (fragmentManager.getFragments().size() > 1) {
                for(int i = 1; i< fragmentManager.getFragments().size(); i++) {
                    fragmentTransaction.remove(fragmentManager.getFragments().get(i));
                }
            }
            if (mPosition == 0) {
                mIngredientsDetailFragment = new IngredientsDetailFragment();
                mIngredientsDetailFragment.setContext(mContext);
                mIngredientsDetailFragment.setmIngredientsList(recipe.getIngredientsList());
                fragmentTransaction.add(R.id.ingredients_detail_fragment, mIngredientsDetailFragment);
                if(mStepDetailsFragment != null) {
                    fragmentTransaction.remove(mStepDetailsFragment);
                }

            } else if (mPosition > 0) {
                mStepDetailsFragment = new StepDetailsFragment();
                mStepDetailsFragment.setContext(mContext);
                mStepDetailsFragment.setDescription(recipe.getStepsList().get(mPosition-1).getDescription());
                mStepDetailsFragment.setVideoUrl(recipe.getStepsList().get(mPosition-1).getVideoUrl());
                mStepDetailsFragment.setmThumbnailUrl(recipe.getStepsList().get(mPosition-1).getThumbnailUrl());
                fragmentTransaction.add(R.id.steps_detail_fragment, mStepDetailsFragment);
                if (mIngredientsDetailFragment != null) {
                    fragmentTransaction.remove(mIngredientsDetailFragment);
                }
            }
        } else {
            if (mPosition == 0) {
                mIngredientsDetailFragment = new IngredientsDetailFragment();
                mIngredientsDetailFragment.setContext(mContext);
                mIngredientsDetailFragment.setmIngredientsList(recipe.getIngredientsList());
                mIngredientsDetailFragment.setFragmentManager(mFragmentManager);
                fragmentTransaction.replace(R.id.fragment_placeholder, mIngredientsDetailFragment);
                if(mStepDetailsFragment != null) {
                    fragmentTransaction.remove(mStepDetailsFragment);
                }
            } else if (mPosition > 0) {
                mStepDetailsFragment = new StepDetailsFragment();
                mStepDetailsFragment.setContext(mContext);
                mStepDetailsFragment.setDescription(recipe.getStepsList().get(mPosition-1).getDescription());
                mStepDetailsFragment.setVideoUrl(recipe.getStepsList().get(mPosition-1).getVideoUrl());
                mStepDetailsFragment.setmThumbnailUrl(recipe.getStepsList().get(mPosition-1).getThumbnailUrl());
                mStepDetailsFragment.setFragmentManager(mFragmentManager);
                fragmentTransaction.replace(R.id.fragment_placeholder, mStepDetailsFragment);
                if (mIngredientsDetailFragment != null) {
                    fragmentTransaction.remove(mIngredientsDetailFragment);
                }
            }
        }
        fragmentTransaction.commit();
    }

    protected static StepsListFragment getStepsListFragment(Recipe recipe) {
        StepsListFragment stepsListFragment = new StepsListFragment();
        List<Ingredient> ingredients = recipe.getIngredientsList();
        List<Step> steps = recipe.getStepsList();
        List<String> items = new ArrayList<>();
        if(ingredients != null && ingredients.size()>0 && steps != null && steps.size()>0 ){
            items.add(INGREDIENTS_STEP);
            for(int i=0; i<steps.size(); i++) {
                items.add(steps.get(i).getShortDescription());
            }
            stepsListFragment.setIngredientsList(ingredients);
            stepsListFragment.setStepsList(steps);
            stepsListFragment.setItemsList(items);
        }

        return stepsListFragment;
    }

    public int getPosition() {
        return mPosition;
    }

    public static void setPosition(int position) {
        mPosition = position;
        updateUI(mRecipe, mFragmentManager);
    }

    @Override
    public void onFragmentInteraction(Uri uri) { updateUI(mRecipe, mFragmentManager); }

    public static Recipe getRecipe() {
        return mRecipe;
    }

    public static Window getmWindow() {
        return mWindow;
    }

    public static ActionBar getRecipeActionBar() {
        return mActionBar;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RECIPE, mRecipe);
        if (mFragmentManager.getFragments().get(mFragmentManager.getFragments().size()-1) instanceof StepsListFragment) {
            mPosition = -1;
        }
        outState.putInt(EXTRA_POSITION, mPosition);
    }
}
