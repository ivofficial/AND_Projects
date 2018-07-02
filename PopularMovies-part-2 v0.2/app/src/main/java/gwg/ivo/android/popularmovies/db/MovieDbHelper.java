package gwg.ivo.android.popularmovies.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import gwg.ivo.android.popularmovies.db.MovieContract.MovieEntry;

import gwg.ivo.android.popularmovies.db.MovieContract;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int VERSION = 1;

    MovieDbHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "+ MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID                  + " INTEGER PRIMARY KEY, " +
                MovieEntry.COLUMN_MOVIE_ID      + " TEXT NOT NULL UNIQUE, " +
                MovieEntry.COLUMN_DATE_ADDED    + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_TITLE         + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_VOTE_AVERAGE  + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_POSTER_IMAGE  + " BLOB NOT NULL, " +
                MovieEntry.COLUMN_RELEASE_DATE  + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_OVERVIEW      + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
