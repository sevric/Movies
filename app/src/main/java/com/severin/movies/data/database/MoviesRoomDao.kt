package com.severin.movies.data.database

import androidx.room.*
import com.severin.movies.data.model.MovieItemDB

@Dao
interface MoviesRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(movieItemFavouriteDB: MovieItemDB)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchLater(movieItemFavouriteDB: MovieItemDB)

    @Query("UPDATE saved_movies SET isFavourite = :isFavourite " +
            "WHERE movieDbId = :movieId")
    suspend fun updateFavourite(movieId: Int, isFavourite: Boolean)

    @Query("UPDATE saved_movies SET shouldWatchLater = :shouldWatchLater " +
            "WHERE movieDbId = :movieId")
    suspend fun updateWatchLater(movieId: Int, shouldWatchLater: Boolean)

    @Query("DELETE FROM saved_movies " +
            "WHERE movieDbId = :movieId")
    fun deleteMovieById(movieId: Int)

    @Query("SELECT * FROM saved_movies " +
            "WHERE movieDbId = :movieId")
    fun getMovieById(movieId: Int?): MovieItemDB?

    @Query("SELECT * FROM saved_movies " +
            "WHERE movieDbId = :movieId AND isFavourite = 1")
    fun getFavouriteById(movieId: Int?): MovieItemDB?

    @Query("SELECT * FROM saved_movies " +
            "WHERE movieDbId = :movieId AND shouldWatchLater = 1")
    fun getWatchLaterById(movieId: Int?): MovieItemDB?

    @Query("SELECT * FROM saved_movies " +
            "WHERE isFavourite = 1")
    fun getAllFavouritesFromDB(): List<MovieItemDB>

    @Query("SELECT * FROM saved_movies " +
            "WHERE shouldWatchLater = 1")
    fun getAllWatchLaterFromDB(): List<MovieItemDB>

}