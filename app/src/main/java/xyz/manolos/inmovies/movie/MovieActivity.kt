package xyz.manolos.inmovies.movie

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*
import xyz.manolos.inmovies.R
import xyz.manolos.inmovies.injector
import xyz.manolos.inmovies.model.ResponseMovies
import javax.inject.Inject


interface MovieView {
    fun showError()
    fun showLoading()
    fun hideLoading()
    fun updatePage(it: ResponseMovies)
}

class MovieActivity : AppCompatActivity(), MovieView {

    @Inject
    lateinit var presenter: MoviePresenter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var page: Int = 1
    private lateinit var adapter: MovieListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(xyz.manolos.inmovies.R.layout.activity_movies)
        setSupportActionBar(toolbar)

        injector
            .plusMovie(MovieModule(this))
            .inject(this)

        setupRecyclerview()

        presenter.fetchGenresAndMovies(page)

        presenter.observeMovies().observe(this, Observer {
            adapter.submitList(it)
        })

        swipeLayout.setOnRefreshListener {
            presenter.fetchGenresAndMovies(page)
        }
    }

    private fun setupRecyclerview() {
        linearLayoutManager = LinearLayoutManager(this)
        moviesList.layoutManager = linearLayoutManager
        adapter = MovieListAdapter(this)
        moviesList.adapter = adapter
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

    override fun updatePage(it: ResponseMovies) {
        if (it.totalPages == page) {
            page = -1
        } else {
            page ++
        }
    }

    override fun showError() {
        Toast.makeText(this, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeLayout.isRefreshing = false
    }
}
