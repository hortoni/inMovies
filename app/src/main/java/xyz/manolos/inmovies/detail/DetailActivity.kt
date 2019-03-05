package xyz.manolos.inmovies.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import xyz.manolos.inmovies.R
import xyz.manolos.inmovies.injector
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.util.DateFormatter
import javax.inject.Inject


private const val MOVIE = "movie"
private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/%s"

interface DetailView {
    fun showGenres(list: LiveData<List<String>>)
}

class DetailActivity : AppCompatActivity(), DetailView {

    companion object {
        fun newIntent(context: Context, movie: Movie): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(MOVIE, movie)
            }
        }
    }

    @Inject
    lateinit var presenter: DetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        injector
            .plusDetail(DetailModule(this))
            .inject(this)

        val movie = intent.getParcelableExtra(MOVIE) as Movie

        setupView(movie)

        presenter.observeGenres(movie)
    }

    private fun setupView(movie: Movie) {
        movieTitleTextview.text = movie.title
        movieOverviewTextview.text = movie.overview
        movieReleaseDateTextview.text =
            String.format(getString(R.string.release_date), DateFormatter.formatDate(movie.releaseDate))

        Picasso.get()
            .load(IMAGE_URL.format(movie.posterPath))
            .fit()
            .centerCrop()
            .into(movieImageView)
    }

    override fun showGenres(list: LiveData<List<String>>) {
        list.observe(this, Observer {
            movieGenresTextview.text = it.joinToString()
        })
    }

}
