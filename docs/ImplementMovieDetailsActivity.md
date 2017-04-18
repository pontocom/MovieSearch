# Implement the MovieDetailsActivity functionality
This will be used to implement and present to the end-user the details of the movie that the user selected on the previous activity `MainActivity`.

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

For this, we are going to use Glide, that will save a lot of time to download, cache and display remote images.

````java
Glide  
        .with(MovieDetailsActivity.this)  
        .load(intent.getStringExtra("MOVIEPOSTER"))  
        .asBitmap()  
        .into(new BitmapImageViewTarget(ivMoviePoster) {  
            @Override  
            public void onResourceReady(Bitmap resource, GlideAnimation\<? super Bitmap\> glideAnimation) {  
                super.onResourceReady(resource, glideAnimation);  
  
            }  
        });
````

## Obtain more details from the API service
Since with the previous service search we only got some of the details of the movie, we need to make a second request, to baton further details from the movie. This new request will be made using the **IMDB id** of the movie - this is store on the `IMDBid` variable.

### Perform the request to web API
Again, we are going to use Volley for this. So the first thing to be done is to create the request and add it to the queue. And we need to expect and answer.

````java
StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.omdbapi.com/?i=" + IMDBid,  
        new Response.Listener\<String\>() {  
            @Override  
            public void onResponse(String response) {  
            }  
        },  
        new Response.ErrorListener() {  
            @Override  
            public void onErrorResponse(VolleyError error) {  
                Log.i("MovieDatabase", error.toString());  
            }  
        });  
RequestQueue requestQueue = Volley.newRequestQueue(this);  
requestQueue.add(stringRequest);
````

### Receive an answer from the service
We will receive an answer from the service, that will need to be handled on the `onResponse` function on the `response` parameter. This will be an answer in **JSON** format. This answer will need to be processed.

````java
try {  
    JSONObject jsonObject = new JSONObject(response);  
    if(jsonObject.getString("Response").compareTo("True") == 0) {  
          
    } else {  
        Toast.makeText(MovieDetailsActivity.this, "Some error occured while getting movies information!", Toast.LENGTH_SHORT).show();  
    }  
} catch (Exception e) {  
    Log.i("MoviesDatabase", "Error processing the JSON answer -\> " + e.getMessage());  
    e.printStackTrace();  
}
````

### Obtain data and add it to the layout views
After receiving a **JSON** formatted responde we need to make sure it is an **appropriate answer**:

````java
if(jsonObject.getString("Response").compareTo("True") == 0) {
````

If it is, we need to go and extract from the JSON structure the data, and add that data directly into the Activity layout views:

````java
tvMovieDescription.setText(jsonObject.getString("Plot"));  
tvMovieActors.setText(jsonObject.getString("Actors"));  
tvMovieDirector.setText(jsonObject.getString("Director"));  
tvMovieWriter.setText(jsonObject.getString("Writer"));
````

And this is all it takes for the **MovieDetailsActivity**. Next you should try it, test it, and modify it as you wish.