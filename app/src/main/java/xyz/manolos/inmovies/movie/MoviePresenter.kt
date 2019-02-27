package xyz.manolos.inmovies.movie

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import xyz.manolos.inmovies.service.MovieService
import javax.inject.Inject

class MoviePresenter @Inject constructor(
    private val view: MovieView,
    private val movieService: MovieService
) {

    private val disposables = CompositeDisposable()


    fun fetchMovies() {
        view.showLoading()
        movieService.fetch("d71ff64de15d4ed68bd780ce30e5b24c", 1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.showMovies(it)
                    view.hideLoading()
                },
                onError = {
                    view.showError(it)
                    view.hideLoading()
                }
            )
            .addTo(disposables)
    }
}