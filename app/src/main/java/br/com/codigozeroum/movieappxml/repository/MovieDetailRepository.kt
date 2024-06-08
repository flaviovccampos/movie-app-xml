package br.com.codigozeroum.movieappxml.repository

import br.com.codigozeroum.movieappxml.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    suspend fun getMovieById(movieId: Int, apiKey: String): Flow<ApiResult<MovieDetail>>

}