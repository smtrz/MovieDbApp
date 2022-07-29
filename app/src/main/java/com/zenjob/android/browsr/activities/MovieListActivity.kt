package com.zenjob.android.browsr.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zenjob.android.browsr.R
import com.zenjob.android.browsr.adapters.MoviesAdapter
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.generics.BaseRecyclerViewAdapter
import com.zenjob.android.browsr.generics.NetworkResult
import com.zenjob.android.browsr.helpers.EndlessRecyclerViewScrollListener
import com.zenjob.android.browsr.helpers.GeneralHelpers
import com.zenjob.android.browsr.viewmodels.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_list.*

@AndroidEntryPoint
class MovieListActivity :
    AppCompatActivity(),
    SwipeRefreshLayout.OnRefreshListener,
    BaseRecyclerViewAdapter.OnItemClickListener<Movie> {
    // get view model instance via Hilt
    private val moviewVM by viewModels<MovieViewModel>()
    private var currentPage = 1
    var totalPages: Int = 0
    private var scrollListener: EndlessRecyclerViewScrollListener? = null
    lateinit var mLayoutManager: LinearLayoutManager

    @Inject lateinit var movieAdpater: MoviesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        // calling methods needed
        init()
        observeDataChanges()
        fetchData(currentPage)
    }

    /** Method takes the movie id and send it to the next activity via intent. */
    fun navigateToMovieDetail(movieId: Long) {
        val i = Intent(this, MovieDetailActivity::class.java)
        i.putExtra("movieId", movieId)
        startActivity(i)
    }

    /** initalizes the layout for recylerview,swipeview and the adapter. */
    private fun init() {
        movieAdpater.setOnItemClickListener(this)

        refresh.setOnRefreshListener(this)
        mLayoutManager = LinearLayoutManager(this)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        movie_list.layoutManager = mLayoutManager
        movie_list.adapter = movieAdpater
    }

    /**
     * takes list of movies and assign it to the adapter and also setup the endless scroll endless
     * scroll is based on the EndlessRecyclerView class
     */
    private fun initMovieAdapater(movies: List<Movie>?) {

        movies?.let { movieAdpater.addItems(it) }
        scrollListener =
            object : EndlessRecyclerViewScrollListener(mLayoutManager) {
                override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                    // Triggered only when new data needs to be appended to the list
                    currentPage += 1
                    if (currentPage < totalPages) {
                        fetchData(page = currentPage)
                    }
                }
            }

        // set up the scroll listener for endless scrolling
        movie_list.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
    }

    /** call the fetch movie method from the viewmodel */
    private fun fetchData(page: Int) {
        moviewVM.fetchPopularMovies(page)
    }

    /** listen to the data changes for the list of movies. */
    private fun observeDataChanges() {
        moviewVM.response.observe(this) { response ->
            when (response) {
                is NetworkResult.Success -> {

                    GeneralHelpers.displayProgressBar(false, progressbar, this)
                    response.data?.let {
                        totalPages = it.total_pages
                        initMovieAdapater(it.results)
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
        movieAdpater.clear()
        currentPage = 1
        fetchData(currentPage)
        refresh.isRefreshing = false
    }

    /** Interface implementation for item click of the list. */
    override fun onItemClick(listitem: Movie, position: Int, view: View?) {
        navigateToMovieDetail(listitem.id)
    }
}
