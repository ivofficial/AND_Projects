package gwg.ivo.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import gwg.ivo.android.bakingapp.MainActivity;
import gwg.ivo.android.bakingapp.R;
import gwg.ivo.android.bakingapp.model.Ingredient;
import gwg.ivo.android.bakingapp.utils.IngredientsListAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IngredientsDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IngredientsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsDetailFragment extends Fragment {

    private List<Ingredient> mIngredientsList;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private OnFragmentInteractionListener mListener;

    public IngredientsDetailFragment() {
        // Required empty public constructor
    }

    public static IngredientsDetailFragment newInstance(String param1, String param2) {
        IngredientsDetailFragment fragment = new IngredientsDetailFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredients_detail, container, false);

        if (mContext != null && mIngredientsList != null) {
            if(!MainActivity.isTablet && !MainActivity.isLandscape && mFragmentManager != null) {
                Button backButton = (Button) rootView.findViewById(R.id.ingredients_back_button);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StepsListFragment stepsListFragment = RecipeDetailActivity.getStepsListFragment(RecipeDetailActivity.getRecipe());
                        mFragmentManager.beginTransaction()
                                .replace(R.id.fragment_placeholder, stepsListFragment)
                                .commit();
                    }
                });
            }

            ListView listView = (ListView) rootView.findViewById(R.id.ingredients_list_view);
            IngredientsListAdapter adapter = new IngredientsListAdapter(mContext, mIngredientsList);
            listView.setAdapter(adapter);
        }
        // Inflate the layout for this fragment
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public List<Ingredient> getmIngredientsList() {
        return mIngredientsList;
    }

    public void setmIngredientsList(List<Ingredient> mIngredientsList) {
        this.mIngredientsList = mIngredientsList;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setFragmentManager(FragmentManager mFragmentManager) {
        this.mFragmentManager = mFragmentManager;
    }

}
