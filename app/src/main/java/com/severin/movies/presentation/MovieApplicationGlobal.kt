package com.severin.movies.presentation

import android.app.Application

class MovieApplicationGlobal : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MovieApplicationGlobal
            private set
    }
}