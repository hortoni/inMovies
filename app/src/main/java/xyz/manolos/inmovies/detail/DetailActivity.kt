package xyz.manolos.inmovies.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import xyz.manolos.inmovies.R
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.util.DateFormatter


private const val MOVIE = "movie"
private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/%s"

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var movie = intent.getParcelableExtra(MOVIE) as Movie

        movieTitleTextview.text =  movie.title
        movieOverviewTextview.text = movie.overview
        movieReleaseDateTextview.text =  String.format(getString(R.string.release_date), DateFormatter.formatDate(movie.release_date))
        Picasso.get()
            .load(String.format(IMAGE_URL, movie.poster_path) )
            .fit()
            .centerCrop()
            .into(movieImageView)
    }
}
