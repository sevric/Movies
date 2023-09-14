package com.severin.movies.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.severin.movies.data.model.MovieItemDB

@Database(entities = [MovieItemDB::class], version = 1, exportSchema = false)
@TypeConverters(MovieTypeConverter::class)
abstract class MoviesSavedRoomDB : RoomDatabase() {
    abstract fun roomDao(): MoviesRoomDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesSavedRoomDB? = null
        private const val DATABASE_FILE_NAME = "movies.db"

        @Synchronized
        fun getInstance(application: Application): MoviesSavedRoomDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    application,
                    MoviesSavedRoomDB::class.java,
                    DATABASE_FILE_NAME
                ).fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE as MoviesSavedRoomDB
        }
    }
}