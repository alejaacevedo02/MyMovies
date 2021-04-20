package com.alejandraacevedo.mymovies.activities

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.alejandraacevedo.mymovies.EXTRA_MOVIE
import com.alejandraacevedo.mymovies.R
import com.alejandraacevedo.mymovies.databinding.ActivityDetailBinding
import com.alejandraacevedo.mymovies.model.Movie
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbarDetails)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        movie?.let {
            title = movie.title
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w780/${movie.backdrop_path}")
                    .into(binding.backdrop)
            binding.summaryDetail.text = movie.overview
            bindDetailInfo(binding.detailInfo, movie)
        }
        binding.fab.setOnClickListener{
            Toast.makeText(this, "${movie?.title} is your fav <3", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindDetailInfo(detailInfo: TextView, movie: Movie) {
        detailInfo.text = buildSpannedString {
            appendInfo(R.string.original_language, movie.original_language)
            appendInfo(R.string.original_title, movie.original_title)
            appendInfo(R.string.release_date, movie.release_date)
            appendInfo(R.string.popularity, movie.popularity.toString())
            appendInfo(R.string.vote_average, movie.vote_average.toString())
        }
    }

    private fun SpannableStringBuilder.appendInfo(stringRes: Int, value: String) {
        bold {
            append(getString(stringRes))
            append(" : ")
        }
        appendLine(value)
    }
}