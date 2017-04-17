# Create a Model for movies basic information
It is useful to create a model/class to hold the basic information for the movies we are going to retrieve 
from the remote API. This may be different depending on the information you wish to hold. In this example,
we are going to hold the following information:

* the movie title (`movieName`)
* the movie year (`movieName`)
* the IMDB id for the movie (`movieIMDBid`)
* the image URL of the poster for the movie (`moviePoster`)

Create a new Java Class with Android Studio and give a name. For clarity sake, name it `Movie`. So the class 
will contain the following code:

```java
public class Movie {
    public String movieName;
    public String movieYear;
    public String movieIMDBid;
    public String moviePoster;
}
```

Next, add the class constructor:

```java

    public Movie(String movieName, String movieYear, String movieIMDBid, String moviePoster) {
        this.movieName = movieName;
        this.movieYear = movieYear;
        this.movieIMDBid = movieIMDBid;
        this.moviePoster = moviePoster;
    }

```

Finally, add the different class getters and setters:

```java
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
```