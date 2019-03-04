package xyz.manolos.inmovies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "genre")
data class Genre(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val name: String?
)

