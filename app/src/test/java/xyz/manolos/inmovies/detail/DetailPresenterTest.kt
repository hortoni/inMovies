package xyz.manolos.inmovies.detail

import androidx.lifecycle.MutableLiveData
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import xyz.manolos.inmovies.database.MovieGenreDao
import xyz.manolos.inmovies.model.Movie

@RunWith(MockitoJUnitRunner::class)


class DetailPresenterTest {
    @Mock
    private lateinit var view: DetailView

    @InjectMocks
    private lateinit var presenter: DetailPresenter

    @Mock
    private lateinit var movieGenreDao: MovieGenreDao

    @Before
    fun `set up`() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun `should show genres when find genres names by movie`() {
        val movie = Movie(1, "title", null, null, null, null, emptyList())

        val liveData = MutableLiveData<List<String>>()
        BDDMockito.given(movieGenreDao.findGenresNamesByMovie(1)).willReturn(liveData)
        presenter.observeGenres(movie)
        Mockito.verify(view).showGenres(liveData)
    }

}