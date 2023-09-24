package com.severin.movies.presentation

import android.app.Application
import com.severin.movies.di.DaggerApplicationComponent

class MovieApplicationGlobal : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MovieApplicationGlobal
            private set
    }
}