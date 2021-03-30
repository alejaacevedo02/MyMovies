package com.alejandraacevedo.mymovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alejandraacevedo.mymovies.databinding.ViewMovieItemBinding
import com.bumptech.glide.Glide

class MoviesAdapter(private val movies: List<Movie>, private val movieClickListener: MovieClickListener) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener{
            movieClickListener.onMovieClicked(movie)
        }
    }

    override fun getItemCount() = movies.size

    class ViewHolder(private val binding: ViewMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movieTitle.text = movie.title
            Glide.with(binding.root.context)
                    .load(movie.coverImage)
                    .into(binding.coverPhoto)
        }
    }
}

interface MovieClickListener{
    fun onMovieClicked(movie: Movie)
}