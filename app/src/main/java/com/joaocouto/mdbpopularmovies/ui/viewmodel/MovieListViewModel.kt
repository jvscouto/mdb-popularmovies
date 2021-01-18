package com.joaocouto.mdbpopularmovies.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.joaocouto.mdbpopularmovies.data.api.TheMovieDB
import com.joaocouto.mdbpopularmovies.data.model.Movie
import com.joaocouto.mdbpopularmovies.data.paging.MoviePagingDataSourceFactory
import com.joaocouto.mdbpopularmovies.data.repository.MovieListRepository
import io.reactivex.disposables.CompositeDisposable

class MovieListViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private lateinit var movieList: LiveData<PagedList<Movie>>

    private val pageSize = 20

    private val sourceFactory: MoviePagingDataSourceFactory

    init {
        sourceFactory = MoviePagingDataSourceFactory(MovieListRepository(TheMovieDB.getClient()), compositeDisposable)
        fetchMovieList()
    }

    private fun fetchMovieList(){

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        movieList = LivePagedListBuilder(sourceFactory, config).build()
    }

    fun getMovieList(): LiveData<PagedList<Movie>> {
        return movieList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}