package xyz.manolos.inmovies.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize
import javax.inject.Named

@JsonClass(generateAdapter = true)
@Named("results")
@Parcelize
@Entity(tableName = "movie")
data class Movie(

    @ColumnInfo(name = "app_id")
    @PrimaryKey(autoGenerate = true) var app_id: Long = 0,

    val id: Int?,
    val title: String?,
    val poster_path: String?,
    val backdrop_path: String?,
//    val genre_ids: List<Int>?,
//    val genres: String?,
    val overview: String?,
    val release_date: String?
) : Parcelable

