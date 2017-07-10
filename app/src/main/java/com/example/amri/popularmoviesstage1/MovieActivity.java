package com.example.amri.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by amri on 7/10/17.
 */

public class MovieActivity extends AppCompatActivity {

    public TextView descText;
    public TextView titleText;
    public TextView dateText;
    public TextView ratingText;
    public ImageView movieposterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        descText=(TextView) findViewById(R.id.descText);
        titleText=(TextView)findViewById(R.id.titleText);
        dateText=(TextView)findViewById(R.id.dateText);
        ratingText=(TextView)findViewById(R.id.ratingText);
        movieposterImageView=(ImageView)findViewById(R.id.movieposterImage);


        Intent movieIntent=getIntent();
        int position=  movieIntent.getIntExtra("position",0);

        descText.setText(MainActivity.movieLists.get(position).overview);
        titleText.setText(MainActivity.movieLists.get(position).title);
        ratingText.setText(MainActivity.movieLists.get(position).vote_average);
        dateText.setText(MainActivity.movieLists.get(position).release_date);
        Picasso.with(getApplicationContext()).load(MainActivity.images.get(position)).into(movieposterImageView);

    }

}
