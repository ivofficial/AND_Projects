package gwg.ivo.android.popularmovies.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesAPIParse {

    public static final String IMDB_IMAGE_URL = "http://image.tmdb.org/t/p/";
    public static final String IMAGE_SIZE_W92 = "w92";
    public static final String IMAGE_SIZE_W154 = "w154";
    public static final String IMAGE_SIZE_W185 = "w185";
    public static final String IMAGE_SIZE_W342 = "w342";
    public static final String IMAGE_SIZE_W500 = "w500";
    public static final String IMAGE_SIZE_W780 = "w780";
    public static final String IMAGE_SIZE_ORIGINAL = "original";
    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    public static final String YOUTUBE_GET_URL = "http://www.youtube.com/get_video?v=";
    public static final String YOUTUBE_EMBED = "https://www.youtube.com/embed/";



    public static String getReviewAuthor(String json) {

        String author = "";
        try {

            JSONObject jsonObject = new JSONObject(json);
            author = jsonObject.getString("author");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return author;
    }

    public static String getReviewContent(String json) {

        String reviewContent = "";
        try {

            JSONObject jsonObject = new JSONObject(json);
            reviewContent = jsonObject.getString("content");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviewContent;
    }

    public static String getTrailerUri(String json) {
        String videoUri = "";
        String key = "";
        try {

            JSONObject jsonObject = new JSONObject(json);
            key = jsonObject.getString("key");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        videoUri = YOUTUBE_BASE_URL + key;

        return videoUri;
    }

    public static List<String> getReviewsFromJson (String json) {

        List<String> reviews = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultsList = jsonObject.getJSONArray("results");

            if (resultsList != null && resultsList.length()>0) {
                for (int i=0; i<resultsList.length(); i++) {
                    reviews.add(resultsList.getString(i));
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    public static List<String> getTrailersFromJson (String json) {
        List<String> trailers = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultsList = jsonObject.getJSONArray("results");

            if (resultsList != null && resultsList.length()>0) {
                for (int i=0; i<resultsList.length(); i++) {
                    trailers.add(resultsList.getString(i));
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return trailers;
    }

    public static List<String> getMoviesFromJson (String json) {

        List<String> movies = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray resultsList = jsonObject.getJSONArray("results");

            if (resultsList != null && resultsList.length()>0) {
                for (int i=0; i<resultsList.length(); i++) {
                    movies.add(resultsList.getString(i));
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return movies;
    }


    public static Bitmap getMovieImage(String imgSrc) {
        URL url;
        HttpURLConnection connection;
        Bitmap bitmap;
        try {
            url = new URL(imgSrc);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream is = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Movie getMovie(String movieJson) {

        Movie movie = new Movie();
        String movieId = "";
        String voteCount = "";
        String voteAverage = "";
        String title = "";
        String popularity = "";
        String posterPath = "";
        String backdropPath = "";
        String overview = "";
        String releaseDate = "";
        List<String> genres = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(movieJson);
            movieId = jsonObject.getString("id");
            voteCount = jsonObject.getString("vote_count");
            voteAverage = jsonObject.getString("vote_average");
            title = jsonObject.getString("title");
            popularity = jsonObject.getString("popularity");
            posterPath = jsonObject.getString("poster_path");
            backdropPath = jsonObject.getString("backdrop_path");
            overview = jsonObject.getString("overview");
            releaseDate = jsonObject.getString("release_date");
            JSONArray genresArray = jsonObject.getJSONArray("genre_ids");

            if (genresArray != null && genresArray.length()>0) {
                for (int i=0; i<genresArray.length(); i++) {
                    genres.add(genresArray.getString(i));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        movie.setMovieId(movieId);
        movie.setVoteCount(voteCount);
        movie.setVoteAverage(voteAverage);
        movie.setTitle(title);
        movie.setPopularity(popularity);
        movie.setPosterPath(posterPath);
        movie.setBackdropPath(backdropPath);
        movie.setOverview(overview);
        movie.setReleaseDate(releaseDate);
        movie.setGenres(genres);

        return movie;
    }



}
