package br.com.codigozeroum.movieappxml.repository

import br.com.codigozeroum.movieappxml.model.Movie
import br.com.codigozeroum.movieappxml.model.MoviePopularResponse
import kotlinx.coroutines.flow.Flow

interface MoviePopularRepository {

    suspend fun getPopularMovies(apiKey: String): Flow<ApiResult<List<Movie>>>

}