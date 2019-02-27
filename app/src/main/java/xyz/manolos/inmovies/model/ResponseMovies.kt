package xyz.manolos.inmovies.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseMovies (
    val results: List<Movie>,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
)
