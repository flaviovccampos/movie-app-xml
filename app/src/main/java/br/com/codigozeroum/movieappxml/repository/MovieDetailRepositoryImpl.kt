package br.com.codigozeroum.movieappxml.repository

import br.com.codigozeroum.movieappxml.BuildConfig
import br.com.codigozeroum.movieappxml.model.MovieDetail
import br.com.codigozeroum.movieappxml.service.MovieApiService
import br.com.codigozeroum.movieappxml.service.NetworkException
import br.com.codigozeroum.movieappxml.service.RetrofitInstance
import br.com.codigozeroum.movieappxml.service.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MovieDetailRepositoryImpl : MovieDetailRepository {

    private val apiService: MovieApiService by lazy{ RetrofitInstance.create().create(MovieApiService::class.java) }

    override suspend fun getMovieById(movieId: Int, apiKey: String): Flow<ApiResult<MovieDetail>> = flow {
        emit(ApiResult.Loading)
        try {
            val response = apiService.getMovieById(movieId, BuildConfig.API_KEY)
            if(response.isSuccessful) {
                response.body()?.let {
                    emit(ApiResult.Success(it))
                }
            }else{
                emit(ApiResult.Error(ServerException("Server error occurred")))
            }
        }catch (e: NetworkException){
            //throw e
            throw NetworkException("Network error occurred", e)
        }catch (e: ServerException){
            //throw e
            throw ServerException("Server error occurred")
        }catch(e: Exception){
            throw e
        }catch (e: IOException){
            throw IOException("Unknown network error occurred", e)
        }
    }
}