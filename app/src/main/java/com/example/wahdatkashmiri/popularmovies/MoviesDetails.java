package com.example.wahdatkashmiri.popularmovies;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wahdatkashmiri.popularmovies.MoviesApi.MoviesApi;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesDetails extends AppCompatActivity {
    @BindView(R.id.rootLayout) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.collapsing) CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.img_poster) ImageView backdrop;
    @BindView(R.id.movie_title) TextView title;
    @BindView(R.id.movie_overview) TextView overview;
    @BindView(R.id.release_date) TextView releasedate;
    @BindView(R.id.movie_ratings) TextView ratings;
    @BindView(R.id.poster) ImageView circlebackdrop;
    @BindView(R.id.toolbar) Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        ButterKnife.bind(this);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        setSupportActionBar(toolbar);


//this line shows back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Intent intent=getIntent();
        String BASE_URL="http://image.tmdb.org/t/p/";
        String size= "w780/";
       String image= intent.getStringExtra("imageUrl");

        String imagename= intent.getStringExtra("imagename");
        String description=intent.getStringExtra("descr");
        String release=intent.getStringExtra("releasedate");
        String average=intent.getStringExtra("ratings");


        Picasso.get().load(BASE_URL+size+image).placeholder(R.drawable.imageload)
                .into(backdrop);

       overview.setText(description);
       title.setText(imagename);
        DateFormat inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = inputFormatter1.parse(release);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat outputFormatter1 = new SimpleDateFormat("dd-MMM-yyyy");
        String output1 = outputFormatter1.format(date1);
        releasedate.setText(output1);
       ratings.setText(average);
        Picasso.get().load(BASE_URL+size+image).into(circlebackdrop);

    }


}
