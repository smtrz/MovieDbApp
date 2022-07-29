package com.zenjob.android.browsr

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zenjob.android.browsr.data.Movie
import com.zenjob.android.browsr.data.MovieDetails
import com.zenjob.android.browsr.data.PaginatedListResponse
import com.zenjob.android.browsr.generics.NetworkResult
import com.zenjob.android.browsr.repo.RemoteDataSource
import com.zenjob.android.browsr.repo.Repository
import com.zenjob.android.browsr.viewmodels.MovieViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch


@ExperimentalCoroutinesApi

@RunWith(JUnit4::class)

class MovieViewModelTest {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var remoteDataSource: RemoteDataSource
    lateinit var repository: Repository

    //   lateinit var apiService: MoviesService
    lateinit var movieViewModel: MovieViewModel

    @Mock
    lateinit var movie_data_observer: Observer<NetworkResult<PaginatedListResponse>>

    //  @Mock
    lateinit var fake: FakeRemoteRepoImpl

    @Mock
    lateinit var movie_details_observer: Observer<NetworkResult<MovieDetails>>
    // var mockedList: List<*> = mock(MutableList::class.java)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        //movie_data_observer = Observer { }
        // movie_details_observer = Observer { }
        fake = FakeRemoteRepoImpl()
        remoteDataSource = RemoteDataSource(fake)
        repository = Repository(remoteDataSource)
        movieViewModel = MovieViewModel(repository)
        //  var remote = RemoteDataSource(movieService = apiService)
        //  repository = Repository(remote)

    }


    @Test
    fun fetchMovies() = runBlockingTest {

        movieViewModel.fetchPopularMovies(1)
        val countDownLatch = CountDownLatch(1)
        var movieList: List<Movie>? = null
        movie_data_observer = Observer<NetworkResult<PaginatedListResponse>> { datastate ->
            when (datastate) {
                is NetworkResult.Success<PaginatedListResponse> -> {
                    movieList = datastate.data?.results
                    countDownLatch.countDown()

                }
                is NetworkResult.Loading<*> -> {


                }
                is NetworkResult.Error<PaginatedListResponse> -> {
                    Assert.assertTrue(false)


                }
            }

        }
        movieViewModel.response.observeForever(movie_data_observer)
        countDownLatch.await()
        Assert.assertTrue(movieList?.size!! > 0)


    }

    @Test
    fun fetchMoviesDetails() = runBlockingTest {

        var movie: MovieDetails? = null
        movieViewModel.fetchMovieDetails(1000, "en-US")
        val countDownLatch = CountDownLatch(1)

        movie_details_observer = Observer<NetworkResult<MovieDetails>> { datastate ->
            when (datastate) {
                is NetworkResult.Success<MovieDetails> -> {
                    movie = datastate?.data!!
                    countDownLatch.countDown()


                }
                is NetworkResult.Loading<*> -> {


                }
                is NetworkResult.Error<MovieDetails> -> {
                    Assert.assertTrue(false)


                }
            }

        }
        movieViewModel.detailresponse.observeForever(movie_details_observer)
        countDownLatch.await()
        Assert.assertEquals(movie?.tagline, "this is some tag line")


    }

    @After
    fun tearDown() {
        movieViewModel.response.removeObserver(movie_data_observer)
        movieViewModel.detailresponse.removeObserver(movie_details_observer)
    }
}



