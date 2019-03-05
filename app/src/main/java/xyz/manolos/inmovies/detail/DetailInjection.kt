package xyz.manolos.inmovies.detail

import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [DetailModule::class])
interface DetailComponent {

    fun inject(activity: DetailActivity)
}

@Module
class DetailModule(private val detailView: DetailView) {

    @Provides
    fun provideDetaukView() = detailView
}