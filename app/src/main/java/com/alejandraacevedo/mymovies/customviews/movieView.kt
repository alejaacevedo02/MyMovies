package com.alejandraacevedo.mymovies.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alejandraacevedo.mymovies.R

class movieView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val cover: ImageView
    private val title: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.view_movie_item, this, true)
        cover = view.findViewById(R.id.cover_photo)
        title = view.findViewById(R.id.movie_title)
    }
}