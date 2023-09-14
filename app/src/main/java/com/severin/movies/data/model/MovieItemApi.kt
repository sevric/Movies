package com.severin.movies.data.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieItemApi(
    @SerializedName("adult")
    val adult: Boolean?,

    @SerializedName("backdrop_path")
    val backdrop_path: String?,

    @SerializedName("genre_ids")
    val genre_ids: List<Int>?,

    @SerializedName("genres")
    val genres: List<Genre>?,

    @PrimaryKey
    @SerializedName("id")
    val id: Int?,

    @SerializedName("original_language")
    val original_language: String?,

    @SerializedName("original_title")
    val original_title: String?,

    @SerializedName("overview")
    val overview: String?,

    @SerializedName("popularity")
    val popularity: Double?,

    @SerializedName("poster_path")
    val poster_path: String?,

    @SerializedName("release_date")
    val release_date: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("video")
    val video: Boolean?,

    @SerializedName("vote_average")
    val vote_average: Double?,

    @SerializedName("vote_count")
    val vote_count: Int?
) : java.io.Serializable {
    fun mapToDb(mapper: MovieApiToDbMapper): MovieItemDB {
        return mapper.mapFromApiToDb(this@MovieItemApi)
    }
}

