package gwg.ivo.android.popularmovies.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    public static final String AUTHORITY = "gwg.ivo.android.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_DATE_ADDED = "date_added";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POSTER_IMAGE = "poster_image";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";
    }
}
