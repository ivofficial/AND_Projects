package gwg.ivo.android.popularmovies;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import gwg.ivo.android.popularmovies.utils.Movie;
import gwg.ivo.android.popularmovies.utils.MoviesAPIConnect;
import gwg.ivo.android.popularmovies.utils.MoviesAPIParse;
import gwg.ivo.android.popularmovies.utils.MoviesAdapter;

public class MainActivity extends AppCompatActivity {

    private String result = "";
    private List<String > moviesJson = new ArrayList<>();
    private List<Movie> moviesList = new ArrayList<>();

    private static final String IMDB_IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE_W92 = "w92";
    private static final String IMAGE_SIZE_W154 = "w154";
    private static final String IMAGE_SIZE_W185 = "w185";
    private static final String IMAGE_SIZE_W342 = "w342";
    private static final String IMAGE_SIZE_W500 = "w500";
    private static final String IMAGE_SIZE_W780 = "w780";
    private static final String IMAGE_SIZE_ORIGINAL = "original";

    public static String DEFAULT_VIEW = "default_view";
    public static String POPULARITY_VIEW = "popularity_view";
    public static String RATING_VIEW = "rating_view";
    public static String CURRENT_VIEW = "current_view";
    public static String JSON_RESULT = "json_result";
    public static String GRID_POSITION = "grid_position";

    private String viewSelection = DEFAULT_VIEW;
    public int gridPosition = 0;
    public static int backPosition = 0;
    public static String backNavigation = "";
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        gridView.setSelection(gridPosition);
//        gridView.smoothScrollToPosition(gridPosition);

        if (savedInstanceState != null ){
            viewSelection = savedInstanceState.getString(CURRENT_VIEW);
            result = savedInstanceState.getString(JSON_RESULT);
            gridPosition = savedInstanceState.getInt(GRID_POSITION);
        }
        AsyncTask<String, Void, String> task = new ConnectAsyncTask();
        if (!isOnline()) {
            onError();
        } else {
            task.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.popular_action) {
           viewSelection = POPULARITY_VIEW;
           backNavigation = "";
           AsyncTask<String, Void, String> task = new ConnectAsyncTask();
           task.execute();
        }
        if (id == R.id.rating_action) {
            viewSelection = RATING_VIEW;
            AsyncTask<String, Void, String> task = new ConnectAsyncTask();
            task.execute();
        }

        if (id == R.id.favorites_action) {
            launchFavorites();
        }

        return super.onOptionsItemSelected(item);
    }

    private class ConnectAsyncTask extends AsyncTask<String, Void, String> {

        String jsonResult = "";


        @Override
        protected String doInBackground(String... strings) {

            if (!isOnline()) {
                onError();
                return null;
            }

            if (backNavigation.equals(RATING_VIEW)) {
                viewSelection = RATING_VIEW;
            }

            try {

                if (viewSelection == null) {
                    viewSelection = DEFAULT_VIEW;
                }

                if (viewSelection.equals(DEFAULT_VIEW)) {
                    jsonResult = MoviesAPIConnect.getResponseFromHttpUrl(MoviesAPIConnect.moviesByPopularity());
                } else if (viewSelection.equals(POPULARITY_VIEW)) {
                    jsonResult = MoviesAPIConnect.getResponseFromHttpUrl(MoviesAPIConnect.moviesByPopularity());
                } else if (viewSelection.equals(RATING_VIEW)) {
                    jsonResult = MoviesAPIConnect.getResponseFromHttpUrl(MoviesAPIConnect.moviesByTopRated());
                } else {
                    jsonResult = MoviesAPIConnect.getResponseFromHttpUrl(MoviesAPIConnect.moviesByPopularity());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (!isOnline()) {
                onError();
                return;
            }

            MainActivity.this.setResult(jsonResult);
            MainActivity.this.populateUI();
        }
    }

    private List<Movie> getMovies(List<String> jsonList) {

        List<Movie> movies = new ArrayList<>();
        Movie movie = new Movie();

        if (jsonList != null && jsonList.size() > 0) {
            for (int i=0; i<jsonList.size(); i++) {
                movie = MoviesAPIParse.getMovie(jsonList.get(i));
                movies.add(movie);
            }
        }

        return movies;
    }

    private void launchMovieDetail(int position) {

        Movie movie = moviesList.get(position);

        Intent intent = new Intent(this, MovieActivity.class);

        if (movie != null ) {
            intent.putExtra(MovieActivity.EXTRA_MOVIE_ID, movie.getMovieId());
            intent.putExtra(MovieActivity.EXTRA_TITLE, movie.getTitle());
            String posterUrl = IMDB_IMAGE_URL + IMAGE_SIZE_W185 + movie.getPosterPath();
            intent.putExtra(MovieActivity.EXTRA_POSTER, posterUrl);
            intent.putExtra(MovieActivity.EXTRA_OVERVIEW, movie.getOverview());
            intent.putExtra(MovieActivity.EXTRA_RATING, movie.getVoteAverage());
            intent.putExtra(MovieActivity.EXTRA_DATE, movie.getReleaseDate());
            intent.putExtra(MovieActivity.EXTRA_PARENT_VIEW, viewSelection);
        }

        startActivity(intent);
    }

    private void launchFavorites() {
        Intent intent = new Intent(this, FavoritesActivity.class);
        startActivity(intent);
    }

    protected void setResult(String _result) {
        result = _result;
    }

    protected void populateUI() {

        moviesJson = MoviesAPIParse.getMoviesFromJson(result);
        moviesList = getMovies(moviesJson);

        gridView = (GridView) findViewById(R.id.grid_view);
        MoviesAdapter adapter = new MoviesAdapter(this,moviesList);
        gridView.setBackgroundColor(Color.LTGRAY);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                backPosition = position;
                launchMovieDetail(position);
            }
        });

        if (backPosition > 0) {
            gridView.setSelection(backPosition);
        } else {
            gridView.setSelection(gridPosition);
        }
        backPosition = 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENT_VIEW, viewSelection);
        outState.putString(JSON_RESULT, result);
        gridPosition = gridView.getFirstVisiblePosition();
        outState.putInt(GRID_POSITION, gridPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString(CURRENT_VIEW, viewSelection);
        outState.putString(JSON_RESULT, result);
        outState.putInt(GRID_POSITION, gridPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        viewSelection = savedInstanceState.getString(CURRENT_VIEW);
        result = savedInstanceState.getString(JSON_RESULT);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void onError() {
        setContentView(R.layout.error_layout);
        TextView errorTV = (TextView) findViewById(R.id.error_tv);
        errorTV.setText(R.string.error_message);
        errorTV.setTextColor(Color.RED);
    }

}
