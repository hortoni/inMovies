package xyz.manolos.inmovies.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import xyz.manolos.inmovies.model.Movie

@Dao
interface MovieDao {

    @Query("select * from movie")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("select * from movie where id == :id")
    fun findMovieById(id: Long): Movie

    @Insert(onConflict = REPLACE)
    fun insertMovie(movie: Movie)

    @Insert(onConflict = REPLACE)
    fun insertMovies(movies: List<Movie>)

    @Update(onConflict = REPLACE)
    fun updateMovie(movie: Movie)

    @Delete
    fun deleteMovie(movie: Movie)
}