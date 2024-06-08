package br.com.codigozeroum.movieappxml.service

import br.com.codigozeroum.movieappxml.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    private var token: String = BuildConfig.BEARER_TOKEN

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}