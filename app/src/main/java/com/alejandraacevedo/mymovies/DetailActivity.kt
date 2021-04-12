package com.alejandraacevedo.mymovies

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.alejandraacevedo.mymovies.databinding.ActivityDetailBinding
import com.alejandraacevedo.mymovies.model.Movie
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        movie?.let {
            title = movie.title
            Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w780/${movie.backdrop_path}")
                    .into(binding.backdrop)
            binding.summaryDetail.text = movie.overview
            bindDetailInfo(binding.detailInfo, movie)
        }
    }

    private fun bindDetailInfo(detailInfo: TextView, movie: Movie) {
        detailInfo.text = buildSpannedString {
            bold { append("Original language : ") }
            appendLine(" ${movie.original_language}")
        }
    }
}