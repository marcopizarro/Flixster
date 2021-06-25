package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=4bab4d46d41c0ce6d10cae2f509dbd10";
    public static final String TAG = "MainActivity";

    List<Movie> movies;
    RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        movies = new ArrayList<Movie>();

        MovieAdapter.OnClickListener onClickListener = new MovieAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.i("MainActivity", "good");
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra("title", movies.get(position).getTitle());
                i.putExtra("description", movies.get(position).getOverview());
                i.putExtra("poster", movies.get(position).getPosterPath());
                i.putExtra("rating", movies.get(position).getRating() / 2);
                Log.i("MainActivity", movies.get(position).getRating().toString());
                MainActivity.this.startActivity(i);
            }
        };
        //create adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies, onClickListener);
        //set adapter on recycler view
        binding.rvMovies.setAdapter(movieAdapter);
        //set layout manager
        binding.rvMovies.setLayoutManager(new LinearLayoutManager(this ));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "results: " + results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit JSON exception", e);
                }
                Log.d(TAG, "success");
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "fail");
            }
        });
    }
}