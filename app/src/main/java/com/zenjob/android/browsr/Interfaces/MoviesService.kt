package com.zenjob.android.browsr.Interfaces

import com.zenjob.android.browsr.Constants.WebServiceConstants
import com.zenjob.android.browsr.data.PaginatedListResponse
import com.zenjob.android.browsr.data.movie_details.MovieDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(WebServiceConstants.POPULAR_MOVIES)
    suspend fun getPopularTvShows(
        @Query("language") lang: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedListResponse>


    @GET(WebServiceConstants.MOVIE_DETAILS)
    suspend fun getDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") lang: String? = null
    ): Response<MovieDetails>


}