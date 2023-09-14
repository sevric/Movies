package com.severin.movies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "saved_movies")
data class MovieItemDB(
    @SerializedName("backdrop_path")
    val backdrop_path: String,

    @SerializedName("genre_ids")
    val genre_ids: List<Int>,

    @SerializedName("genres")
    val genres: List<Genre>,

    @PrimaryKey
    @SerializedName("id")
    val movieDbId: Int,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val poster_path: String,

    @SerializedName("release_date")
    val release_date: String,//release_date="1999-10-15"

    @SerializedName("title")
    val title: String,

    @SerializedName("vote_average")
    val vote_average: Double,

    @SerializedName("isFavourite")
    val isFavourite: Boolean,

    @SerializedName("shouldWatchLater")
    val shouldWatchLater: Boolean
) : java.io.Serializable