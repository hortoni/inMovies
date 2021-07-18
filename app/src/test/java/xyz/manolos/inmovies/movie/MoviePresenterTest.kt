package xyz.manolos.inmovies.movie

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import xyz.manolos.inmovies.database.GenreDao
import xyz.manolos.inmovies.database.MovieDao
import xyz.manolos.inmovies.database.MovieGenreDao
import xyz.manolos.inmovies.model.Genre
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.model.ResponseGenres
import xyz.manolos.inmovies.model.ResponseMovies
import xyz.manolos.inmovies.service.MovieService

@RunWith(MockitoJUnitRunner::class)


class MoviePresenterTest {
    @Mock
    private lateinit var view: MovieView

    @Mock
    private lateinit var movieService: MovieService

    @InjectMocks
    private lateinit var presenter: MoviePresenter

    @Mock
    private lateinit var movieDao: MovieDao

    @Mock
    private lateinit var genreDao: GenreDao

    @Mock
    private lateinit var movieGenreDao: MovieGenreDao

    @Before
    fun `set up`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }


    @Test
    fun `should show error when fetch movies fails`() {
        BDDMockito.given(movieService.fetchMovies(1)).willReturn(Single.error(Throwable()))
        presenter.fetchMovies(1)
        verify(view).showError()
    }

    @Test
    fun `should update page when fetch movies is fetched`() {
        val responseMovie = ResponseMovies(emptyList(), 1,1 ,1)
        BDDMockito.given(movieService.fetchMovies(1)).willReturn(Single.just(responseMovie))
        BDDMockito.given(movieDao.insertMovies(ArrayList<Movie>())).willReturn(Completable.complete())
        presenter.fetchMovies(1)
        verify(view).updatePage(responseMovie)
    }

    @Test
    fun `should save movies in database when fetch movies is successful` () {
        val responseMovie = ResponseMovies(emptyList(), 1,1 ,1)
        BDDMockito.given(movieService.fetchMovies(1)).willReturn(Single.just(responseMovie))
        val list = ArrayList<Movie>()
        BDDMockito.given(movieDao.insertMovies(list)).willReturn(Completable.complete())
        presenter.fetchMovies(1)
        verify(movieDao).insertMovies(list)
    }

    @Test
    fun `should show error when fetch genres and movies fails`() {
        BDDMockito.given(movieService.fetchGenres()).willReturn(Single.error(Throwable()))
        presenter.fetchGenresAndMovies(1)
        verify(view).showError()
    }

    @Test
    fun `should update page when fetch genres movies is fetched`() {
        val responseGenres = ResponseGenres(ArrayList<Genre>())
        BDDMockito.given(movieService.fetchGenres()).willReturn(Single.just(responseGenres))
        val responseMovie = ResponseMovies(ArrayList<Movie>(), 1,1 ,1)
        BDDMockito.given(genreDao.insertGenres(ArrayList<Genre>())).willReturn(Completable.complete())
        BDDMockito.given(movieService.fetchMovies(1)).willReturn(Single.just(responseMovie))
        BDDMockito.given(movieDao.insertMovies(ArrayList<Movie>())).willReturn(Completable.complete())
        presenter.fetchGenresAndMovies(1)
        verify(view).updatePage(responseMovie)
    }

    @Test
    fun `should save genres when fetch genres movies is fetched`() {
        val responseGenres = ResponseGenres(emptyList())
        BDDMockito.given(movieService.fetchGenres()).willReturn(Single.just(responseGenres))
        val list = ArrayList<Genre>()
        BDDMockito.given(genreDao.insertGenres(list)).willReturn(Completable.complete())
        presenter.fetchGenresAndMovies(1)
        verify(genreDao).insertGenres(list)

    }

    @Test
    fun `should fetch movies when fetch genres is fetched`() {
        val responseGenres = ResponseGenres(emptyList())
        BDDMockito.given(movieService.fetchGenres()).willReturn(Single.just(responseGenres))
        BDDMockito.given(genreDao.insertGenres(ArrayList<Genre>())).willReturn(Completable.complete())
        presenter.fetchGenresAndMovies(1)
        verify(movieService).fetchMovies(1)
    }

    @Test
    fun `should save movies when fetch genres movies is fetched`() {
        val responseGenres = ResponseGenres(emptyList())

        BDDMockito.given(movieService.fetchGenres()).willReturn(Single.just(responseGenres))
        val responseMovie = ResponseMovies(emptyList(), 1,1 ,1)
        BDDMockito.given(genreDao.insertGenres(ArrayList<Genre>())).willReturn(Completable.complete())
        BDDMockito.given(movieService.fetchMovies(1)).willReturn(Single.just(responseMovie))
        val list = ArrayList<Movie>()
        BDDMockito.given(movieDao.insertMovies(list)).willReturn(Completable.complete())
        presenter.fetchGenresAndMovies(1)
        verify(movieDao).insertMovies(list)

    }

}