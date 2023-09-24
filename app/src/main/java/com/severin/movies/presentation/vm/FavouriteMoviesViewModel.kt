package com.severin.movies.presentation.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.severin.movies.data.model.MovieItemApi
import com.severin.movies.data.model.MovieItemDB
import com.severin.movies.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteMoviesViewModel @Inject constructor(
    private val getAllFavouritesFromDBUseCase: GetAllFavouritesFromDBUseCase,
    private val getFavouriteByIdFromDBUseCase: GetFavouriteByIdFromDBUseCase,
    private val getMovieByIdFromDBUseCase: GetMovieByIdFromDBUseCase,
    private val insertFavouriteIntoDBUseCase: InsertFavouriteIntoDBUseCase,
    private val updateFavouriteUseCase: UpdateFavouriteUseCase,
    private val removeMovieFromDBByIdUseCase: RemoveMovieFromDBByIdUseCase
) : ViewModel() {
    private val _movieFavouriteById = MutableLiveData<MovieItemDB?>()
    val movieFavouriteById: LiveData<MovieItemDB?>
        get() = _movieFavouriteById

    private val _allFavouriteMovies = MutableLiveData<List<MovieItemDB?>>()
    val allFavouriteMovies: LiveData<List<MovieItemDB?>>
        get() = _allFavouriteMovies

    fun getAllFavouriteMovies() = viewModelScope.launch(Dispatchers.IO) {
        _allFavouriteMovies.postValue(
            getAllFavouritesFromDBUseCase.invoke()
        )
    }

    fun getFavouriteMovieById(movieId: Int?) = viewModelScope.launch(Dispatchers.IO) {
        _movieFavouriteById.postValue(
            getFavouriteByIdFromDBUseCase.invoke(movieId)
        )
    }

    fun toggleFavourite(movieItemApi: MovieItemApi) = viewModelScope.launch(Dispatchers.IO) {
        val movieItemFromDB = getMovieByIdFromDBUseCase.invoke(movieItemApi.id)
        if (movieItemFromDB == null) {
            insertFavouriteIntoDBUseCase.invoke(movieItemApi)
        } else {
            updateFavouriteUseCase.invoke(movieItemFromDB.movieDbId, !movieItemFromDB.isFavourite)
            val movieItemFromDBUpdated = getMovieByIdFromDBUseCase.invoke(movieItemApi.id)
            if (
                (movieItemFromDBUpdated != null) &&
                !movieItemFromDBUpdated.isFavourite &&
                !movieItemFromDBUpdated.shouldWatchLater
            ) {
                removeMovieFromDBByIdUseCase.invoke(movieItemApi)
            }
        }
        _movieFavouriteById.postValue(
            getFavouriteByIdFromDBUseCase.invoke(movieItemApi.id)
        )
        _allFavouriteMovies.postValue(
            getAllFavouritesFromDBUseCase.invoke()
        )
    }
}

