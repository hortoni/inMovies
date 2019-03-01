package xyz.manolos.inmovies.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import xyz.manolos.inmovies.model.AppDatabase


@Module
object RoomModule {

    @Provides
    fun providesAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "movie-db").allowMainThreadQueries().build()

    @Provides
    fun providesMovieDao(database: AppDatabase) = database.movieDao()
}