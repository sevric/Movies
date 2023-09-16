package com.severin.movies.presentation.adapters

import com.severin.movies.data.model.MovieItemApi

interface MovieFromApiAdapterClickListener {
    fun onClick(movieItemApi: MovieItemApi)

    fun onLongClick(movieItemApi: MovieItemApi)
}