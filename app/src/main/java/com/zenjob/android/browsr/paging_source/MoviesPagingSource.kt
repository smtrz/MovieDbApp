package com.zenjob.android.browsr.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.repo.Repository
import okio.IOException
import retrofit2.HttpException

private const val TMDB_STARTING_PAGE_INDEX = 1


class MoviesPagingSource(
    private val repository: Repository
) : PagingSource<Int, Movie>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val pageIndex = params.key ?: TMDB_STARTING_PAGE_INDEX
        return try {
            val response = repository.getPaginatedMovies("en-US", pageIndex)
            val movies = response
            val nextKey =
                if (movies.results.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
//                    pageIndex + (params.loadSize / 25)
                    pageIndex + 1
                }
            LoadResult.Page(
                data = response.results,
                prevKey = if (pageIndex == TMDB_STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    /**
     * The refresh key is used for subsequent calls to PagingSource.Load after the initial load.
     */
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}