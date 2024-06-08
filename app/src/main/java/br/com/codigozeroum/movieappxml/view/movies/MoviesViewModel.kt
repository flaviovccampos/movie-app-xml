package br.com.codigozeroum.movieappxml.view.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.codigozeroum.movieappxml.BuildConfig
import br.com.codigozeroum.movieappxml.model.Movie
import br.com.codigozeroum.movieappxml.repository.ApiResult
import br.com.codigozeroum.movieappxml.repository.MoviePopularRepositoryImpl
import br.com.codigozeroum.movieappxml.service.NetworkException
import br.com.codigozeroum.movieappxml.service.ServerException
import br.com.codigozeroum.movieappxml.view.BaseView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MoviesViewModel(private val baseView: BaseView) : ViewModel() {

    private val _moviesApiResult = MutableLiveData<ApiResult<List<Movie>>>(ApiResult.Loading)
    val moviesApiResult: LiveData<ApiResult<List<Movie>>> get() = _moviesApiResult
    private val moviePopularRepositoryImpl = MoviePopularRepositoryImpl()

    init {
        getMovies()
    }

    private fun getMovies() {

        viewModelScope.launch {

            //Just for testing/show Loading
            delay(2500)

            moviePopularRepositoryImpl.getPopularMovies(BuildConfig.API_KEY)
                .onEach { apiResult ->
                    _moviesApiResult.postValue(apiResult)
                }
                .catch { error ->
                    val exception = error.cause
                    when (exception) {
                        is NetworkException -> {
                            Log.d("NetworkException", "NetworkException")
                            baseView.showError("Verifique sua conexão de internet e tente novamente")
                        }

                        is ServerException -> {
                            Log.d("ServerException", "ServerException")
                            baseView.showError("Erro inesperado ao carregar os filmes, tente novamente mais tarde")
                        }
                    }
                }
                .collect()

        }

    }

}