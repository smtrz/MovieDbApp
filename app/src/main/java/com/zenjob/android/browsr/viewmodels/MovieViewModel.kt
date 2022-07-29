package com.zenjob.android.browsr.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenjob.android.browsr.data.PaginatedListResponse
import com.zenjob.android.browsr.data.MovieDetails
import com.zenjob.android.browsr.generics.NetworkResult
import com.zenjob.android.browsr.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/** View Model for movies, fetches the movie and also the details for the movies. */
@HiltViewModel
class MovieViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    // Livedata getters and private variables
    private val _response: MutableLiveData<NetworkResult<PaginatedListResponse>> = MutableLiveData()

    val response: MutableLiveData<NetworkResult<PaginatedListResponse>>
        get() = _response

    private val _detailresponse: MutableLiveData<NetworkResult<MovieDetails>> = MutableLiveData()

    val detailresponse: MutableLiveData<NetworkResult<MovieDetails>>
        get() = _detailresponse

    /** Method to fetch popular movies, by pages and language */
    fun fetchPopularMovies(page: Int) {

        viewModelScope.launch {
            repository
                .getMovies("en-US", page)
                .onEach { values -> _response.postValue(values) }
                .collect()
        }
    }

    /** Method to fetch movie details from the server based on the id */
    fun fetchMovieDetails(movieId: Long, lang: String) {

        viewModelScope.launch {
            repository
                .getMovieDetails(movieId, "en-US")
                .onEach { values -> _detailresponse.postValue(values) }
                .collect()
        }
    }
}
