package xyz.manolos.inmovies.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.manolos.inmovies.model.Genre
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.model.MovieGenre

@Database(entities = [Movie::class, Genre::class, MovieGenre::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun movieGenreDao(): MovieGenreDao
}