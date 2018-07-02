package gwg.ivo.android.popularmovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;
import gwg.ivo.android.popularmovies.utils.FavoritesAdapter;
import gwg.ivo.android.popularmovies.utils.Movie;
import gwg.ivo.android.popularmovies.db.MovieContract.MovieEntry;

public class FavoritesActivity extends AppCompatActivity {

    private Cursor cursor;
    private List<Object[]> resultsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        AsyncTaskLoader<Cursor> cursorAsyncTaskLoader = new FavoritesAsyncTask(this);
        cursor = cursorAsyncTaskLoader.loadInBackground();
        populateUI();

    }

    private class FavoritesAsyncTask extends AsyncTaskLoader<Cursor> {

        public FavoritesAsyncTask(Context context) {
            super(context);
        }

        @Override
        public Cursor loadInBackground() {
            Cursor _cursor = null;
            _cursor = getContentResolver().query(MovieEntry.CONTENT_URI, null, null, null, MovieEntry.COLUMN_DATE_ADDED);

            setCursor(_cursor);
            return _cursor;
        }

        private void setCursor(Cursor _cursor) {
            cursor = _cursor;
        }
    }

    private void getMoviesFromCursor(Cursor _cursor) {
        if (_cursor == null || _cursor.getCount()<=0) {
            return;
        } else {
            _cursor.moveToFirst();
        }

        if(resultsList==null) {
            resultsList = new ArrayList<>();
        }



       for (int i=0; i<_cursor.getCount(); i++) {
           Movie _movie = new Movie();
           _movie.setMovieId(_cursor.getString(_cursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID)));
           _movie.setTitle(_cursor.getString(_cursor.getColumnIndex(MovieEntry.COLUMN_TITLE)));
           _movie.setOverview(_cursor.getString(_cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)));
           _movie.setReleaseDate(_cursor.getString(_cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE)));
           _movie.setVoteAverage(_cursor.getString(_cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE)));
           byte[] imageBytes = _cursor.getBlob(_cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_IMAGE));
           String dateAdded = _cursor.getString(_cursor.getColumnIndex(MovieEntry.COLUMN_DATE_ADDED));
           Object[] objects = {_movie, imageBytes, dateAdded};
           resultsList.add(objects);
           _cursor.moveToNext();
       }
    }

    private void populateUI() {
        getMoviesFromCursor(cursor);
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(this,resultsList);
        GridView gridView = (GridView) findViewById(R.id.grid_view_favorites);
        gridView.setBackgroundColor(Color.parseColor(FavoritesDetailActivity.BACKGROUND_COLOR));
        gridView.setAdapter(favoritesAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchMovieDetail(position);
            }
        });
    }

    private void launchMovieDetail(int position) {

        Object[] objects = resultsList.get(position);
        if (objects==null) {
            return;
        }
        Movie movie = (Movie) objects[FavoritesAdapter.MOVIE_INDEX];
        byte[] imageByteArray = (byte[]) objects[FavoritesAdapter.BITMAP_INDEX];
        String dateAdded = (String) objects[FavoritesAdapter.DATE_ADDED_INDEX];

        Intent intent = new Intent(this, FavoritesDetailActivity.class);

        if (movie != null ) {
            intent.putExtra(FavoritesDetailActivity.EXTRA_TITLE, movie.getTitle());
            intent.putExtra(FavoritesDetailActivity.EXTRA_POSTER, imageByteArray);
            intent.putExtra(FavoritesDetailActivity.EXTRA_OVERVIEW, movie.getOverview());
            intent.putExtra(FavoritesDetailActivity.EXTRA_RATING, movie.getVoteAverage());
            intent.putExtra(FavoritesDetailActivity.EXTRA_DATE, movie.getReleaseDate());
            intent.putExtra(FavoritesDetailActivity.EXTRA_DATE_ADDED, dateAdded);
        }

        startActivity(intent);
    }
}
