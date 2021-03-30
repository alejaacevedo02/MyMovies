package com.alejandraacevedo.mymovies

import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.alejandraacevedo.mymovies.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.moviesRv.adapter = MoviesAdapter(listOf(
                Movie("Title 1", "https://loremflickr.com/320/240?lock=1"),
                Movie("Title 2", "https://loremflickr.com/320/240?lock=2"),
                Movie("Title 3", "https://loremflickr.com/320/240?lock=3"),
                Movie("Title 4", "https://loremflickr.com/320/240?lock=4"),
                Movie("Title 5", "https://loremflickr.com/320/240?lock=5"),
                Movie("Title 6", "https://loremflickr.com/320/240?lock=6")
        ))

    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }
}