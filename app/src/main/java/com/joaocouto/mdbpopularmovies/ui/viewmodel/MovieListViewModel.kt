package com.joaocouto.mdbpopularmovies.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.joaocouto.mdbpopularmovies.data.api.TheMovieDB
import com.joaocouto.mdbpopularmovies.data.model.Movie
import com.joaocouto.mdbpopularmovies.data.paging.MoviePagingDataSourceFactory
import com.joaocouto.mdbpopularmovies.data.repository.MovieListRepository
import com.joaocouto.mdbpopularmovies.data.utils.Resource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieListViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private var mutableMovieList = MutableLiveData<Resource<PagedList<Movie>>>()

    private val pageSize = 20

    private val sourceFactory: MoviePagingDataSourceFactory

    init {
        sourceFactory = MoviePagingDataSourceFactory(MovieListRepository(TheMovieDB.getClient()), compositeDisposable)
        fetchMovieList()
    }

    private fun fetchMovieList(){

        mutableMovieList.postValue(Resource.loading(null))
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        compositeDisposable.add(
            RxPagedListBuilder(sourceFactory, config)
                .setFetchScheduler(Schedulers.io())
                .buildObservable()
                .cache()
                .subscribe({ list ->
                    mutableMovieList.postValue(Resource.success(list))
                    }, { throwable ->
                    mutableMovieList.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    fun getMovieList(): MutableLiveData<Resource<PagedList<Movie>>> {
        return mutableMovieList
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}