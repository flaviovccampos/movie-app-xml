package br.com.codigozeroum.movieappxml.view.movies

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.codigozeroum.movieappxml.R
import br.com.codigozeroum.movieappxml.adapter.MovieAdapter
import br.com.codigozeroum.movieappxml.adapter.OnMovieClickListener
import br.com.codigozeroum.movieappxml.model.Movie
import br.com.codigozeroum.movieappxml.repository.ApiResult
import br.com.codigozeroum.movieappxml.view.BaseView
import br.com.codigozeroum.movieappxml.view.HomeInterface

class MoviesFragment(private val homeInterface: HomeInterface) : Fragment(), BaseView, OnMovieClickListener {

    private val viewModel: MoviesViewModel by viewModels{
        MoviesViewModelFactory(this)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private val movies = mutableListOf<Movie>()
    private lateinit var errorLayout: ConstraintLayout
    private lateinit var progressBar: ContentLoadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observer()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_movies, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        movieAdapter = MovieAdapter(movies, this)
        recyclerView.adapter = movieAdapter
        errorLayout = view.findViewById(R.id.layout_error)
        progressBar = view.findViewById(R.id.progress_bar)

        return view
    }

    private fun observer(){
        viewModel.moviesApiResult.observe(this) { apiResult ->
            when (apiResult) {
                is ApiResult.Success -> {
                    dismissLoading()
                    this.movies.clear()
                    this.movies.addAll(apiResult.data)
                    movieAdapter.updateMovies(this.movies)
                }
                is ApiResult.Error -> {
                    dismissLoading()
                    showError(apiResult.error.message!!)
                }
                else -> {
                    showLoading()
                }
            }
        }
    }

    override fun onMovieClick(movie: Movie) {
        goToDetails(movie)
    }

    private fun goToDetails(movie: Movie) {
        homeInterface.goToMovieDetails(movie.id)
    }

    override fun showLoading() {
        progressBar.show()
    }

    override fun dismissLoading() {
        progressBar.hide()
    }

    override fun showError(message: String) {
        dismissLoading()
        errorLayout.visibility = View.VISIBLE
        errorLayout.findViewById<AppCompatTextView>(R.id.tv_error_message).text = message
    }

    companion object {
        fun newInstance(homeInterface: HomeInterface) = MoviesFragment(homeInterface)
    }
}