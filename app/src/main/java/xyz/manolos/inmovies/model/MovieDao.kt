package xyz.manolos.inmovies.model

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE

@Dao
interface MovieDao {

    @Query("select * from movie")
    fun getAllMovies(): List<Movie>

    @Query("select * from movie where id == :id")
    fun findMovieById(id: Long): Movie

    @Insert(onConflict = REPLACE)
    fun insertMovie(movie: Movie)

    @Update(onConflict = REPLACE)
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)
}