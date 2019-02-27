package xyz.manolos.inmovies.movie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*
import xyz.manolos.inmovies.injector
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.model.Response
import javax.inject.Inject


interface MovieView {
    fun showMovies(it: Response)
    fun showError(it: Throwable)
    fun showLoading()
    fun hideLoading()
    fun updatePage(it: Response)
}

class MovieActivity : AppCompatActivity(), MovieView {

    @Inject
    lateinit var presenter: MoviePresenter

    lateinit var linearLayoutManager: LinearLayoutManager
    private var page: Int = 1
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

        swipeLayout.setOnRefreshListener {
            presenter.fetchMovies(page)
        }

    }

    private fun setupRecyclerview() {
        movies = ArrayList()
        linearLayoutManager = LinearLayoutManager(this)
        moviesList.adapter = MovieListAdapter(movies, this)
        moviesList.layoutManager = linearLayoutManager
        moviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                var total = linearLayoutManager.itemCount
                var lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                var isNearEnd = total - 1 == lastVisibleItem
                if (isNearEnd && !swipeLayout.isRefreshing && page != -1)  {
                    presenter.fetchMovies(page)

                }
            }
        })
    }

    override fun showMovies(it: Response) {
        movies.addAll(it.results)
        moviesList.adapter!!.notifyDataSetChanged()

    }

    override fun updatePage(it: Response) {
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


}