package xyz.manolos.inmovies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "movie_genre",
    indices = [Index(value = ["id_movie", "id_genre"], unique = true)])
data class MovieGenre(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long? = null,

    var id_movie: Long? = null,
    var id_genre: Long? = null
)

