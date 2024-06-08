package br.com.codigozeroum.movieappxml.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.codigozeroum.movieappxml.R
import br.com.codigozeroum.movieappxml.model.Genre

class GenreAdapter (private val genres: List<Genre>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvGenre: AppCompatTextView = itemView.findViewById(R.id.tv_genre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.genre_item, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.tvGenre.text = genre.name
    }

    override fun getItemCount(): Int = genres.size

}