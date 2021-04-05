package com.alejandraacevedo.mymovies.model

import com.google.gson.annotations.SerializedName

data class MovieDbResult(
        val page: Int,
        val results: List<MovieDb>,
        @SerializedName("total_pages")
        val totalPages: Int,
        @SerializedName("total_results")
        val totalResults: Int
)