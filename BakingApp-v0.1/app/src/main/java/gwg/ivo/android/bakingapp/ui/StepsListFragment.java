package gwg.ivo.android.bakingapp.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

import gwg.ivo.android.bakingapp.R;
import gwg.ivo.android.bakingapp.model.Ingredient;
import gwg.ivo.android.bakingapp.model.Step;
import gwg.ivo.android.bakingapp.utils.StepsListRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepsListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepsListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private AdapterView.OnItemClickListener mCallback;
    private List<String> mItemsList;
    private List<Ingredient> mIngredientsList;
    private List<Step> mStepsList;

    public StepsListFragment() {
        // Required empty public constructor
    }


    public static StepsListFragment newInstance(String param1, String param2) {
        StepsListFragment fragment = new StepsListFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(rootView.getContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        StepsListRecyclerViewAdapter adapter = new StepsListRecyclerViewAdapter(mItemsList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public List<String> getItemsList() {
        return mItemsList;
    }

    public void setItemsList(List<String> mItemsList) {
        this.mItemsList = mItemsList;
    }

    public List<Ingredient> getIngredientsList() {
        return mIngredientsList;
    }

    public void setIngredientsList(List<Ingredient> mIngredientsList) {
        this.mIngredientsList = mIngredientsList;
    }

    public List<Step> getStepsList() {
        return mStepsList;
    }

    public void setStepsList(List<Step> mStepsList) {
        this.mStepsList = mStepsList;
    }


}
