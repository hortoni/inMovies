package xyz.manolos.inmovies.movie

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import xyz.manolos.inmovies.service.MovieService
import javax.inject.Inject

private const val API_KEY = "d71ff64de15d4ed68bd780ce30e5b24c"

class MoviePresenter @Inject constructor(
    private val view: MovieView,
    private val movieService: MovieService
) {

    private val disposables = CompositeDisposable()


    fun fetchMovies(page: Int) {
        view.showLoading()
        movieService.fetchMovies(API_KEY, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.showMovies(it)
                    view.updatePage(it)
                    view.hideLoading()
                },
                onError = {
                    view.showError(it)
                    view.hideLoading()
                }
            )
            .addTo(disposables)
    }

    fun fetchGenres() {
        movieService.fetchGenres(API_KEY)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view.getGenres(it)
                },
                onError = {
                    view.showError(it)
                }
            )
            .addTo(disposables)
    }
}