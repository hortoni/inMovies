package xyz.manolos.inmovies.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import xyz.manolos.inmovies.model.MovieGenre

@Dao
interface MovieGenreDao {

    @Query("select genre.name from movie_genre inner join genre on movie_genre.id_genre = genre.id where movie_genre.id_movie == :movie_id")
    fun findGenresNamesByMovie(movie_id: Long): LiveData<List<String>>

    @Insert(onConflict = REPLACE)
    fun insertMoviesGenres(moviesGenres: List<MovieGenre>): Completable
}