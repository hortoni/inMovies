package xyz.manolos.inmovies.detail

import xyz.manolos.inmovies.dao.MovieGenreDao
import xyz.manolos.inmovies.model.Movie
import javax.inject.Inject


class DetailPresenter @Inject constructor(
    private val view: DetailView,
    private val movieGenreDao: MovieGenreDao
) {

    fun getGenres (movie: Movie){
        view.showGenres(movieGenreDao.findGenresNamesByMovieId(movie.id))
    }

}