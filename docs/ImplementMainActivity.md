# Implement the MainActivity functionality
In this part we will implement all the functionalities in the MainActivity

## Connect to the layout views
Create the connection between the views in layout with the functional part of the activity. 

```java
public class MainActivity extends AppCompatActivity {
    protected EditText etSearch;
    protected ListView lvSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.etSearch);
        lvSearchResults = (ListView) findViewById(R.id.lvMoviesResults);
    }
}
```

## Create a button event function
Create a function `clicarPesquisar` to deal with the event when the user presses the `Search` button.

On the `MainActivity` layout file, on the XML part of the button, add the `onClick` attribute, add the name of function to call `clicarPesquisar`.

```xml
<Button
        android:id="@+id/button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginLeft="8dp"
        android:layout_marginRight="8dp"
        android:layout_marginTop="8dp"
        android:text="Search"
        app:layout_constraintLeft_toRightOf="@+id/etSearch"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_marginStart="8dp"
        android:layout_marginEnd="8dp"
        android:onClick="clicarPesquisar"/>
``` 

Then, on the `MainActivity.class`, create the `clicarPesquisar` function.

```java
    public void clicarPesquisar(View v) {
    }
```

## Implement the search functionality
This section will present the implementation of the `clicarPesquisar` function.

### Handle the search text
After the user inserts text on the search field and presses the search button, the search field will have to cleared, and the text will have to encoded to be passed to the API endpoint.

```java
        String searchText = "";
        try {
            searchText = URLEncoder.encode(etSearch.getText().toString(), "utf-8");
        } catch (Exception e) {
            Log.i("MovieDatabase", "Wrong encoding " + e.getMessage());
        }

        etSearch.setText("");
```

### Build and send the API web request
Using Volley we will prepare and send the API web resquest and prepare to handle the anwer from the remote service.

```java
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.omdbapi.com/?s=" + searchText,
                new Response.Listener<String>() {
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
```

### Handle the JSON answer from web API
On the `onResponse` function, the `String response` variable will contain the response from the remote API. This response is formated using JSON format.

It is necessary to handle this answer and process it appropriately.

```java
                        Log.i("MovieDatabase", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("Response").compareTo("True") == 0) {
                                JSONArray listMovies = jsonObject.getJSONArray("Search");


                            } else {
                                Toast.makeText(MainActivity.this, "Some error occured while getting movies information!", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            Log.i("GuideMeApp", "Error processing the JSON answer -> " + e.getMessage());
                            e.printStackTrace();
                        }
``` 

### Process JSON answer and present results on ListView
If the answer from the service is valid:

```java
if(jsonObject.getString("Response").compareTo("True") == 0)
```

It contains a JSON array with the results that need to be processed:

```json
{
    "Response":
    "True",
    "Search":
    [{
        "Poster":
        "https://images-na.ssl-images-amazon.com/images/M/MV5BMTk2NTI1MTU4N15BMl5BanBnXkFtZTcwODg0OTY0Nw@@._V1_SX300.jpg",
        "Title":
        "The Avengers",
        "Type":
        "movie",
        "Year":
        "2012",
        "imdbID":
        "tt0848228"
    }],
    "totalResults":
    "89"
}
```
Therefore it is necessary to iterate this array and create an array of movies, that will contain the 
list of movies obtained in the answer. It is ncessary to add these two class variables.
 
```java
    protected String[] moviesResult;
    protected ArrayList<Movie> arrayOfMovies;
```

`moviesResult` is an array with the movies name, that will be added to a basic ListView, and `arrayOfMovies` 
contains all the details of a Movie that are store on the model we have created previously.

```java
            arrayOfMovies = new ArrayList<Movie>();

            moviesResult = new String[listMovies.length()];
            for(int i = 0; i < listMovies.length(); i++) {
                JSONObject jmovie = listMovies.getJSONObject(i);
                Log.i("MovieDatabase", jmovie.getString("Title"));
                moviesResult[i] = jmovie.getString("Title");
                arrayOfMovies.add(i, new Movie(jmovie.getString("Title"), jmovie.getString("Year"), jmovie.getString("imdbID"), jmovie.getString("Poster")));
            }
```

### Add the array to the ListView
Finally, the `moviesResult` array is added to the ListView.

```java
            ArrayAdapter<String> moviesArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, moviesResult);
            lvSearchResults.setAdapter(moviesArrayAdapter);
```

## Handle the user selection on the ListView of results
After all the results are displayed on the ListView, it should be possible for the user to select a specific
result and get more details about it.

### Add selection listener to the ListView
On the `onCreate` function, add the ListView listener.

```java
        lvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("APP", "Movie selected = " + arrayOfMovies.get(i).getMovieName());
            }
        });
```

The `onItemClick` function will be called when the user selects an option on the ListView, and receives the 
number of the item selected.

### Build Intent and start the new activity
After the selection of an option on the ListView, an Intent with some information about the movie will be prepared an it will be passed to the MovieDetails Activity.

```java
                Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                intent.putExtra("MOVIENAME", arrayOfMovies.get(i).getMovieName());
                intent.putExtra("MOVIEYEAR", arrayOfMovies.get(i).getMovieYear());
                intent.putExtra("MOVIEIMDBID", arrayOfMovies.get(i).getMovieIMDBid());
                intent.putExtra("MOVIEPOSTER", arrayOfMovies.get(i).getMoviePoster());
                startActivity(intent);
```

In the next step, we will [implement the MovieDetails activity][1].

[1]:	https://github.com/pontocom/MovieSearch/blob/master/docs/ImplementMovieDetailsActivity.md