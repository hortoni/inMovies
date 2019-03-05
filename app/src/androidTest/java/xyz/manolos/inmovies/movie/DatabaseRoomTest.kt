package xyz.manolos.inmovies.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.*
import org.junit.runner.RunWith
import xyz.manolos.inmovies.dao.AppDatabase
import xyz.manolos.inmovies.dao.GenreDao
import xyz.manolos.inmovies.dao.MovieDao
import xyz.manolos.inmovies.dao.MovieGenreDao
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
        val appContext = InstrumentationRegistry.getTargetContext()
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
        val movie = Movie(1, "title", null, null, null, null, emptyList())
        movieDao.insertMovie(movie)
        val allMovies = movieDao.getAllMovies()
        allMovies.observeForever {
            Assert.assertFalse(it.isEmpty())
        }
    }

    @Test
    fun shouldReturn3MoviesinMoviesList() {
        var list = ArrayList<Movie>()
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
        val genre = Genre(1, "genre")
        genreDao.insertGenre(genre)
        val allGenres = genreDao.getAllGenres()
        allGenres.observeForever {
            Assert.assertFalse(it.isEmpty())
        }
    }

    @Test
    fun shouldReturn3GenresInGenreList() {
        var list = ArrayList<Genre>()
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
    fun shouldReturnEmptyMovieGenreList() {
        val allMovieGenres = movieGenreDao.getAllMoviesGenres()
        allMovieGenres.observeForever {
            Assert.assertTrue(it.isEmpty())
        }
    }

    @Test
    fun shouldReturnNotEmptyMovieGenreList() {
        val movieGenre = MovieGenre(1, 1, 1)
        movieGenreDao.insertMovieGenre(movieGenre)
        val allMoviesGenres = movieGenreDao.getAllMoviesGenres()
        allMoviesGenres.observeForever {
            Assert.assertFalse(it.isEmpty())
        }
    }

    @Test
    fun shouldReturn3MoviesGenresInMoviesGenreList() {
        var list = ArrayList<MovieGenre>()
        list.add(MovieGenre(1, 1, 1))
        list.add(MovieGenre(2, 2, 2))
        list.add(MovieGenre(3, 3, 3))

        movieGenreDao.insertMoviesGenres(list)
        val allMoviesGenres = movieGenreDao.getAllMoviesGenres()
        allMoviesGenres.observeForever {
            Assert.assertTrue(it.size == 3)
        }
    }

    @Test
    fun shouldReturnGenreByMovieId() {
        var genreList = ArrayList<Genre>()
        genreList.add(Genre(1, "title1"))
        genreList.add(Genre(2, "title2"))
        genreDao.insertGenres(genreList)

        var genreListFromMovie = ArrayList<Long>()
        genreListFromMovie.add(1)
        genreListFromMovie.add(2)
        var movie = (Movie(1, "title1", null, null, null, null, genreListFromMovie ))
        movieDao.insertMovie(movie)

        var moviesGenresList = ArrayList<MovieGenre>()
        moviesGenresList.add(MovieGenre( 1, 1, 1))
        moviesGenresList.add(MovieGenre( 2, 1, 2))
        movieGenreDao.insertMoviesGenres(moviesGenresList)

        val genresNamesList = movieGenreDao.findGenresNamesByMovie(1)
        genresNamesList.observeForever{
            Assert.assertTrue(it.size == 2)
        }
    }

}