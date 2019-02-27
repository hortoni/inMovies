package xyz.manolos.inmovies.movie

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_movies.*
import kotlinx.android.synthetic.main.content_movies.*
import xyz.manolos.inmovies.injector
import xyz.manolos.inmovies.model.Response
import javax.inject.Inject






interface MovieView {
    fun showMovies(response: Response)
    fun showError(it: Throwable)
    fun showLoading()
    fun hideLoading()
}

class MovieActivity : AppCompatActivity(), MovieView {


    @Inject
    lateinit var presenter: MoviePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(xyz.manolos.inmovies.R.layout.activity_movies)
        setSupportActionBar(toolbar)

        injector
            .plusMovie(MovieModule(this))
            .inject(this)


        setupRecyclerview()

        presenter.fetchMovies()

        swipeLayout.setOnRefreshListener {
            presenter.fetchMovies()
        }

    }

    fun setupRecyclerview() {
//        val layoutManager = GridLayoutManager(this, 1)
        moviesList.layoutManager = LinearLayoutManager(this)
    }

    override fun showMovies(response: Response) {
        moviesList.adapter = MovieListAdapter(response.results, this)
        response.results.forEach {
            Log.e("DEBUG", it.title)
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
