package xyz.manolos.inmovies.movie

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item.view.*
import xyz.manolos.inmovies.model.Movie
import java.text.SimpleDateFormat


private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/%s"

class MovieListAdapter(private val movies: List<Movie>,
                       private val context: Context) : Adapter<MovieListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(xyz.manolos.inmovies.R.layout.movie_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bindView(movie)

    }

    fun getMovies() : List<Movie> {
        return movies
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(movie: Movie) {
            val title = itemView.card_view_title
            val date = itemView.card_view_date
            val image = itemView.card_view_image

            title.text = movie.title
            date.text = formatDate(movie.release_date)


            Picasso.get()
                .load(String.format(IMAGE_URL, movie.backdrop_path) )
                .fit()
                .centerCrop()
                .into(image)
        }

        private fun formatDate (date :String? ) : String {
            var format = SimpleDateFormat("yyyy-MM-dd")
            val newDate = format.parse(date)

            format = SimpleDateFormat("dd/MM/yyyy")
            return format.format(newDate)
        }

    }


}

