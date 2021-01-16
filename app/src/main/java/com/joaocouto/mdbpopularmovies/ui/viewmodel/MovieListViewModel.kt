package com.joaocouto.mdbpopularmovies.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.joaocouto.mdbpopularmovies.data.api.TheMovieDB
import com.joaocouto.mdbpopularmovies.data.paging.MoviePagingDataSourceFactory
import com.joaocouto.mdbpopularmovies.data.model.Movie
import com.joaocouto.mdbpopularmovies.data.repository.MovieListRepository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieListViewModel : ViewModel() {

    val movieList: Observable<PagedList<Movie>>
    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 20

    private val sourceFactory: MoviePagingDataSourceFactory

    init {
        sourceFactory = MoviePagingDataSourceFactory(MovieListRepository(TheMovieDB.getClient()), compositeDisposable)

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        movieList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}