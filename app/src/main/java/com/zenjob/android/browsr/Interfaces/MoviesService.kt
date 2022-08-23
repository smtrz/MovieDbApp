package com.zenjob.android.browsr.Interfaces

import androidx.paging.PagingData
import com.zenjob.android.browsr.Constants.WebServiceConstants
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.data.PaginatedListResponse
import com.zenjob.android.browsr.data.MovieDetails
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesService {

    @GET(WebServiceConstants.POPULAR_MOVIES)
    suspend fun getPaginatedMovies(
        @Query("language") lang: String? = null,
        @Query("page") page: Int? = null
    ): PaginatedListResponse




    @GET(WebServiceConstants.POPULAR_MOVIES)
    suspend fun getPopularTvShows(
        @Query("language") lang: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedListResponse>

    fun getMovies(): Flow<PagingData<Movie>>

    @GET(WebServiceConstants.MOVIE_DETAILS)
    suspend fun getDetails(
        @Path("movie_id") movieId: Long,
        @Query("language") lang: String? = null
    ): Response<MovieDetails>


}