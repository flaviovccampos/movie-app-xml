package br.com.codigozeroum.movieappxml.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.codigozeroum.movieappxml.model.MovieFavorite

@Database(entities = [MovieFavorite::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun movieFavoriteDao(): MovieFavoriteDao
}