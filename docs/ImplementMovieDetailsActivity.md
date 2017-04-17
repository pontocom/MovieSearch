# Implement the MovieDetailsActivity functionality
This will be used to implement and present to the end-user the details of the movie that the user selected on the previous activity `MainActivity`

## Handling the connection to the layout views
This first thing to accomplish is to create the connection between the views that were placed in the layout design (XML) and the the `MovieDetailsActivity.java`.

````java
public class MovieDetailsActivity extends AppCompatActivity {  
    protected TextView tvMovieName;  
    protected TextView tvMovieYear;  
    protected ImageView ivMoviePoster;  
    protected TextView tvMovieDirector;  
    protected TextView tvMovieWriter;  
    protected TextView tvMovieActors;  
    protected TextView tvMovieDescription;  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_movie_details);  
  
        tvMovieName = (TextView) findViewById(R.id.tvMovieName);  
        tvMovieYear = (TextView) findViewById(R.id.tvMovieYear);  
        ivMoviePoster = (ImageView) findViewById(R.id.ivMoviePoster);  
        tvMovieDirector = (TextView) findViewById(R.id.tvMovieDirector);  
        tvMovieWriter = (TextView) findViewById(R.id.tvMovieWriter);  
        tvMovieActors = (TextView) findViewById(R.id.tvMovieActors);  
        tvMovieDescription = (TextView) findViewById(R.id.tvMovieDescription);  
    }  
}  
````

## Obtain data from Intent passed to this activity
Data was passed to this **Activity** from the `MainActivity` activity, and therefore it is necessary to get the **Intent** and the _information it contains_. This is also done inside the `onCreate` function.

````java
String IMDBid = "";  
Intent intent = getIntent();  
tvMovieName.setText(intent.getStringExtra("MOVIENAME"));  
tvMovieYear.setText(intent.getStringExtra("MOVIEYEAR"));  
IMDBid = intent.getStringExtra("MOVIEIMDBID");
````

With this, data from the **Intent** is obtained and part of it (**MOVIENAME**, **MOVIEYEAR**) can be immediately used to update the views in the layout.

## Display the movie poster
Another piece of information received with the **Intent** is the **URL** of the **image** that contains the movie poster we want to display for the user (**MOVIEPOSTER**).



## Obtain more details from the API service
Since with the previous service search we only got some of the details of the movie, we need to make a second request, to baton further details from the movie. This new request will be made using the **IMDB id** of the movie - this is store on the `IMDBid` variable.
