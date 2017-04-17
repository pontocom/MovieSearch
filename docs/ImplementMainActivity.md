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

```java
public class MainActivity extends AppCompatActivity {
    protected EditText etSearch;
    protected ListView lvSearchResults;

    protected String[] moviesResult;
    protected ArrayList<Movie> arrayOfMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = (EditText) findViewById(R.id.etSearch);
        lvSearchResults = (ListView) findViewById(R.id.lvMoviesResults);

        lvSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("APP", "Movie selected = " + arrayOfMovies.get(i).getMovieName());
                Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                intent.putExtra("MOVIENAME", arrayOfMovies.get(i).getMovieName());
                intent.putExtra("MOVIEYEAR", arrayOfMovies.get(i).getMovieYear());
                intent.putExtra("MOVIEIMDBID", arrayOfMovies.get(i).getMovieIMDBid());
                intent.putExtra("MOVIEPOSTER", arrayOfMovies.get(i).getMoviePoster());
                startActivity(intent);
            }
        });
    }

    public void clicarPesquisar(View v) {
        String searchText = "";
        try {
            searchText = URLEncoder.encode(etSearch.getText().toString(), "utf-8");
        } catch (Exception e) {
            Log.i("MovieDatabase", "Wrong encoding " + e.getMessage());
        }

        etSearch.setText("");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.omdbapi.com/?s=" + searchText,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MovieDatabase", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if(jsonObject.getString("Response").compareTo("True") == 0) {
                                JSONArray listMovies = jsonObject.getJSONArray("Search");

                                arrayOfMovies = new ArrayList<Movie>();

                                moviesResult = new String[listMovies.length()];
                                for(int i = 0; i < listMovies.length(); i++) {
                                    JSONObject jmovie = listMovies.getJSONObject(i);
                                    Log.i("MovieDatabase", jmovie.getString("Title"));
                                    moviesResult[i] = jmovie.getString("Title");
                                    arrayOfMovies.add(i, new Movie(jmovie.getString("Title"), jmovie.getString("Year"), jmovie.getString("imdbID"), jmovie.getString("Poster")));
                                }

                                ArrayAdapter<String> moviesArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, moviesResult);
                                lvSearchResults.setAdapter(moviesArrayAdapter);

                            } else {
                                Toast.makeText(MainActivity.this, "Some error occured while getting movies information!", Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            Log.i("GuideMeApp", "Error processing the JSON answer -> " + e.getMessage());
                            e.printStackTrace();
                        }
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

    }
}

```

In the next step, we will [implement the MovieDetails activity](https://github.com/pontocom/MovieSearch/blob/master/docs/ImplementMovieDetailsActivity.md).