package xyz.manolos.inmovies.movie

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*
import xyz.manolos.inmovies.injector
import xyz.manolos.inmovies.model.Genre
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.model.ResponseGenres
import xyz.manolos.inmovies.model.ResponseMovies
import javax.inject.Inject


interface MovieView {
    fun showError(it: Throwable)
    fun showLoading()
    fun hideLoading()
    fun updatePage(it: ResponseMovies)
    fun getGenres(it:ResponseGenres)
}

class MovieActivity : AppCompatActivity(), MovieView {

    @Inject
    lateinit var presenter: MoviePresenter

    lateinit var linearLayoutManager: androidx.recyclerview.widget.LinearLayoutManager
    private var page: Int = 1
    lateinit var genres: ArrayList<Genre>
    lateinit var movies: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(xyz.manolos.inmovies.R.layout.activity_movies)
        setSupportActionBar(toolbar)

        injector
            .plusMovie(MovieModule(this))
            .inject(this)

        setupRecyclerview()

        presenter.fetchMovies(page)
        presenter.observeMovies().observe(this, Observer {
            movies.addAll(it)
            moviesList.adapter!!.notifyDataSetChanged()
        })

        swipeLayout.setOnRefreshListener {
            presenter.fetchMovies(page)
        }


    }

    private fun setupRecyclerview() {
        movies = ArrayList()
        linearLayoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        moviesList.layoutManager = linearLayoutManager
        moviesList.adapter = MovieListAdapter(movies, this)
        moviesList.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                var total = linearLayoutManager.itemCount
                var lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                var isNearEnd = total - 1 == lastVisibleItem
                if (isNearEnd && !swipeLayout.isRefreshing && page != -1)  {
                    presenter.fetchMovies(page)

                }
            }
        })
    }

    override fun updatePage(it: ResponseMovies) {
        if (it.total_pages == page) {
            page = -1
        } else {
            page ++
        }
    }

    override fun showError(it: Throwable) {
        Log.e("DEBUG", "error: " + it.message)
    }

    override fun showLoading() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeLayout.isRefreshing = false
    }

    override fun getGenres(it: ResponseGenres) {
        genres = it.genres as ArrayList<Genre>
    }
}
