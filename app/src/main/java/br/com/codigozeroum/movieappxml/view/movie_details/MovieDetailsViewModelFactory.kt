package br.com.codigozeroum.movieappxml.view.movie_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.codigozeroum.movieappxml.view.BaseView
import br.com.codigozeroum.movieappxml.view.movies.MoviesViewModel

class MovieDetailsViewModelFactory (private val baseView: BaseView) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(baseView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}