package com.alejandraacevedo.mymovies

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.alejandraacevedo.mymovies.databinding.ActivityMainBinding
import com.alejandraacevedo.mymovies.service.MovieDbClient
import timber.log.Timber
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val moviesAdapter = MoviesAdapter(emptyList()) { movie ->
            Toast
                    .makeText(this@MainActivity, movie.title, Toast.LENGTH_SHORT)
                    .show()
        }
        binding.moviesRv.adapter = moviesAdapter

        thread {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey)
            val body = popularMovies.execute().body()

            runOnUiThread{
                if (body != null) {
                    moviesAdapter.movies = body.results
                    moviesAdapter.notifyDataSetChanged()
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }
}