package com.example.wahdat.popularmovies2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.wahdat.popularmovies2.Adapter.Adapter;
import com.example.wahdat.popularmovies2.Model.Model;

import com.example.wahdat.popularmovies2.Model.Result;
import com.example.wahdat.popularmovies2.Model.Trailer;
import com.example.wahdat.popularmovies2.MoviesApi.MoviesApi;
import com.example.wahdat.popularmovies2.NetworkInfo.Network;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Adapter.OnItemClickListener{


   @BindView(R.id.recyclerview) RecyclerView recyclerView;
   @BindView(R.id.contentlayout) ConstraintLayout constraintLayout;
   private RecyclerView.LayoutManager layoutManager;
    private Adapter adapter;
    private Model moviesModal;

    private List<Result> movieResults;


    //
    String API_KEY="90787843a200cfbfd55b14b39270f6a1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        movieResults= new ArrayList<>();
        //Bindingview

        ButterKnife.bind(this);
        settinglayoutmanager();

      boolean isConnected=  Network.getConnectivityStatus(MainActivity.this);
      if (isConnected){
          loadPopularMovies();
      }
      else {

          checkNetworkConnection();
          Snackbar.make(constraintLayout,"Please Check Internet Connection",Snackbar.LENGTH_LONG).show();
      }



    }

    private void checkNetworkConnection() {
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection and then Refresh to continue");
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void settinglayoutmanager() {

        //int resId = R.anim.layout_animation_fall_down;
        //LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(), resId);
        int noOfColumns= 2;

        layoutManager=new GridLayoutManager(this,noOfColumns);
        recyclerView.setLayoutManager(layoutManager);
        LayoutAnimationController controller= AnimationUtils.loadLayoutAnimation(getApplicationContext(),R.anim.layout_animation_fall_down);
        recyclerView.setLayoutAnimation(controller);
        recyclerView.setHasFixedSize(true);

        //recyclerView.setLayoutAnimation(animation);
    }

    private void loadPopularMovies() {
       // Creating a retrofit object
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(MoviesApi.Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Creating the Api interface
     MoviesApi.Api api= retrofit.create(MoviesApi.Api.class);

        //now making the call object
        Call<Model> call= api.getPopularMovies(API_KEY);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
             moviesModal= response.body();
             movieResults=new ArrayList<>();
             movieResults=moviesModal.getResults();
             adapter=new Adapter(movieResults,getApplicationContext());
             adapter.setonItemClickListener(MainActivity.this);

             recyclerView.setAdapter(adapter);

             //animation

            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
             Log.d("MEssage",""+response);


            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.d("MEssage",""+t);
            }
        });



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public  boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_popular) {
            // Handle the camera action
            boolean isConnected=  Network.getConnectivityStatus(MainActivity.this);
            if (isConnected){
                loadPopularMovies();
            }
            else {
                checkNetworkConnection();
                Snackbar.make(constraintLayout,"Please Check Internet Connection",Snackbar.LENGTH_LONG).show();
            }
        } else if (id == R.id.nav_Top_rated) {

            boolean isConnected=  Network.getConnectivityStatus(MainActivity.this);
            if (isConnected){
                loadTopRatedMovies();
            }
            else {
                checkNetworkConnection();
                Snackbar.make(constraintLayout,"Please Check Internet Connection",Snackbar.LENGTH_LONG).show();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadTopRatedMovies() {
        // Creating a retrofit object
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(MoviesApi.Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Creating the Api interface
        MoviesApi.Api api= retrofit.create(MoviesApi.Api.class);

        //now making the call object
        Call<Model> call= api.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                moviesModal= response.body();
                movieResults=new ArrayList<>();
                movieResults=moviesModal.getResults();
                adapter=new Adapter(movieResults,getApplicationContext());
                adapter.setonItemClickListener(MainActivity.this);

                recyclerView.setAdapter(adapter);
                //animation

                recyclerView.getAdapter().notifyDataSetChanged();
                recyclerView.scheduleLayoutAnimation();
                Log.d("MEssage",""+response);


            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.d("MEssage",""+t);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if (id==R.id.refresh){
            boolean isConnected=  Network.getConnectivityStatus(MainActivity.this);
            if (isConnected){
                loadPopularMovies();
            }
            else {
                checkNetworkConnection();
                Snackbar.make(constraintLayout,"Please Check Internet Connection",Snackbar.LENGTH_LONG).show();
            }
        }
        return true;
    }



    @Override
    public void onListItemClick(int position) {

        Intent intent=new Intent(MainActivity.this,MoviesDetails.class);
        intent.putExtra("imageUrl",movieResults.get(position).getBackdropPath());
        intent.putExtra("imagename",movieResults.get(position).getTitle());
        intent.putExtra("descr",movieResults.get(position).getOverview());
        intent.putExtra("ratings",movieResults.get(position).getVoteAverage().toString());
        intent.putExtra("releasedate",movieResults.get(position).getReleaseDate());



        startActivity(intent);

    }
}
