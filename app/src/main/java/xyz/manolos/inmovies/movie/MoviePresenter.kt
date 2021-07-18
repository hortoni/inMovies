package xyz.manolos.inmovies.movie

import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import xyz.manolos.inmovies.database.GenreDao
import xyz.manolos.inmovies.database.MovieDao
import xyz.manolos.inmovies.database.MovieGenreDao
import xyz.manolos.inmovies.model.Genre
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.model.MovieGenre
import xyz.manolos.inmovies.model.ResponseMovies
import xyz.manolos.inmovies.service.MovieService
import javax.inject.Inject


class MoviePresenter @Inject constructor(
    private val view: MovieView,
    private val movieService: MovieService,
    private val movieDao: MovieDao,
    private val genreDao: GenreDao,
    private val movieGenreDao: MovieGenreDao
) {

    private val disposables = CompositeDisposable()


    fun fetchMovies(page: Int) {
        view.showLoading()
        movieService.fetchMovies(page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    saveMovies(it.results)
                    updatePageAndHideLoading(it)
                },
                onError = {
                    showErrorAndHideLoading()
                }
            )
            .addTo(disposables)
    }

    fun fetchGenresAndMovies(page: Int) {
        view.showLoading()
        movieService.fetchGenres()
            .flatMap {
                saveGenres(it.genres)
                movieService.fetchMovies(page)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    saveMovies(it.results)
                    updatePageAndHideLoading(it)
                },
                onError = {
                    showErrorAndHideLoading()
                }
            )
            .addTo(disposables)
    }

    fun observeMovies(): LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }

    private fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies)
            .subscribeOn(Schedulers.io())
            .subscribe()

        saveMoviesGenres(movies)
    }

    private fun saveGenres(genres: List<Genre>) {
        genreDao.insertGenres(genres)
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    private fun saveMoviesGenres(movies: List<Movie>) {
        movies.map {
            movieGenreDao.insertMoviesGenres(getMoviesGenresByMovie(it))
                .subscribeOn(Schedulers.io())
                .subscribe()
        }
    }

    private fun getMoviesGenresByMovie(movie: Movie): List<MovieGenre> {
        return movie.genreIds.map { MovieGenre(id_movie = movie.id, id_genre = it) }
    }

    private fun updatePageAndHideLoading(it: ResponseMovies) {
        view.updatePage(it)
        view.hideLoading()
    }

    private fun showErrorAndHideLoading() {
        view.showError()
        view.hideLoading()
    }

}