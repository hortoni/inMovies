package xyz.manolos.inmovies.movie

import androidx.lifecycle.LiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import xyz.manolos.inmovies.dao.GenreDao
import xyz.manolos.inmovies.dao.MovieDao
import xyz.manolos.inmovies.dao.MovieGenreDao
import xyz.manolos.inmovies.model.Genre
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.model.MovieGenre
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
                    view.updatePage(it)
                    view.hideLoading()
                },
                onError = {
                    view.showError()
                    view.hideLoading()
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
                    view.updatePage(it)
                    view.hideLoading()
                },
                onError = {
                    view.showError()
                    view.hideLoading()
                }
            )
            .addTo(disposables)
    }

    private fun saveMovies(movies: List<Movie>) {
        Single.fromCallable { movieDao.insertMovies(movies) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

        saveMoviesGenres(movies)

    }

    private fun saveMoviesGenres(movies: List<Movie>) {
        movies.forEach {
            Single.fromCallable { movieGenreDao.insertMoviesGenres(getMoviesGenresByMovie(it)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()

        }
    }

    private fun getMoviesGenresByMovie(movie: Movie) : List<MovieGenre>{
        var list = ArrayList<MovieGenre>()
        movie.genre_ids?.forEach {
            list.add(MovieGenre(null, movie.id, it))
        }
        return list
    }

    private fun saveGenres (genres: List<Genre>) {
        Single.fromCallable { genreDao.insertGenres(genres) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

    }

    fun observeMovies() : LiveData<List<Movie>> {
        return movieDao.getAllMovies()
    }
}