package br.com.codigozeroum.movieappxml.adapter

import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.codigozeroum.movieappxml.R
import br.com.codigozeroum.movieappxml.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter (private var movies: List<Movie>, private val onMovieClickListener: OnMovieClickListener) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPoster: ImageView = itemView.findViewById(R.id.iv_poster)
        val textViewTitle: TextView = itemView.findViewById(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.textViewTitle.text = movie.title
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w300" + movie.poster_path)
            .transform(RoundedCornersTransformation(20f))
            .into(holder.imageViewPoster)

        holder.itemView.setOnClickListener {
            onMovieClickListener.onMovieClick(movie)
        }
    }

    override fun getItemCount() = movies.size

    fun updateMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

}