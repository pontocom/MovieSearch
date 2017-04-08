package pt.iscte.daam.moviesearch.model;

/**
 * Created by cserrao on 08/04/2017.
 */

public class Movie {
    public String movieName;
    public String movieYear;
    public String movieIMDBid;
    public String moviePoster;

    public Movie(String movieName, String movieYear, String movieIMDBid, String moviePoster) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.movieIMDBid = movieIMDBid;
        this.moviePoster = moviePoster;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getMovieIMDBid() {
        return movieIMDBid;
    }

    public void setMovieIMDBid(String movieIMDBid) {
        this.movieIMDBid = movieIMDBid;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }
}
