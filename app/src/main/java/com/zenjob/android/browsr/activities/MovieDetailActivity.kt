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
    private val moviewVM by viewModels<MovieViewModel>()
    lateinit var binding: MovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movie_detail)

        fetchMovieDetails()
        subscribeObservers()
    }


    fun fetchMovieDetails() {
        getMovieId()?.let { moviewVM.fetchMovieDetails(it, "en-US") }

    }

    fun getMovieId(): Long? {
        return getIntent().getExtras()?.getLong("movieId", 0);
    }

    fun subscribeObservers() {
        moviewVM.detailresponse.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {

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