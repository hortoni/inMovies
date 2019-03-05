package xyz.manolos.inmovies.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import xyz.manolos.inmovies.database.AppDatabase
import xyz.manolos.inmovies.database.GenreDao
import xyz.manolos.inmovies.database.MovieDao
import xyz.manolos.inmovies.database.MovieGenreDao
import xyz.manolos.inmovies.model.Genre
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.model.MovieGenre

@RunWith(AndroidJUnit4::class)


class DatabaseRoomTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var movieDao: MovieDao

    private lateinit var genreDao: GenreDao
    private lateinit var movieGenreDao: MovieGenreDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        appDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase::class.java).build()
        movieDao = appDatabase.movieDao()
        genreDao = appDatabase.genreDao()
        movieGenreDao = appDatabase.movieGenreDao()

    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun shouldReturnEmptyMovieList() {
        val allMovies = movieDao.getAllMovies()
        allMovies.observeForever {
            Assert.assertTrue(it.isEmpty())
        }
    }

    @Test
    fun shouldReturnNotEmptyMovieList() {
        val list = ArrayList<Movie>()
        list.add(Movie(1, "title", null, null, null, null, emptyList()))
        movieDao.insertMovies(list)
        val allMovies = movieDao.getAllMovies()
        allMovies.observeForever {
            Assert.assertFalse(it.isEmpty())
        }
    }

    @Test
    fun shouldReturn3MoviesInMoviesList() {
        val list = ArrayList<Movie>()
        list.add(Movie(1, "title1", null, null, null, null, emptyList()))
        list.add(Movie(2, "title2", null, null, null, null, emptyList()))
        list.add(Movie(3, "title3", null, null, null, null, emptyList()))

        movieDao.insertMovies(list)
        val allMovies = movieDao.getAllMovies()
        allMovies.observeForever {
            Assert.assertTrue(it.size == 3)
        }
    }

    @Test
    fun shouldReturnEmptyGenreList() {
        val allGenres = genreDao.getAllGenres()
        allGenres.observeForever {
            Assert.assertTrue(it.isEmpty())
        }
    }

    @Test
    fun shouldReturnNotEmptyGenreList() {
        val list = ArrayList<Genre>()
        list.add(Genre(1, "genre"))
        genreDao.insertGenres(list)
        val allGenres = genreDao.getAllGenres()
        allGenres.observeForever {
            Assert.assertFalse(it.isEmpty())
        }
    }

    @Test
    fun shouldReturn3GenresInGenreList() {
        val list = ArrayList<Genre>()
        list.add(Genre(1, "genre1"))
        list.add(Genre(2, "genre2"))
        list.add(Genre(3, "genre3"))

        genreDao.insertGenres(list)
        val allGenres = genreDao.getAllGenres()
        allGenres.observeForever {
            Assert.assertTrue(it.size == 3)
        }
    }


    @Test
    fun shouldReturnGenreByMovieId() {
        val genreList = ArrayList<Genre>()
        genreList.add(Genre(1, "title1"))
        genreList.add(Genre(2, "title2"))
        genreDao.insertGenres(genreList)

        val genreListFromMovie = ArrayList<Long>()
        genreListFromMovie.add(1)
        genreListFromMovie.add(2)
        val list = ArrayList<Movie>()
        list.add(Movie(1, "title1", null, null, null, null, genreListFromMovie ))
        movieDao.insertMovies(list)

        val moviesGenresList = ArrayList<MovieGenre>()
        moviesGenresList.add(MovieGenre( 1, 1, 1))
        moviesGenresList.add(MovieGenre( 2, 1, 2))
        movieGenreDao.insertMoviesGenres(moviesGenresList)

        val genresNamesList = movieGenreDao.findGenresNamesByMovie(1)
        genresNamesList.observeForever{
            Assert.assertTrue(it.size == 2)
        }
    }

}