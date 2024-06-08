package br.com.codigozeroum.movieappxml.view

interface BaseView {
    fun showLoading()
    fun dismissLoading()
    fun showError(message: String)
}