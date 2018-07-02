package gwg.ivo.android.popularmovies;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gwg.ivo.android.popularmovies.utils.MoviesAPIConnect;
import gwg.ivo.android.popularmovies.utils.MoviesAPIParse;
import gwg.ivo.android.popularmovies.utils.TrailersAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrailersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrailersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrailersFragment extends Fragment {
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    private String movieId = "";
    private String result = "";
    private List<String> trailersJson = new ArrayList<>();
    private List<String> trailersList = new ArrayList<>();

    private View mView;

    private OnFragmentInteractionListener mListener;

    public TrailersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TrailersFragment.
     */
    public static TrailersFragment newInstance(Bundle args) {
        TrailersFragment fragment = new TrailersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trailers, container, false);
        setView(view);
        movieId = this.getArguments().getString(EXTRA_MOVIE_ID);
        AsyncTask<String, Void, String> task = new ConnectAsyncTask();
        task.execute();

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    private class ConnectAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            try {
                String _result = MoviesAPIConnect.getResponseFromHttpUrl(MoviesAPIConnect.getMovieVideos(movieId));
                setResult(_result);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            setResult(result);
            populateTrailers(result, mView);
        }
    }

    private void setResult(String _result) {
        result = _result;
    }

    private void setView(View _view) {
        mView = _view;
    }

    private void populateTrailers(String jsonResult, View _view) {

        trailersJson = MoviesAPIParse.getTrailersFromJson(jsonResult);

        if (trailersJson != null && trailersJson.size()>0) {
            for(int i=0; i<trailersJson.size(); i++) {
                trailersList.add(MoviesAPIParse.getTrailerUri(trailersJson.get(i)));
            }
        }

        RecyclerView recyclerView = (RecyclerView) _view.findViewById(R.id.trailers_rv);
        TrailersAdapter adapter = new TrailersAdapter(trailersList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(_view.getContext().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(Color.parseColor(FavoritesDetailActivity.BACKGROUND_COLOR));
        recyclerView.setScrollbarFadingEnabled(false);
        adapter.notifyDataSetChanged();
        ConstraintLayout constraintLayout = (ConstraintLayout) _view.findViewById(R.id.layout_fragment_trailers);
        constraintLayout.setBackgroundColor(Color.parseColor(FavoritesDetailActivity.BACKGROUND_COLOR));
    }
}
