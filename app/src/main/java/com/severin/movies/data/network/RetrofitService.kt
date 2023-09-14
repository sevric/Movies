package com.severin.movies.data.network

import com.severin.movies.data.model.GenresResponse
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.data.model.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("search/multi")
    suspend fun getMoviesByUserSearchQuery(
        @Query("query") queryString: String
    ): MoviesResponse

    @GET("discover/movie")
    suspend fun getPopularMovies(

        @Query("page") page: Int,

        @Query("sort_by") sort_by: String,

        @Query("vote_count.gte") vote_count_gte: Int,

        @Query("vote_average.gte") vote_average_gte: Int
    ): MoviesResponse

    @GET("discover/movie")
    suspend fun getNowInTheTheatresMovies(
        @Query("primary_release_date.gte") primary_release_date_gte: String
    ): MoviesResponse

    @GET("discover/movie")
    suspend fun getPopularOfPeriod(
        @Query("primary_release_date.gte") time_period: String,
        @Query("sort_by") sort_by: String,
        @Query("vote_average.gte") vote_average_gte: Int
    ): MoviesResponse

    @GET("discover/movie")
    suspend fun getMoviesForKids(
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String,
        @Query("vote_average.gte") vote_average_gte: Int,
        @Query("certification.lte") certification_lte: String,
        @Query("with_genres") with_genres: Int
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id", encoded = true) movieId: Int?
    ): MovieItemApi

    @GET("genre/movie/list")
    suspend fun getGenresList(): GenresResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenreId(
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String,
        @Query("with_genres") genreId: String?
    ): MoviesResponse

}