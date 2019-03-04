package xyz.manolos.inmovies.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import xyz.manolos.inmovies.model.MovieGenre

@Dao
interface MovieGenreDao {

    @Query("select * from movie_genre where id_movie == :movie_id")
    fun findGenresByMovieId(movie_id: Long): List<MovieGenre>

    @Insert(onConflict = REPLACE)
    fun insertMovieGenre(movieGenre: MovieGenre)

    @Insert(onConflict = REPLACE)
    fun insertMoviesGenres(moviesGenres: List<MovieGenre>)

    @Update(onConflict = REPLACE)
    fun updateMovieGenre(movieGenre: MovieGenre)

    @Delete
    fun deleteMovie(movieGenre: MovieGenre)
}