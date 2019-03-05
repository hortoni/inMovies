package xyz.manolos.inmovies.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import xyz.manolos.inmovies.dao.AppDatabase
import javax.inject.Singleton


@Module
object RoomModule {

    @Provides
    @JvmStatic
    @Singleton
    fun providesAppDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "inmovies-db").build()

    @Provides
    @JvmStatic
    @Singleton
    fun providesMovieDao(database: AppDatabase) = database.movieDao()

    @Provides
    @JvmStatic
    @Singleton
    fun providesGenreDao(database: AppDatabase) = database.genreDao()

    @Provides
    @JvmStatic
    @Singleton
    fun providesMovieGenreDao(database: AppDatabase) = database.movieGenreDao()
}