package br.com.codigozeroum.movieappxml.view.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.codigozeroum.movieappxml.view.BaseView

@Suppress("UNCHECKED_CAST")
class MoviesViewModelFactory(private val baseView: BaseView) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesViewModel::class.java)) {
            return MoviesViewModel(baseView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}