package xyz.manolos.inmovies.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import javax.inject.Named

@JsonClass(generateAdapter = true)
@Named("results")
@Parcelize
@Entity(tableName = "movie")
data class Movie(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0,

    val title: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val genre_ids: List<Int>?,
    var genres: String?,
    val overview: String?,
    val release_date: String?
) : Parcelable

