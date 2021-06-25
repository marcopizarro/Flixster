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

    TextView tvDetailTitle;
    TextView tvDetailDescription;
    ImageView ivDetailPoster;
    RatingBar rbDetailRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ActivityDetailsBinding binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();

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
    }
}