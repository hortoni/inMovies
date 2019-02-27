package xyz.manolos.inmovies.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import xyz.manolos.inmovies.movie.MovieComponent
import xyz.manolos.inmovies.movie.MovieModule
import javax.inject.Singleton

@Component(modules = [ServiceModule::class])
@Singleton
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Context): Builder
        fun build(): ApplicationComponent
    }

    fun plusMovie(movieModule: MovieModule): MovieComponent

}