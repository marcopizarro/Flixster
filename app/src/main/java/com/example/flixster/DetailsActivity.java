package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster.databinding.ActivityDetailsBinding;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class DetailsActivity extends AppCompatActivity {

    public static final String TAG = "DetailsActivity";

    TextView tvDetailTitle;
    TextView tvDetailDescription;
    ImageView ivDetailPoster;
    RatingBar rbDetailRating;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        intent = getIntent();
        View view = binding.getRoot();
        setContentView(view);

        float rating = (float) intent.getDoubleExtra("rating", 0);
        binding.tvDetailTitle.setText(intent.getStringExtra("title"));
        binding.tvDetailDescription.setText(intent.getStringExtra("description"));
        Glide.with(this)
                .load(intent.getStringExtra("poster"))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .fitCenter()
                .transform(new RoundedCornersTransformation(20, 0))
                .into(binding.ivDetailPoster);
        binding.rbDetailRating.setRating(rating);

        binding.ivDetailPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(DetailsActivity.this, MovieTrailerActivity.class);
                int key = intent.getIntExtra("key", 0);
                in.putExtra("key", String.valueOf(key));
                DetailsActivity.this.startActivity(in);
            }
        });
    }
}