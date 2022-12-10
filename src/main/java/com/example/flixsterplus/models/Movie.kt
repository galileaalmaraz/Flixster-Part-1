package com.example.flixsterplus.models
import android.os.Parcel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


@Parcel
class Movie {
    var movieId = 0
    private var backdropPath: String? = null
    private var posterPath: String? = null
    var title: String? = null
    var overview: String? = null
    var rating = 0.0

    //Empty constructor required for Parceler lib
    constructor() {}
    constructor(jsonObject: JSONObject) {
        backdropPath = jsonObject.getString("backdrop_path")
        posterPath = jsonObject.getString("poster_path")
        title = jsonObject.getString("title")
        overview = jsonObject.getString("overview")
        rating = jsonObject.getDouble("vote_average")
        movieId = jsonObject.getInt("id")
    }

    fun getPosterPath(): String {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath)
    }

    fun getBackdropPath(): String {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath)
    }

    companion object {
        @Throws(JSONException::class)
        fun fromJsonArray(movieJsonArray: JSONArray): List<Movie> {
            val movies: MutableList<Movie> = ArrayList()
            for (i in 0 until movieJsonArray.length()) {
                movies.add(Movie(movieJsonArray.getJSONObject(i)))
            }
            return movies
        }
    }
}