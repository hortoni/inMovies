package xyz.manolos.inmovies.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import xyz.manolos.inmovies.model.Genre

@Dao
interface GenreDao {

    @Query("select * from genre")
    fun getAllGenres(): LiveData<List<Genre>>

    @Query("select * from genre where id == :id")
    fun findGenreById(id: Long): Genre

    @Insert(onConflict = REPLACE)
    fun insertGenre(genre: Genre)

    @Insert(onConflict = REPLACE)
    fun insertGenres(genres: List<Genre>)

    @Update(onConflict = REPLACE)
    fun updateGenre(genre: Genre)

    @Delete
    fun deleteGenre(genre: Genre)
}