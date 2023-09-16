package com.severin.movies.presentation.adapters

import com.severin.movies.data.model.MovieItemDB

interface MovieFromDBAdapterClickListener {
    fun onClick(movieItemDB: MovieItemDB)

    fun onLongClick(movieItemDB: MovieItemDB)
}