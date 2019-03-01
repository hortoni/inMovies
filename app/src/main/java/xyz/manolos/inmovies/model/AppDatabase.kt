package xyz.manolos.inmovies.model

import androidx.room.Database
import androidx.room.RoomDatabase
import javax.inject.Singleton

@Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
@Singleton
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}