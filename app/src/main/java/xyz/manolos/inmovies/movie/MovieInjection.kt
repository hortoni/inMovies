package xyz.manolos.inmovies.movie

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [MovieModule::class])
interface MovieComponent {

    fun inject(activity: MovieActivity)
}

@Module
class MovieModule(private val movieView: MovieView) {

    @Provides
    fun provideMovieView() = movieView
}