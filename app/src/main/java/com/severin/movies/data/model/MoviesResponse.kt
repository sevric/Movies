package com.severin.movies.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("results")
    val results: List<MovieItemApi>
)