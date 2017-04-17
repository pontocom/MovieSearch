# Implement the MovieDetailsActivity functionality
This will be used to implement and present to the end-user the details of the movie that the user selected on the previous activity `MainActivity`

## Handling the connection to the layout views
This first thing to accomplish is to create the connection between the views that were placed in the layout design (XML) and the the `MovieDetailsActivity.java`.

`````java
`public class MovieDetailsActivity extends AppCompatActivity {  
    protected TextView tvMovieName;  
    protected TextView tvMovieYear;  
    protected ImageView ivMoviePoster;  
    protected TextView tvMovieDirector;  
    protected TextView tvMovieWriter;  
    protected TextView tvMovieActors;  
    protected TextView tvMovieDescription;
`````
`
