package xyz.manolos.inmovies.movie

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import xyz.manolos.inmovies.dao.GenreDao
import xyz.manolos.inmovies.dao.MovieDao
import xyz.manolos.inmovies.dao.MovieGenreDao
import xyz.manolos.inmovies.model.Genre
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.model.MovieGenre
import xyz.manolos.inmovies.service.MovieService
import javax.inject.Inject

private const val API_KEY = "d71ff64de15d4ed68bd780ce30e5b24c"

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
        movieService.fetchMovies(API_KEY, page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    saveMovies(it.results)
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

    fun fetchGenresAndMovies(page: Int) {
        view.showLoading()
        movieService.fetchGenres(API_KEY)
            .flatMap {
                saveGenres(it.genres)
                movieService.fetchMovies(API_KEY, page)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    saveMovies(it.results)
//                    saveMoviesGenres(it.results)
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

    private fun saveMovies(movies: List<Movie>) {
        movieDao.insertMovies(movies)
        saveMoviesGenres(movies)
    }

    private fun saveMoviesGenres(movies: List<Movie>) {
        movies.forEach {
            movieGenreDao.insertMoviesGenres(getMoviesGenresByMovie(it))
        }
    }

    private fun getMoviesGenresByMovie(movie: Movie) : List<MovieGenre>{
        var list = ArrayList<MovieGenre>()
        movie.genre_ids?.forEach {
            Log.e("DEBUG", movie.id.toString())
            Log.e("DEBUG", it.toString())
            list.add(MovieGenre(null, movie.id, it))
        }
        return list
    }

    private fun saveGenres (genres: List<Genre>) {
        genreDao.insertGenres(genres)

    }

    fun observeMovies() : LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }
}