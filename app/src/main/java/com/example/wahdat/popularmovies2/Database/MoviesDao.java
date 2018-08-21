package com.example.wahdat.popularmovies2.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.wahdat.popularmovies2.Model.Model;

import java.util.List;

@Dao
public interface MoviesDao {

    @Query("SELECT * FROM favourite_movies")
    List<FavouritesModal> getAllMovies();

    @Insert
    void insertFavMovie(FavouritesModal favouritesModal);

   // @Query("DELETE  FROM favourite_movies WHERE id = :id")
   @Query("DELETE FROM favourite_movies WHERE movie_id = :movieid")
    void deleteFavMovie(String movieid);
}
