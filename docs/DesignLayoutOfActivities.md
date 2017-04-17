# Design the layout of the activities in the project
In this stage, it will be important to design the layout of the two activities that will be used in the application (`MainActivity` and `MovieDetailsActivity`).

## Design the MainActivity
The following image displays the design of layout of the `MainActivity`.

![MainActivity layout](https://github.com/pontocom/MovieSearch/blob/master/docs/images/Voila_Capture%202017-04-08_03-04-09_PM.png)

The views of this activity are:
* An `EditText` that will allow the user to input the text corresponding to the movie search: `etSearch`
* A `ListView` that will be used to display the search results obtained from the remote API: `lvMoviesResults`

## Design the MovieDetailsActivity
The following image displays the design of layout of the `MovieDetailsActivity`.

![MovieDetailsActivity layout](https://github.com/pontocom/MovieSearch/blob/master/docs/images/Voila_Capture%202017-04-08_03-18-40_PM.png)

The views of this activity are:
* A `TextView` that will display the movie name: `tvMovieName`
* A `TextView` that will display the movie year: `tvMovieYear`
* An `ImageView` that will display the image of the poster movie: `ivMoviePoster`
* A `TextView` that will display the movie director: `tvMovieDirector`
* A `TextView` that will display the movie writer: `tvMovieWriter`
* A `TextView` that will display the movie actors: `tvMovieActors`
* A `TextView` that will display the movie plot: `tvMovieDescription`

The next step will to implement the functionality of the MainActivity.
