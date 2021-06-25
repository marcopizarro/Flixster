package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.module.AppGlideModule;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

//import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailsActivity extends AppCompatActivity {

    TextView tvDetailTitle;
    TextView tvDetailDescription;
    ImageView ivDetailPoster;
    RatingBar rbDetailRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent intent = getIntent();

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailDescription = findViewById(R.id.tvDetailDescription);
        ivDetailPoster = findViewById(R.id.ivDetailPoster);
        rbDetailRating = findViewById(R.id.rbDetailRating);
        float rating = (float) intent.getDoubleExtra("rating", 0);

        tvDetailTitle.setText(intent.getStringExtra("title"));
        tvDetailDescription.setText(intent.getStringExtra("description"));
        Glide.with(this)
                .load(intent.getStringExtra("poster"))
                .placeholder(R.drawable.flicks_movie_placeholder)
                .fitCenter()
                .transform(new RoundedCornersTransformation(20, 0))
                .into(ivDetailPoster);
        rbDetailRating.setRating(rating);
    }
}