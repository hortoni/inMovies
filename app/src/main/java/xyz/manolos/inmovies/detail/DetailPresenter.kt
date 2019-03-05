package xyz.manolos.inmovies.detail

import xyz.manolos.inmovies.database.MovieGenreDao
import xyz.manolos.inmovies.model.Movie
import javax.inject.Inject


class DetailPresenter @Inject constructor(
    private val view: DetailView,
    private val movieGenreDao: MovieGenreDao
) {

    fun observeGenres(movie: Movie) {
        view.showGenres(movieGenreDao.findGenresNamesByMovie(movie.id))
    }
}