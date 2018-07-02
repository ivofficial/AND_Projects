package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesAPIParse {


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
