package pt.iscte.daam.moviesearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import pt.iscte.daam.moviesearch.model.Movie;

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
