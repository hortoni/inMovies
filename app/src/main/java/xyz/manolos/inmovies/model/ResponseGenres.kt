package xyz.manolos.inmovies.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseGenres (
    val genres: List<Genre>

)
