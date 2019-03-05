package xyz.manolos.inmovies.movie

import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner
import xyz.manolos.inmovies.dao.GenreDao
import xyz.manolos.inmovies.dao.MovieDao
import xyz.manolos.inmovies.dao.MovieGenreDao
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

    @Spy
    private lateinit var movieDao: MovieDao

    @Spy
    private lateinit var genreDao: GenreDao

    @Spy
    private lateinit var movieGenreDao: MovieGenreDao

    @Before
    fun `set up`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun closeDb() {

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
        presenter.fetchMovies(1)
        verify(view).updatePage(responseMovie)

    }

}