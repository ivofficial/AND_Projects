package gwg.ivo.android.popularmovies.db;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;

import gwg.ivo.android.popularmovies.utils.Movie;

public class FavoritesAsyncTask extends AsyncTaskLoader<Cursor> {

    private static final int ALL = 10;
    private static final int ADD = 11;
    private static final int REMOVE = 12;

    private int action;
    private Movie movie;

    public FavoritesAsyncTask(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {

        Cursor cursor = null;
        long result;
        MovieContentProvider movieContentProvider = new MovieContentProvider();

        switch (action) {
            case ALL:
                cursor = movieContentProvider.queryAllMovies();
            case ADD:
                result = movieContentProvider.queryAddMovie(movie);
            case REMOVE:
                result = movieContentProvider.deleteMovie(movie.getMovieId());
            default:
                cursor = movieContentProvider.queryAllMovies();
        }

        return cursor;
    }


    public void addMovieToFavorites(Movie _movie) {
        action = ADD;
        movie = _movie;
    }

    public void getAllFavorites() {
        action = ALL;
    }

    public void removeMovieFromFavorites(Movie _movie) {
        action = REMOVE;
        movie = _movie;
    }

}
