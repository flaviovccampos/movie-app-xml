package br.com.codigozeroum.movieappxml.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import br.com.codigozeroum.movieappxml.R
import br.com.codigozeroum.movieappxml.model.Movie
import br.com.codigozeroum.movieappxml.view.favorites.FavoritesFragment
import br.com.codigozeroum.movieappxml.view.movie_details.MovieDetailsActivity
import br.com.codigozeroum.movieappxml.view.movies.MoviesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), HomeInterface {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bottomNavigationView = findViewById(R.id.bottom_nav)

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            var selectedFragment: Fragment? = null
            when (menuItem.itemId) {
                R.id.nav_home -> selectedFragment = MoviesFragment.newInstance(this)
                R.id.nav_favorite -> selectedFragment = FavoritesFragment.newInstance()
            }
            selectedFragment?.let {
                supportFragmentManager.beginTransaction().replace(R.id.frame_content, it).commit()
            }
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    override fun goToMovieDetails(movieId: Int){
        Log.d("HomeActivity", "goToMovieDetails")

        val intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("MOVIE_ID", movieId)
        startActivity(intent)
    }

}