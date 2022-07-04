package com.zenjob.android.browsr.repo

import com.zenjob.android.browsr.Interfaces.MoviesService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val movieService: MoviesService) {

    suspend fun getPopularMovies(lang: String, page: Int) =
        movieService.getPopularTvShows(lang, page)

    suspend fun getMovieDetails(movieId: Long, lang: String) =
        movieService.getDetails(movieId, lang)
}