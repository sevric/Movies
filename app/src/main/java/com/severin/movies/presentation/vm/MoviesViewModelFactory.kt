package com.severin.movies.presentation.vm

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.severin.movies.data.MoviesRepositoryImpl
import com.severin.movies.data.database.MoviesSavedRoomDB
import com.severin.movies.data.model.MovieApiToDbMapper
import com.severin.movies.domain.*

class MoviesViewModelFactory(
    application: Application
) : ViewModelProvider.Factory {
    private val moviesRoomDao = MoviesSavedRoomDB.getInstance(application).roomDao()
    private val movieApiToDbMapper = MovieApiToDbMapper()
    private val repository = MoviesRepositoryImpl(moviesRoomDao, movieApiToDbMapper)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MostPopularMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            MostPopularMoviesViewModel(GetPopularMoviesFromApiUseCase(repository)) as T
        } else if (modelClass.isAssignableFrom(NowInTheTheatersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            NowInTheTheatersViewModel(GetNowInTheTheatersMoviesFromApiUseCase(repository)) as T
        } else if (modelClass.isAssignableFrom(HighlightedByPeriodViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            HighlightedByPeriodViewModel(GetPopularMoviesByPeriodUseCase(repository)) as T
        } else if (modelClass.isAssignableFrom(MoviesForChildrenViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            MoviesForChildrenViewModel(GetMoviesForKidsFromApiUseCase(repository)) as T
        } else if (modelClass.isAssignableFrom(GenresViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            GenresViewModel(GetGenresListFromApiUseCase(repository)) as T
        } else if (modelClass.isAssignableFrom(FavouriteMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            FavouriteMoviesViewModel(
                GetAllFavouritesFromDBUseCase(repository),
                GetFavouriteByIdFromDBUseCase(repository),
                GetMovieByIdFromDBUseCase(repository),
                InsertFavouriteIntoDBUseCase(repository),
                UpdateFavouriteUseCase(repository),
                RemoveMovieFromDBByIdUseCase(repository)
            ) as T
        } else if (modelClass.isAssignableFrom(MovieByIdFromApiViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            MovieByIdFromApiViewModel(GetMovieByIdFromApiUseCase(repository)) as T
        } else if (modelClass.isAssignableFrom(WatchLaterMoviesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            WatchLaterMoviesViewModel(
                GetWatchLaterByIdFromDBUseCase(repository),
                GetMovieByIdFromDBUseCase(repository),
                InsertWatchLaterIntoDBUseCase(repository),
                UpdateWatchLaterUseCase(repository),
                RemoveMovieFromDBByIdUseCase(repository)
            ) as T
        } else if (modelClass.isAssignableFrom(MoviesByUserSearchQueryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            MoviesByUserSearchQueryViewModel(GetMoviesByUserSearchQueryUseCase(repository)) as T
        } else if (modelClass.isAssignableFrom(MoviesByGenreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            MoviesByGenreViewModel(GetMoviesByGenreIdFromApiUseCase(repository)) as T
        } else {
            super.create(modelClass)
        }
    }
}