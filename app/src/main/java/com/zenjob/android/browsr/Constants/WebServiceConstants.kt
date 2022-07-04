package com.zenjob.android.browsr.Constants

import com.zenjob.android.browsr.BuildConfig

class WebServiceConstants {
    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/movie/"
        const val POPULAR_MOVIES = "popular?api_key=" + BuildConfig.TMDB_API_KEY
        const val POSTER_PATH = "https://image.tmdb.org/t/p/w300/"
        const val MOVIE_DETAILS = "{movie_id}?api_key=" + BuildConfig.TMDB_API_KEY

    }
}