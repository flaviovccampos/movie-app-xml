package br.com.codigozeroum.movieappxml.adapter

import br.com.codigozeroum.movieappxml.model.Movie

interface OnMovieClickListener {
    fun onMovieClick(movie: Movie)
}