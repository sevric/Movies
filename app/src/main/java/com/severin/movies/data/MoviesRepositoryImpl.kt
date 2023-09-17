package com.severin.movies.data

import com.severin.movies.data.model.*
import com.severin.movies.data.network.RetrofitInstance
import com.severin.movies.data.network.RetrofitService
import com.severin.movies.data.database.MoviesRoomDao
import com.severin.movies.domain.MoviesRepository

class MoviesRepositoryImpl constructor(
    private val moviesRoomDao: MoviesRoomDao,
    private val movieApiToDbMapper: MovieApiToDbMapper
) : MoviesRepository {
    private val retrofitService: RetrofitService = RetrofitInstance.retrofitService

    override suspend fun getMoviesByUserSearchQuery(queryString: String): MoviesResponse =
        retrofitService.getMoviesByUserSearchQuery(queryString)

    override suspend fun getPopularMoviesFromApi(
        page: Int,
        sort_by: String,
        vote_count_gte: Int,
        vote_average_gte: Int
    ): MoviesResponse =
        retrofitService.getPopularMovies(
            page,
            sort_by,
            vote_count_gte,
            vote_average_gte
        )

    override suspend fun getNowInTheTheatersMoviesFromApi(
        date: String
    ): MoviesResponse =
        retrofitService.getNowInTheTheatresMovies(date)

    override suspend fun getPopularMoviesByPeriod(
        period: String,
        sort_by: String,
        vote_average_gte: Int
    ): MoviesResponse =
        retrofitService.getPopularOfPeriod(
            period,
            sort_by,
            vote_average_gte
        )

    override suspend fun getMoviesForKidsFromApi(
        page: Int,
        sort_by: String,
        vote_average_gte: Int,
        certification_lte: String,
        with_genres: Int
    ): MoviesResponse =
        retrofitService.getMoviesForKids(
            page,
            sort_by,
            vote_average_gte,
            certification_lte,
            with_genres
        )

    override suspend fun getMovieByIdFromApi(
        movieId: Int?
    ): MovieItemApi =
        retrofitService.getMovieById(movieId)

    override suspend fun getGenresListFromApi(): GenresResponse =
        retrofitService.getGenresList()

    override suspend fun getMoviesByGenreIdFromApi(
        page: Int,
        sort_by: String,
        genreId: Int?
    ): MoviesResponse =
        retrofitService.getMoviesByGenreId(
            page,
            sort_by,
            genreId.toString()
        )

    override suspend fun getAllFavouritesFromDB() =
        moviesRoomDao.getAllFavouritesFromDB()

    override suspend fun getAllWatchLaterFromDB() =
        moviesRoomDao.getAllWatchLaterFromDB()

    override suspend fun insertFavouriteIntoDB(
        movieItemApi: MovieItemApi
    ): MovieItemDB {
        val movieItemFavouriteDB =
            mapFromApiToDb(movieItemApi)
        moviesRoomDao.insertFavourite(movieItemFavouriteDB)
        moviesRoomDao.updateFavourite(movieItemFavouriteDB.movieDbId, true)
        return movieItemFavouriteDB
    }

    override suspend fun insertWatchLaterIntoDB(
        movieItemApi: MovieItemApi
    ): MovieItemDB {
        val movieItemFavouriteDB =
            mapFromApiToDb(movieItemApi)
        moviesRoomDao.insertWatchLater(movieItemFavouriteDB)
        moviesRoomDao.updateWatchLater(movieItemFavouriteDB.movieDbId, true)
        return movieItemFavouriteDB
    }

    override suspend fun updateFavourite(movieId: Int, isFavourite: Boolean) {
        moviesRoomDao.updateFavourite(movieId, isFavourite)
    }

    override suspend fun updateWatchLater(movieId: Int, shouldWatchLater: Boolean) {
        moviesRoomDao.updateWatchLater(movieId, shouldWatchLater)
    }

    override fun removeMovieFromDBById(
        movieItemApi: MovieItemApi
    ): MovieItemDB {
        val movieItemFavouriteDB =
            mapFromApiToDb(movieItemApi)
        moviesRoomDao.deleteMovieById(movieItemFavouriteDB.movieDbId)
        return movieItemFavouriteDB
    }

    override fun getMovieByIdFromDB(movieId: Int?): MovieItemDB? =
        moviesRoomDao.getMovieById(movieId)

    override fun getFavouriteByIdFromDB(movieId: Int?): MovieItemDB? =
        moviesRoomDao.getFavouriteById(movieId)

    override fun getWatchLaterByIdFromDB(movieId: Int?): MovieItemDB? =
        moviesRoomDao.getWatchLaterById(movieId)

    private fun mapFromApiToDb(movieItemApi: MovieItemApi) =
        movieItemApi.mapToDb(movieApiToDbMapper)
}

