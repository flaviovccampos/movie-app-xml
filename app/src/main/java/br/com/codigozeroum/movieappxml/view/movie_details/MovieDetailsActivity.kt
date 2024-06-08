package br.com.codigozeroum.movieappxml.view.movie_details

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.codigozeroum.movieappxml.R
import br.com.codigozeroum.movieappxml.adapter.GenreAdapter
import br.com.codigozeroum.movieappxml.model.MovieDetail
import br.com.codigozeroum.movieappxml.repository.ApiResult
import br.com.codigozeroum.movieappxml.view.BaseView
import com.squareup.picasso.Picasso
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class MovieDetailsActivity : AppCompatActivity(), BaseView {

    private val viewModel: MovieDetailsViewModel by viewModels{
        MovieDetailsViewModelFactory(this)
    }
    private lateinit var movieDetails: MovieDetail
    private lateinit var backdrop: AppCompatImageView
    private lateinit var title: AppCompatTextView
    private lateinit var movieTime: AppCompatTextView
    private lateinit var movieRate: AppCompatTextView
    private lateinit var releaseDate: AppCompatTextView
    private lateinit var genresRecyclerView: RecyclerView
    private lateinit var genresAdapter: GenreAdapter
    private lateinit var synopsis: AppCompatTextView
    private lateinit var errorLayout: ConstraintLayout
    private lateinit var progressBar: ContentLoadingProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        getMovieIntentExtra()
    }

    private fun initViews(){
        backdrop = findViewById(R.id.iv_backdrop)
        title = findViewById(R.id.tv_title)
        movieTime = findViewById(R.id.tv_movie_time)
        movieRate = findViewById(R.id.tv_votes)
        releaseDate = findViewById(R.id.tv_release_date)
        genresRecyclerView = findViewById(R.id.rv_genres)
        genresRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        synopsis = findViewById(R.id.tv_synopsis)
        errorLayout = findViewById(R.id.layout_error)
        progressBar = findViewById(R.id.progress_bar)
    }

    private fun getMovieIntentExtra() {
        val movieId = intent.getIntExtra("MOVIE_ID", 0)
        if(movieId != 0){
            viewModel.getMovieDetails(movieId)
            observeMovieDetails()
        }else{
            showError("Erro inesperado, tente novamente mais tarde")
        }
    }

    private fun observeMovieDetails() {
        viewModel.movieDetailApiResult.observe(this) { apiResult ->
            when(apiResult){
                is ApiResult.Success -> {
                    dismissLoading()
                    movieDetails = apiResult.data
                    bindViews()
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

    private fun bindViews(){
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w780" + movieDetails.backdrop_path)
            .into(backdrop)
        title.text = movieDetails.title
        movieTime.text = "${movieDetails.runtime} minutes"
        val movieRateRounded = roundToOneDecimalPlace(movieDetails.vote_average)
        movieRate.text = "$movieRateRounded"
        val releaseDateFormatted = convertDate(movieDetails.release_date)
        releaseDate.text = releaseDateFormatted
        genresAdapter = GenreAdapter(movieDetails.genres)
        genresRecyclerView.adapter = genresAdapter
        synopsis.text = movieDetails.overview
    }

    private fun convertDate(dateStr: String): String{
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH)
        val date = LocalDate.parse(dateStr, inputFormatter)
        val formattedDate = date.format(outputFormatter)
        return formattedDate
    }

    private fun roundToOneDecimalPlace(value: Double): Double {
        return BigDecimal(value).setScale(1, RoundingMode.HALF_UP).toDouble()
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
}