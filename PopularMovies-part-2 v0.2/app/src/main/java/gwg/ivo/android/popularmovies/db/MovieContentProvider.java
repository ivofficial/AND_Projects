package gwg.ivo.android.popularmovies.db;

import gwg.ivo.android.popularmovies.db.MovieContract.MovieEntry;
import gwg.ivo.android.popularmovies.utils.Movie;
import gwg.ivo.android.popularmovies.utils.MoviesAPIParse;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MovieContentProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIES_WITH_ID = 101;
    Context mContext;

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private MovieDbHelper movieDbHelper;

    public static UriMatcher buildUriMatcher() {
        UriMatcher _uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        _uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        _uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIES_WITH_ID);

        return _uriMatcher;
    }

    @Override
    public boolean onCreate() {

        Context context = getContext();
        movieDbHelper = new MovieDbHelper(context);
        return false;
    }

    public Cursor queryAllMovies() {
        if (mContext==null) {
            System.out.println("Test3");
        }

        if(movieDbHelper==null) {
            System.out.println("Test1");
        }


        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();
        if(db==null) {
            System.out.println("Test2");
        }
        Cursor returnCursor = db.query(MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if(returnCursor==null) {
            System.out.println("Test3");
        }

        return returnCursor;
    }

    public Cursor queryMovie(String _movieId) {
        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();
        String selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = {_movieId};
        Cursor returnCursor = db.query(MovieEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        return returnCursor;
    }

    public long queryAddMovie(Movie movie) {
        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.COLUMN_MOVIE_ID, movie.getMovieId());
        contentValues.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
        contentValues.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
        String currentDate = sdf.format(date);
        contentValues.put(MovieEntry.COLUMN_DATE_ADDED, currentDate);

        String imageUrl = MoviesAPIParse.IMDB_IMAGE_URL + MoviesAPIParse.IMAGE_SIZE_W185 + movie.getPosterPath();
        Bitmap bitmap = MoviesAPIParse.getMovieImage(imageUrl);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        byte[] imageBlob = outputStream.toByteArray();
        contentValues.put(MovieEntry.COLUMN_POSTER_IMAGE, imageBlob);


        long insertResult =db.insert(MovieEntry.TABLE_NAME,null, contentValues);

        return insertResult;
    }

    public int deleteMovie(String _movieId) {
        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();
        String selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = {_movieId};
        int result = db.delete(MovieEntry.TABLE_NAME,
                selection,
                selectionArgs);

        return result;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = movieDbHelper.getReadableDatabase();
        int match = uriMatcher.match(uri);
        Cursor returnCursor;

        switch (match) {
            case MOVIES:
                returnCursor = db.query(MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES:
                long id = db.insert(MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into: " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        int moviesDeleted;

        switch (match) {
            case MOVIES_WITH_ID:
                String id = uri.getPathSegments().get(1);
                moviesDeleted = db.delete(MovieEntry.TABLE_NAME,
                        "_id=?",
                        new String[] {id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        if(moviesDeleted !=0 ) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return moviesDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    public void setContext(Context _context) {
        mContext = _context;
    }
}
