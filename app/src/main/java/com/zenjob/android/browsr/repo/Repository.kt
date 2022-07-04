package com.zenjob.android.browsr.repo

import com.zenjob.android.browsr.data.PaginatedListResponse
import com.zenjob.android.browsr.data.movie_details.MovieDetails
import com.zenjob.android.browsr.generics.BaseApiResponse
import com.zenjob.android.browsr.generics.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : BaseApiResponse() {

    suspend fun getMovies(lang: String, page: Int): Flow<NetworkResult<PaginatedListResponse>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall { remoteDataSource.getPopularMovies(lang, page) })
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetails(movieId: Long, lang: String): Flow<NetworkResult<MovieDetails>> {
        return flow {
            emit(NetworkResult.Loading())
            emit(safeApiCall { remoteDataSource.getMovieDetails(movieId, lang) })
        }.flowOn(Dispatchers.IO)
    }


}