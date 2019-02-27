package xyz.manolos.inmovies.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Genre(
    val id: String?,
    val name: String?
)

