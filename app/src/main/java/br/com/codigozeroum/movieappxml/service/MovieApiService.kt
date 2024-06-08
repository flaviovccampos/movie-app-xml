package br.com.codigozeroum.movieappxml.service

import br.com.codigozeroum.movieappxml.model.MovieDetail
import br.com.codigozeroum.movieappxml.model.MoviePopularResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Response<MoviePopularResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieById(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String): Response<MovieDetail>

}