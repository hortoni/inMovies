package xyz.manolos.inmovies.di

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import xyz.manolos.inmovies.model.AppDatabase


@Module
class RoomModule(private val context: Context) {

    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "movie-db").allowMainThreadQueries().build()

    @Provides
    fun providesMovieDao(database: AppDatabase) = database.movieDao()
}