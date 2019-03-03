package xyz.manolos.inmovies.movie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_list_item.view.*
import xyz.manolos.inmovies.detail.DetailActivity
import xyz.manolos.inmovies.model.Movie
import xyz.manolos.inmovies.util.DateFormatter


private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/%s"
private const val MOVIE = "movie"

class MovieListAdapter(private val context: Context) : ListAdapter<Movie, MovieListAdapter.ViewHolder>(DiffCallback()) {


    class DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(xyz.manolos.inmovies.R.layout.movie_list_item, parent, false)

        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bindView(movie)
        holder.itemView.setOnClickListener{
            //
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE, movie)
            context.startActivity(intent)
        }

    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        fun bindView(movie: Movie) {
            val title = itemView.card_view_title
            val date = itemView.card_view_date
            val image = itemView.card_view_image

            title.text = movie.title
            date.text =  DateFormatter.formatDate(movie.release_date)


            Picasso.get()
                .load(String.format(IMAGE_URL, movie.backdrop_path) )
                .fit()
                .centerCrop()
                .into(image)
        }

    }


}

