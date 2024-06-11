package br.com.codigozeroum.movieappxml.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_favorite")
data class MovieFavorite(
    @PrimaryKey
    val id: Int,
    val title: String,
    val posterPath: String
)