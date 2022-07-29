package com.zenjob.android.browsr.data

data class MovieDetails(

    val backdrop_path: String,
    val id: Int,
    val imdb_id: String,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val status: String,
    val tagline: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
)