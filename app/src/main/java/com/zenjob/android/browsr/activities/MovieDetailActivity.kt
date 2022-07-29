package com.zenjob.android.browsr.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.databinding.MovieDetailBinding
import com.zenjob.android.browsr.generics.NetworkResult
import com.zenjob.android.browsr.helpers.GeneralHelpers
import com.zenjob.android.browsr.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.movie_detail.*

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {
    //get view model instance via Hilt
    private val moviewVM by viewModels<MovieViewModel>()
    lateinit var binding: MovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initialization for data binding
        binding = DataBindingUtil.setContentView(this, R.layout.movie_detail)
        // fetch the details of the selected movie
        fetchMovieDetails()
        // subscribe to the result of the movie
        subscribeObservers()
    }

    /**
     * call the fetch movie details method from the viewmodel
     */
    fun fetchMovieDetails() {
        getMovieId()?.let { moviewVM.fetchMovieDetails(it, "en-US") }

    }

    /**
     * Obtains the movie id via intent extras from the previous activity and returns it
     */
    fun getMovieId(): Long? {
        return getIntent().getExtras()?.getLong("movieId", 0);
    }

    /**
     * listen to the response of the server for movie details.
     */
    fun subscribeObservers() {
        moviewVM.detailresponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    // got the response , sets the data binding and also hides the progress bar
                    GeneralHelpers.displayProgressBar(false, progressbar, this)
                    response.data?.let {
                        binding.movieDetails = it
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                    Toast.makeText(
                        this,
                        response.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    GeneralHelpers.displayProgressBar(false, progressbar, this)

                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                    GeneralHelpers.displayProgressBar(true, progressbar, this)
                }
            }
        }

    }
}