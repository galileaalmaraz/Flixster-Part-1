package com.codepath.flixterplus

import android.R
import android.graphics.Movie
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.flixter.adapters.MovieAdapter
import okhttp3.Headers
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var movies: MutableList<Movie>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rvMovies = findViewById<RecyclerView>(R.id.rvMovies)
        movies = ArrayList()

        // Create Adapter
        val movieAdapter = MovieAdapter(this, movies)

        // Set adapter on recycler View
        rvMovies.adapter = movieAdapter

        // Set a layout manager on recycler view
        rvMovies.layoutManager = LinearLayoutManager(this)
        val client = AsyncHttpClient()
        client.get(NOW_PLAYING_URL, object : JsonHttpResponseHandler() {
            fun onSuccess(i: Int, headers: com.sun.net.httpserver.Headers?, json: JSON) {
                Log.d(TAG, "onSuccess")
                val jsonObject: JSONObject = json.jsonObject
                try {
                    val results = jsonObject.getJSONArray("results")
                    Log.i(TAG, "Results: $results")
                    movies.addAll(Movie.fromJsonArray(results))
                    movieAdapter.notifyDataSetChanged()
                    Log.i(TAG, "Movies: " + movies.size)
                } catch (e: JSONException) {
                    Log.e(TAG, "Hit json exception", e)
                    e.printStackTrace()
                }
            }

            fun onFailure(
                i: Int,
                headers: com.sun.net.httpserver.Headers?,
                s: String?,
                throwable: Throwable?
            ) {
                Log.d(TAG, "onFailure")
            }
        })
    }

    companion object {
        const val NOW_PLAYING_URL =
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"
        const val TAG = "MainActivity"
    }
}