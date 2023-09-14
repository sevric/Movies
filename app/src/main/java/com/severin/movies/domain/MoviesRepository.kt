package com.severin.movies.domain

import com.severin.movies.data.model.*

interface MoviesRepository {

    suspend fun getMoviesByUserSearchQuery(
        queryString: String
    ): MoviesResponse

    suspend fun getPopularMoviesFromApi(
        page: Int,
        sort_by: String,
        vote_count_gte: Int,
        vote_average_gte: Int
    ): MoviesResponse

    suspend fun getNowInTheTheatersMoviesFromApi(
        date: String
    ): MoviesResponse

    suspend fun getPopularMoviesByPeriod(
        period: String,
        sort_by: String,
        vote_average_gte: Int
    ): MoviesResponse

    suspend fun getMoviesForKidsFromApi(
        page: Int,
        sort_by: String,
        vote_average_gte: Int,
        certification_lte: String,
        with_genres: Int
    ): MoviesResponse

    suspend fun getMovieByIdFromApi(
        movieId: Int?
    ): MovieItemApi

    suspend fun getGenresListFromApi(): GenresResponse

    suspend fun getMoviesByGenreIdFromApi(
        page: Int,
        sort_by: String,
        genreId: Int?
    ): MoviesResponse

    suspend fun getAllFavouritesFromDB(): List<MovieItemDB>

    suspend fun getAllWatchLaterFromDB(): List<MovieItemDB>

    suspend fun insertFavouriteIntoDB(
        movieItemApi: MovieItemApi
    ): MovieItemDB

    suspend fun insertWatchLaterIntoDB(
        movieItemApi: MovieItemApi
    ): MovieItemDB

    suspend fun updateFavourite(movieId: Int, isFavourite: Boolean)

    suspend fun updateWatchLater(movieId: Int, shouldWatchLater: Boolean)

    fun removeMovieFromDBById(
        movieItemApi: MovieItemApi
    ): MovieItemDB

    fun getMovieByIdFromDB(movieId: Int?): MovieItemDB?

    fun getFavouriteByIdFromDB(movieId: Int?): MovieItemDB?

    fun getWatchLaterByIdFromDB(movieId: Int?): MovieItemDB?
}