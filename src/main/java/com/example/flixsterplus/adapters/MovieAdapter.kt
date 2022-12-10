package com.example.flixsterplus.adapters

import android.R
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Movie
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flixsterplus.DetailActivity
import org.parceler.Parcels


class MovieAdapter {
}

import com.bumptech.glide.Glide
import com.codepath.flixter.DetailActivity
import com.codepath.flixter.R
import com.codepath.flixter.models.Movie
import org.parceler.Parcels


class MovieAdapter(var context: Context, var movies: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        Log.d("Movie Adapter", "onCreateViewHolder")
        val movieView: View =
            LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieAdapter.ViewHolder(movieView)
    }

    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
        Log.d("Movie Adapter", "onBindViewHolder $position")
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var container: RelativeLayout
        var tvTitle: TextView
        var tvOverview: TextView
        var ivPoster: ImageView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvOverview = itemView.findViewById(R.id.tvOverview)
            ivPoster = itemView.findViewById(R.id.ivPoster)
            container = itemView.findViewById(R.id.container)
        }

        fun bind(movie: Movie) {
            tvTitle.setText(movie.getTitle())
            tvOverview.setText(movie.getOverview())
            val imageURL: String
            if (context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath()
            } else {
                imageURL = movie.getPosterPath()
            }
            Glide.with(context).load(imageURL).into(ivPoster)
            container.setOnClickListener {
                val i = Intent(context, DetailActivity::class.java)
                i.putExtra("movie", Parcels.wrap(movie))
                context.startActivity(i)
            }
        }
    }
}