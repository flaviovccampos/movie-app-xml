package br.com.codigozeroum.movieappxml.view.movie_details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.codigozeroum.movieappxml.BuildConfig
import br.com.codigozeroum.movieappxml.model.MovieDetail
import br.com.codigozeroum.movieappxml.repository.ApiResult
import br.com.codigozeroum.movieappxml.repository.MovieDetailRepositoryImpl
import br.com.codigozeroum.movieappxml.service.NetworkException
import br.com.codigozeroum.movieappxml.service.ServerException
import br.com.codigozeroum.movieappxml.view.BaseView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val baseView: BaseView) : ViewModel() {

    private val _movieDetailApiResult = MutableLiveData<ApiResult<MovieDetail>>(ApiResult.Loading)
    val movieDetailApiResult: LiveData<ApiResult<MovieDetail>> get() = _movieDetailApiResult
    private val movieDetailRepository = MovieDetailRepositoryImpl()

    fun getMovieDetails(movieId: Int) {

        viewModelScope.launch {

            //Just for testing/show Loading
            delay(2000)

            movieDetailRepository.getMovieById(movieId, BuildConfig.API_KEY)
                .onEach { apiResult ->
                    _movieDetailApiResult.postValue(apiResult)
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
                            baseView.showError("Erro inesperado ao carregar detalhes do filme, tente novamente mais tarde")
                        }
                    }
                }
                .collect()

        }

    }

}