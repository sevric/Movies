package com.severin.movies.presentation.adapters

import com.severin.movies.data.model.Genre

interface GenresAdapterClickListener {
    fun onClick(genre: Genre)
}