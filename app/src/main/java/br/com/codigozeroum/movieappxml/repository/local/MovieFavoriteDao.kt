package br.com.codigozeroum.movieappxml.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.codigozeroum.movieappxml.model.MovieFavorite

@Dao
interface MovieFavoriteDao {

    @Insert
    suspend fun insert(movie: MovieFavorite)

    @Query("SELECT * FROM movie_favorite")
    suspend fun getAllFavorites(): List<MovieFavorite>

    @Query("SELECT id FROM movie_favorite")
    suspend fun getAllIdsFavorites(): List<Int>

    @Query("DELETE FROM movie_favorite WHERE id = :id")
    suspend fun delete(id: Int)

}