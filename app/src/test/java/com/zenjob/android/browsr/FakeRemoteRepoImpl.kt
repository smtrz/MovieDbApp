package com.zenjob.android.browsr

import com.zenjob.android.browsr.Interfaces.MoviesService
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.data.PaginatedListResponse
import com.zenjob.android.browsr.data.MovieDetails
import retrofit2.Response

class FakeRemoteRepoImpl() : MoviesService {
    override suspend fun getPopularTvShows(
        lang: String?,
        page: Int?
    ): Response<PaginatedListResponse> {
        var movie =
            Movie(
                1,
                "12",
                "this is a fake overview",
                "fake title",
                "fake path",
                "poster path",
                "12-05-2005", 4.6f
            )
        var lisofMovies = ArrayList<Movie>()
        lisofMovies.add(movie)
        return Response.success(PaginatedListResponse(1, 45, 2877, results = lisofMovies))
    }

    override suspend fun getDetails(movieId: Long, lang: String?): Response<MovieDetails> {

        return Response.success(
            MovieDetails(
                "",
                1,
                "1",
                "orignal title",
                "this is overview",
                "poster path",
                "12-02-2004",
                "released",
                "this is some tag line",
                "this is movie title",
                7.4,
                10
            )
        )
    }
}

