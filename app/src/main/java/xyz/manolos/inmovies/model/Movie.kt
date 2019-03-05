package xyz.manolos.inmovies.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
@Entity(tableName = "movie")
data class Movie(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long,
    var title: String?,
    var poster_path: String?,
    var backdrop_path: String?,
    var overview: String?,
    var release_date: String?,
    @Ignore var genre_ids: List<Long>
) : Parcelable {
    constructor() : this(0, "", "", "", "", "", emptyList() )
}

