package gwg.ivo.android.popularmovies.utils;

import android.net.Uri;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MoviesAPIConnect {

    private static final String API_KEY = "?api_key=cd29509e209a14a502ba87544af35cde";

    private static final String API_TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZDI5NTA5ZTIwOWExNGE1MDJiYTg3NTQ0YWYzNWNkZSIsInN1YiI6IjVhZThlMGE4OTI1MTQxNGQwMDAwMDFlYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.9bqFzFr_E4tITI6ow60ZpdCMBxZuqQByPUDpew4Zs4I";

    private static final String URL_PREFIX = "https://api.themoviedb.org/3";
    private static final String CONFIG_URL = "https://api.themoviedb.org/3/configuration";

    private static final String DATE_GREATER = "primary_release_date.gte";
    private static final String DATE_LOWER = "primary_release_date.lte";
    private static final String RELEASE_YEAR = "primary_release_year";
    private static final String VOTE_COUNT_GREATER = "vote_count.gte";

    //sort_by
    private static final String SORT_BY = "sort_by";
    private static final String POPULARITY_DESC = "popularity.desc";
    private static final String POPULARITY_ASC = "popularity.asc";
    private static final String VOTE_AVG_DESC = "vote_average.desc";
    private static final String VOTE_AVG_ASC = "vote_average.asc";

    private static final String DISCOVER = "/discover/movie";
    private static final String POPULARITY = "/movie/popular";
    private static final String TOP_RATED = "/movie/top_rated";
    private static final String FIND = "/find";
    private static final String SEARCH = "/search";
    private static final String MOVIES = "/movie/";
    private static final String REVIEWS = "/reviews";
    private static final String VIDEOS = "/videos";


    public static URL getMovieReviews(String _movieId) {

        String baseUrl = URL_PREFIX + MOVIES + _movieId + REVIEWS + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }

    public static URL getMovieVideos(String _movieId) {

        String baseUrl = URL_PREFIX + MOVIES + _movieId + VIDEOS + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }



    public static URL moviesByPopularity() {

        String baseUrl = URL_PREFIX + POPULARITY + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }

    public static URL moviesByTopRated() {

        String baseUrl = URL_PREFIX + TOP_RATED + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }



    public static URL sortByDatesBetween(String dateBefore, String dateAfter) {

        String baseUrl = URL_PREFIX + DISCOVER + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(DATE_LOWER, dateBefore)
                .appendQueryParameter(DATE_GREATER, dateAfter)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }

    public static URL sortByDateAfter(String dateAfter) {

        String baseUrl = URL_PREFIX + DISCOVER + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(DATE_GREATER, dateAfter)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }

    public static URL sortByDateBefore(String dateBefore) {

        String baseUrl = URL_PREFIX + DISCOVER + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(DATE_LOWER, dateBefore)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }


    public static URL sortByReleaseYear(String year) {

        String baseUrl = URL_PREFIX + DISCOVER + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(RELEASE_YEAR, year)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }


    public static URL sortByVoteCountAsc() {

        String baseUrl = URL_PREFIX + DISCOVER + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(SORT_BY, VOTE_AVG_ASC)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }


    public static URL sortByVoteCountDesc() {

        String baseUrl = URL_PREFIX + DISCOVER + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(SORT_BY, VOTE_AVG_DESC)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }


    public static URL sortByPopularityAsc() {

        String baseUrl = URL_PREFIX + DISCOVER + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(SORT_BY, POPULARITY_ASC)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }


    public static URL sortByPopularityDesc() {

        String baseUrl = URL_PREFIX + DISCOVER + API_KEY;

        Uri uri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(SORT_BY, POPULARITY_DESC)
                .build();

        URL url = null;

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        StringBuffer buffer = new StringBuffer("");

        try {

            InputStream inputStream = urlConnection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                buffer.append(line);
            }

        } finally {
            urlConnection.disconnect();
        }

        return buffer.toString();
    }


}
