package gwg.ivo.android.popularmovies.utils;

import java.util.List;

public class Movie {

    private String movieId;
    private String voteCount;
    private String voteAverage;
    private String title;
    private String popularity;
    private String posterPath;
    private String backdropPath;
    private String overview;
    private String releaseDate;
    private List<String> genres;

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

}
