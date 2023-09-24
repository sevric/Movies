package com.severin.movies.di

import androidx.lifecycle.ViewModel
import com.severin.movies.presentation.vm.*
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesByUserSearchQueryViewModel::class)
    fun bindMoviesByUserSearchQueryViewModel(viewModel: MoviesByUserSearchQueryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteMoviesViewModel::class)
    fun bindFavouriteMoviesViewModel(viewModel: FavouriteMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GenresViewModel::class)
    fun bindGenresViewModel(viewModel: GenresViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HighlightedByPeriodViewModel::class)
    fun bindHighlightedByPeriodViewModel(viewModel: HighlightedByPeriodViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MostPopularMoviesViewModel::class)
    fun bindMostPopularMoviesViewModel(viewModel: MostPopularMoviesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoviesForChildrenViewModel::class)
    fun bindMoviesForChildrenViewModel(viewModel: MoviesForChildrenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MovieByIdFromApiViewModel::class)
    fun bindMovieByIdViewModel(viewModel: MovieByIdFromApiViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoviesByGenreViewModel::class)
    fun bindMoviesByGenreViewModel(viewModel: MoviesByGenreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NowInTheTheatersViewModel::class)
    fun bindNowInTheTheatersViewModel(viewModel: NowInTheTheatersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WatchLaterMoviesViewModel::class)
    fun bindWatchLaterMoviesViewModel(viewModel: WatchLaterMoviesViewModel): ViewModel


//TODO(remove these commented lines if not needed)
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(FirebaseAuthorizationViewModel::class)
//    fun bindLoginViewModel(viewModel: FirebaseAuthorizationViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(RegistrationViewModel::class)
//    fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel
//
//    @Binds
//    @IntoMap
//    @ViewModelKey(ResetPasswordViewModel::class)
//    fun bindResetPasswordViewModel(viewModel: ResetPasswordViewModel): ViewModel
}