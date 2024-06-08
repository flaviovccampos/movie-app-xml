package br.com.codigozeroum.movieappxml.service

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response: Response

        try {
            response = chain.proceed(request)
        } catch (e: IOException) {
            // Lidar com erros de rede (falta de conectividade, etc.)
            throw NetworkException("Network error occurred", e)
        }

        if (response.code == 500) {
            // Lidar com código de erro 500 (servidor)
            throw ServerException("Server error occurred, Code 500")
        }

        return response
    }
}

class NetworkException(message: String, cause: Throwable? = null) : IOException(message, cause)
class ServerException(message: String) : IOException(message)