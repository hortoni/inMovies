package xyz.manolos.inmovies.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import javax.inject.Named

@JsonClass(generateAdapter = true)
@Named("results")
@Parcelize
data class Movie(

    val title: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    val overview: String?,
    val release_date: String?
) : Parcelable

