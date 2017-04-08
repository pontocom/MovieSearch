package pt.iscte.daam.moviesearch;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.json.JSONObject;

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

        String IMDBid = "";
        Intent intent = getIntent();
        tvMovieName.setText(intent.getStringExtra("MOVIENAME"));
        tvMovieYear.setText(intent.getStringExtra("MOVIEYEAR"));
        IMDBid = intent.getStringExtra("MOVIEIMDBID");

        Glide
                .with(MovieDetailsActivity.this)
                .load(intent.getStringExtra("MOVIEPOSTER"))
                .asBitmap()
                .into(new BitmapImageViewTarget(ivMoviePoster) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);

                    }
                });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.omdbapi.com/?i=" + IMDBid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("MovieDatabase", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("Response").compareTo("True") == 0) {
                                tvMovieDescription.setText(jsonObject.getString("Plot"));
                                tvMovieActors.setText(jsonObject.getString("Actors"));
                                tvMovieDirector.setText(jsonObject.getString("Director"));
                                tvMovieWriter.setText(jsonObject.getString("Writer"));

                            } else {
                                Toast.makeText(MovieDetailsActivity.this, "Some error occured while getting movies information!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.i("MoviesDatabase", "Error processing the JSON answer -> " + e.getMessage());
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
