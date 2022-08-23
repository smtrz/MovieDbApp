package com.zenjob.android.browsr.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.adapters.MoviesAdapterPaginated
import com.zenjob.android.browsr.adapters.OnItemClickListener
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.generics.NetworkResult
import com.zenjob.android.browsr.helpers.GeneralHelpers
import com.zenjob.android.browsr.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MovieListActivityPaginated :
    AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener, OnItemClickListener {
    // get view model instance via Hilt
    private val moviewVM by viewModels<MovieViewModel>()
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject
    lateinit var paginatedMovieAdapter: MoviesAdapterPaginated
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        // calling methods needed
        init()
        // observeDataChanges()
        fetchData(1)
    }

    /** Method takes the movie id and send it to the next activity via intent. */
    fun navigateToMovieDetail(movieId: Long) {
        val i = Intent(this, MovieDetailActivity::class.java)
        i.putExtra("movieId", movieId)
        startActivity(i)
    }

    /** initalizes the layout for recylerview,swipeview and the adapter. */
    private fun init() {

        refresh.setOnRefreshListener(this)
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        movie_list.layoutManager = mLayoutManager
        movie_list.adapter = paginatedMovieAdapter

    }

    /**
     * takes list of movies and assign it to the adapter and also setup the endless scroll endless
     * scroll is based on the EndlessRecyclerView class
     */
    private fun initMovieAdapater() {


        this.lifecycleScope.launch {
            moviewVM.Movies.collectLatest {
                paginatedMovieAdapter.submitData(it)

            }
        }
        paginatedMovieAdapter.addLoadStateListener { combinedLoadStates ->


            if (combinedLoadStates.refresh is LoadState.Loading || combinedLoadStates.append is LoadState.Loading) {
                txtLoadingFailed.visibility = View.GONE
                movie_list.visibility = View.VISIBLE
                GeneralHelpers.displayProgressBar(true, progressbar, this)
            } else {
                GeneralHelpers.displayProgressBar(false, progressbar, this)

                val errorState = when {
                    combinedLoadStates.append is LoadState.Error -> combinedLoadStates.append as LoadState.Error
                    combinedLoadStates.prepend is LoadState.Error -> combinedLoadStates.prepend as LoadState.Error
                    combinedLoadStates.refresh is LoadState.Error -> combinedLoadStates.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {

                    if(paginatedMovieAdapter.itemCount>0){
                        Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                    }else {
                        txtLoadingFailed.visibility = View.VISIBLE

                    }
                }
            }
        }

        paginatedMovieAdapter.setOnItemClickListener(this)

    }

    /** call the fetch movie method from the viewmodel */
    private fun fetchData(page: Int) {
        moviewVM.fetchPopularMovies(page)
        initMovieAdapater()
    }

    /** listen to the data changes for the list of movies. */
    private fun observeDataChanges() {
        moviewVM.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    GeneralHelpers.displayProgressBar(false, progressbar, this)
                    response.data?.let {
                        initMovieAdapater()
                    }
                }
                is NetworkResult.Error -> {
                    // show error message
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                    GeneralHelpers.displayProgressBar(true, progressbar, this)
                }
            }
        }
    }

    /** setup for pull to refresh */
    override fun onRefresh() {

       paginatedMovieAdapter.refresh()
        refresh.isRefreshing = false

    }

    override fun onItemClick(listitem: Movie, position: Int, view: View?) {

        navigateToMovieDetail(listitem.id)

    }

}
