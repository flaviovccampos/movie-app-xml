package br.com.codigozeroum.movieappxml.repository

import br.com.codigozeroum.movieappxml.BuildConfig
import br.com.codigozeroum.movieappxml.model.Movie
import br.com.codigozeroum.movieappxml.service.MovieApiService
import br.com.codigozeroum.movieappxml.service.NetworkException
import br.com.codigozeroum.movieappxml.service.RetrofitInstance
import br.com.codigozeroum.movieappxml.service.ServerException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class MoviePopularRepositoryImpl: MoviePopularRepository{

    private val apiService: MovieApiService by lazy{ RetrofitInstance.create().create(MovieApiService::class.java) }

    override suspend fun getPopularMovies(apiKey: String): Flow<ApiResult<List<Movie>>> = flow {
        emit(ApiResult.Loading)
        try{
            val response = apiService.getPopularMovies(BuildConfig.API_KEY)
            if (response.isSuccessful) {
                response.body()?.results?.let {
                    emit(ApiResult.Success(it))
                }
            }else{
                emit(ApiResult.Error(ServerException("Erro inesperado ao carregar os filmes, tente novamente mais tarde")))
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