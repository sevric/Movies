package com.severin.movies.di

import android.app.Application
import com.severin.movies.data.MoviesRepositoryImpl
import com.severin.movies.data.database.MoviesRoomDao
import com.severin.movies.data.database.MoviesSavedRoomDB
import com.severin.movies.domain.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindMoviesRepository(impl: MoviesRepositoryImpl): MoviesRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideMoviesRoomDao(
            application: Application
        ): MoviesRoomDao {
            return MoviesSavedRoomDB.getInstance(application).roomDao()
        }
    }
}