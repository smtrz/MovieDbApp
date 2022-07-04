package com.zenjob.android.browsr.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zenjob.android.browsr.data.PaginatedListResponse
import com.zenjob.android.browsr.data.movie_details.MovieDetails
import com.zenjob.android.browsr.generics.NetworkResult
import com.zenjob.android.browsr.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieViewModel @Inject constructor
    (
    private val repository: Repository
) : ViewModel() {

    init {

        Log.d("##", "View model created.")
    }

    private val _response: MutableLiveData<NetworkResult<PaginatedListResponse>> =
        MutableLiveData()

    val response: MutableLiveData<NetworkResult<PaginatedListResponse>> = _response
    private val _detailresponse: MutableLiveData<NetworkResult<MovieDetails>> =
        MutableLiveData()

    val detailresponse: MutableLiveData<NetworkResult<MovieDetails>> = _detailresponse


    fun fetchPopularMovies(page: Int) {
        viewModelScope.launch {
            repository.getMovies("en", page).collect { values ->
                _response.value = values

            }


        }

    }

    fun fetchMovieDetails(movieId: Long, lang: String) {
        viewModelScope.launch {
            repository.getMovieDetails(movieId, lang).collect { values ->
                _detailresponse.value = values

            }


        }

    }


}