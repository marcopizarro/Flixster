package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieTrailerBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {
    YouTubePlayerView playerView;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        // temporary test video id -- TODO replace with movie trailer video id

        Intent intent = getIntent();
        Log.i("Trailer", String.valueOf(intent.getStringExtra("key")));
        Log.i("Trailer", String.format("/movie/%s/videos", intent.getStringExtra("key")));

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=4bab4d46d41c0ce6d10cae2f509dbd10", intent.getStringExtra("key")), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i("Trailer", "videos: " + results.toString());

                    for(int j = 0; j < results.length(); j++){
                        JSONObject n = results.getJSONObject(j);
                        key = n.getString("key");
                    }

                } catch (JSONException e) {
                    Log.e("Trailer", "Hit JSON exception", e);
                }

                // resolve the player view from the layout
                playerView = (YouTubePlayerView) findViewById(R.id.player);

                // initialize with API key stored in secrets.xml
                playerView.initialize("AIzaSyA_u3l_hYRJvbzsWZyEWo7m9plTrq68qS0", new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(key);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        // log the error
                        Log.e("ZZZ", "Error initializing YouTube player");
                    }
                });
                Log.d("Trailer", "success");
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("Trailer", "fail");
            }
        });

    }
}