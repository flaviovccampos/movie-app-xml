package br.com.codigozeroum.movieappxml.repository

sealed class ApiResult<out D> private constructor() {
    data class Success<out T>(val data: T): ApiResult<T>()
    data class Error(val error: Throwable): ApiResult<Nothing>()
    data object Loading: ApiResult<Nothing>()
}