package xyz.manolos.inmovies.detail

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
private const val GENRES = "genres"


interface DetailView {
    fun showGenres(list: LiveData<List<String>>)
}

class DetailActivity : AppCompatActivity(), DetailView {

    @Inject
    lateinit var presenter: DetailPresenter

    private lateinit var genres: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        injector
            .plusDetail(DetailModule(this))
            .inject(this)

        var movie = intent.getParcelableExtra(MOVIE) as Movie

        genres = savedInstanceState?.getString(GENRES) ?: ""
        movieGenresTextview.text = genres

        movieTitleTextview.text = movie.title
        movieOverviewTextview.text = movie.overview
        movieReleaseDateTextview.text =
            String.format(getString(R.string.release_date), DateFormatter.formatDate(movie.release_date))
        Picasso.get()
            .load(String.format(IMAGE_URL, movie.poster_path))
            .fit()
            .centerCrop()
            .into(movieImageView)

        if (genres.isBlank()) {
            presenter.observeGenres(movie)
        }

    }

    override fun showGenres(list: LiveData<List<String>>) {
        var text: String
        list.observe(this, Observer {
            text = it.joinToString {
                it
            }
            movieGenresTextview.text = text
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(GENRES, genres)
    }

}
