package xyz.manolos.inmovies.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import xyz.manolos.inmovies.model.MovieGenre

@Dao
interface MovieGenreDao {

    @Query("select * from movie_genre")
    fun getAllGenres(): List<MovieGenre>

    @Query("select * from movie_genre where id_movie == :movie_id")
    fun findGenresByMovieId(movie_id: Long): List<MovieGenre>

    @Query("select genre.name from movie_genre inner join genre on movie_genre.id_genre = genre.id where movie_genre.id_movie == :movie_id")
    fun findGenresNamesByMovie(movie_id: Long): LiveData<List<String>>

    @Insert(onConflict = REPLACE)
    fun insertMovieGenre(movieGenre: MovieGenre)

    @Insert(onConflict = REPLACE)
    fun insertMoviesGenres(moviesGenres: List<MovieGenre>)

    @Update(onConflict = REPLACE)
    fun updateMovieGenre(movieGenre: MovieGenre)

    @Delete
    fun deleteMovie(movieGenre: MovieGenre)
}