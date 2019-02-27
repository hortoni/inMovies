package xyz.manolos.inmovies.model

import com.squareup.moshi.JsonClass
import javax.inject.Named

@JsonClass(generateAdapter = true)
@Named("results")
data class Movie (
    val title: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val overview: String?,
    val release_date: String?
)
