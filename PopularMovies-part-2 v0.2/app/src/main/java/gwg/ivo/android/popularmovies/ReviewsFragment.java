package gwg.ivo.android.popularmovies;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
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
import gwg.ivo.android.popularmovies.utils.ReviewsAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReviewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReviewsFragment extends Fragment {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";

    public static final int INDEX_AUTHOR = 0;
    public static final int INDEX_CONTENT = 1;

    private String movieId = "";
    private String result = "";
    private List<String > reviewsJson = new ArrayList<>();
    private List<String[]> reviewsList = new ArrayList<>();

    private View mView;


    private OnFragmentInteractionListener mListener;

    public ReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ReviewsFragment.
     */

    public static ReviewsFragment newInstance(Bundle args) {
        ReviewsFragment fragment = new ReviewsFragment();
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
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);
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
                String _result = MoviesAPIConnect.getResponseFromHttpUrl(MoviesAPIConnect.getMovieReviews(movieId));
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
            populateReviews(result, mView);
        }
    }

    private void setResult(String _result) {
        result = _result;
    }

    private void populateReviews(String jsonResult, View _view) {

        reviewsJson = MoviesAPIParse.getReviewsFromJson(jsonResult);

        if(reviewsJson != null && reviewsJson.size()>0) {

            for (int i=0; i<reviewsJson.size(); i++) {
                String[] stringArray ={"",""};
                stringArray[INDEX_AUTHOR] = MoviesAPIParse.getReviewAuthor(reviewsJson.get(i).trim());
                stringArray[INDEX_CONTENT] = MoviesAPIParse.getReviewContent(reviewsJson.get(i).trim());
                reviewsList.add(stringArray);
            }
        }

        RecyclerView reviewsRecyclerView = (RecyclerView) _view.findViewById(R.id.reviews_rv);
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(_view.getContext().getApplicationContext());
        reviewsRecyclerView.setLayoutManager(layoutManager);
        reviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewsRecyclerView.setAdapter(reviewsAdapter);
        reviewsRecyclerView.setBackgroundColor(Color.parseColor(FavoritesDetailActivity.BACKGROUND_COLOR));
        reviewsRecyclerView.setScrollbarFadingEnabled(false);
        reviewsRecyclerView.addItemDecoration(new DividerItemDecoration(_view.getContext(), LinearLayoutManager.VERTICAL));
        reviewsAdapter.notifyDataSetChanged();
        ConstraintLayout constraintLayout = (ConstraintLayout) _view.findViewById(R.id.layout_fragment_reviews);
        constraintLayout.setBackgroundColor(Color.parseColor(FavoritesDetailActivity.BACKGROUND_COLOR));
    }

    private void setView(View _view) {
        mView = _view;
    }

}
