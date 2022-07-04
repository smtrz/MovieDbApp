package com.zenjob.android.browsr.data

import com.squareup.moshi.Json

data class PaginatedListResponse(
    val page: Int,
    @Json(name = "total_results") val totalResults: Int,
    val total_pages: Int,
    val results: List<Movie>
)
