package com.zenjob.android.browsr.data

data class Movie(
    val id: Long,
    val imdbId: String?,
    val overview: String?,
    val title: String,
    val original_title: String,
    val poster_path: String?,
    val release_date: String?,
    val vote_average: Float
)
