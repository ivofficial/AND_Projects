package gwg.ivo.android.popularmovies;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import gwg.ivo.android.popularmovies.utils.MoviesAPIParse;
import gwg.ivo.android.popularmovies.db.MovieContract.MovieEntry;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_POSTER = "extra_poster";
    public static final String EXTRA_OVERVIEW = "extra_overview";
    public static final String EXTRA_RATING = "extra_rating";
    public static final String EXTRA_DATE = "extra_date";
    public static final String EXTRA_PARENT_VIEW = "extra_parent_view";

    public static final int INDEX_AUTHOR = 0;
    public static final int INDEX_CONTENT = 1;

    private String parentView = "";
    private String movieId = "";
    private String title = "";
    private String posterUrl = "";
    private String overview = "";
    private String rating = "";
    private String releaseDate = "";

    private View mView;
    private ContentValues mContentValues;

    private OnFragmentInteractionListener mListener;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieDetailsFragment.
     */
    public static MovieDetailsFragment newInstance(Bundle args) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        populateMovieDetails(view);

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

    private void populateMovieDetails(final View _view) {
        Bundle extras = this.getArguments();

        movieId = extras.getString(EXTRA_MOVIE_ID);
        title = extras.getString(EXTRA_TITLE);
        posterUrl = extras.getString(EXTRA_POSTER);
        overview = extras.getString(EXTRA_OVERVIEW);
        rating = extras.getString(EXTRA_RATING);
        releaseDate = extras.getString(EXTRA_DATE);
        parentView= extras.getString(EXTRA_PARENT_VIEW);

        if (_view == null) {
            System.out.println("NULL");
        }

        ImageView posterIV = (ImageView) _view.findViewById(R.id.poster_iv);

        Picasso.with(getActivity())
                .load(posterUrl)
                .error(R.drawable.image48x48)
                .placeholder(R.drawable.image48x48)
                .into(posterIV);

        TextView titleTV = (TextView) _view.findViewById(R.id.title_tv);
        titleTV.setText(title);

        TextView ratingTV = (TextView) _view.findViewById(R.id.rating_tv);
        ratingTV.setText(rating);

        TextView dateTV = (TextView) _view.findViewById(R.id.date_tv);
        dateTV.setText(releaseDate);

        TextView overviewTV = (TextView) _view.findViewById(R.id.overview_tv);
        overviewTV.setText(overview);

        ConstraintLayout constraintLayout = (ConstraintLayout) _view.findViewById(R.id.fragment_movie_details_layout);
        constraintLayout.setBackgroundColor(Color.parseColor(FavoritesDetailActivity.BACKGROUND_COLOR));

        ImageButton imageButton = (ImageButton) _view.findViewById(R.id.add_favorites_button);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieEntry.COLUMN_MOVIE_ID, movieId);
                contentValues.put(MovieEntry.COLUMN_TITLE, title);
                contentValues.put(MovieEntry.COLUMN_OVERVIEW, overview);
                contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, releaseDate);
                contentValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, rating);

                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
                String currentDate = sdf.format(date);
                contentValues.put(MovieEntry.COLUMN_DATE_ADDED, currentDate);

                String imageUrl = posterUrl;
                Bitmap bitmap = MoviesAPIParse.getMovieImage(imageUrl);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
                byte[] imageBlob = outputStream.toByteArray();
                contentValues.put(MovieEntry.COLUMN_POSTER_IMAGE, imageBlob);

                setView(_view, contentValues);
                AsyncTask<String,Void,String> task  = new DatabaseAsyncTask();
                task.execute();
            }
        });
    }

    private class DatabaseAsyncTask extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... strings) {

            if( mView == null || mContentValues == null) {
                return null;
            }

            mView.getContext().getContentResolver().insert(MovieEntry.CONTENT_URI, mContentValues);

            return null;
        }
    }

    private void setView(View _view, ContentValues cv) {
        mView = _view;
        mContentValues = cv;
    }
}
