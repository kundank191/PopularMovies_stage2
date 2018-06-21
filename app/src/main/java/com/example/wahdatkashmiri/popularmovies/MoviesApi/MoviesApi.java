package com.example.wahdatkashmiri.popularmovies.MoviesApi;

import com.example.wahdatkashmiri.popularmovies.Model.Model;
import com.example.wahdatkashmiri.popularmovies.Model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class MoviesApi {




    public interface Api{

        String BASE_URL="http://api.themoviedb.org/3/movie/";

        @GET("popular")
        Call<Model> getPopularMovies(@Query("api_key") String KEY);

        @GET("top_rated")
        Call<Model> getTopRatedMovies(@Query("api_key") String KEY);
    }
}
