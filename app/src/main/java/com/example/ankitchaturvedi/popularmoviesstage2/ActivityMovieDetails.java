package com.example.ankitchaturvedi.popularmoviesstage2;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.ankitchaturvedi.popularmoviesstage2.Adapter.MovieTrailerAdapter;
import com.example.ankitchaturvedi.popularmoviesstage2.Adapter.RecyclerViewAdapter;
import com.example.ankitchaturvedi.popularmoviesstage2.Fragment.MovieTrailerFragment;
import com.example.ankitchaturvedi.popularmoviesstage2.Fragment.ReviewsFragment;
import com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser.MainGridResponseParser;
import com.example.ankitchaturvedi.popularmoviesstage2.ResponseParser.MovieTrailerResponseParser;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.Constant;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.ServiceCall;
import com.example.ankitchaturvedi.popularmoviesstage2.Utils.helper;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.entity.StringEntityHC4;

/**
 * Created by Ankit.Chaturvedi on 04-Apr-17.
 */

public class ActivityMovieDetails extends AppCompatActivity {
    TextView mtv_movie_title,mtv_release_date,mtv_overview,mtv_censor_rating,mtv_rating;
    ImageView miv_collapsing;
    RatingBar rating_bar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ProgressBar progressBar;
    FrameLayout mframe_layout;
    FloatingActionButton mfab_trailers,mfab_reviews;
    LinearLayout mll_movie_details;
    Fragment fragment;


    static String mposter_path,id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupWindowAnimations();

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_layout);
        mtv_movie_title = (TextView) findViewById(R.id.tv_movie_title);
        miv_collapsing = (ImageView) findViewById(R.id.iv_collapsing);
//        miv_back = (ImageView) findViewById(R.id.iv_back);
        mtv_overview = (TextView) findViewById(R.id.tv_overview);
        mtv_release_date = (TextView) findViewById(R.id.tv_release_date);
        mtv_censor_rating = (TextView) findViewById(R.id.tv_censor_rating);
        mtv_rating = (TextView) findViewById(R.id.tv_rating);
        rating_bar = (RatingBar) findViewById(R.id.rating_bar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        mframe_layout = (FrameLayout) findViewById(R.id.frame_layout);
        mfab_trailers = (FloatingActionButton) findViewById(R.id.fab_trailers);
        mfab_reviews = (FloatingActionButton) findViewById(R.id.fab_reviews);
        mll_movie_details = (LinearLayout) findViewById(R.id.ll_movie_details);


        mfab_trailers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceCall.id = id;
                fragment = new MovieTrailerFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        mfab_reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceCall.id = id;
                fragment = new ReviewsFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Intent intent = getIntent();
        mtv_movie_title.setText(String.format("Title: %s", intent.getStringExtra("title")));
        mposter_path=intent.getStringExtra("backdrop_path");
        mtv_overview.setText(String.format("Overview: %s", intent.getStringExtra("overview")));
        mtv_release_date.setText(String.format("Release Date: %s", intent.getStringExtra("release_date")));
        rating_bar.setRating(intent.getFloatExtra("vote_average",0));
        id= String.valueOf(intent.getIntExtra("id",0));
        rating_bar.setClickable(false);
        mtv_rating.setText(String.format("%s/10", rating_bar.getRating()));
        if(intent.getBooleanExtra("adult",false)){
            mtv_censor_rating.setVisibility(View.VISIBLE);
        }else mtv_censor_rating.setVisibility(View.GONE);
        setTitle(intent.getStringExtra("title"));

        Picasso.with(ActivityMovieDetails.this)
                .load(helper.base_url+helper.poster_sizesw184+mposter_path)
                .into(miv_collapsing);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id==R.id.close) {
           finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.slide);
            getWindow().setExitTransition(slide);
            getWindow().setEnterTransition(slide);
        }
    }


}
