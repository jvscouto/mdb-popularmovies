package com.joaocouto.mdbpopularmovies.data.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.joaocouto.mdbpopularmovies.data.model.Movie
import com.joaocouto.mdbpopularmovies.data.repository.MovieListRepository
import io.reactivex.disposables.CompositeDisposable

class MoviePagingSource (
        private val movieListRepository: MovieListRepository,
        private val compositeDisposable: CompositeDisposable
) : PageKeyedDataSource<Int, Movie>(){

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        val numberOfItems = params.requestedLoadSize
        createObservable(1, 1, numberOfItems, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page + 1, numberOfItems, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
        createObservable(page, page - 1, numberOfItems, null, callback)
    }

    private fun createObservable(requestedPage: Int,
                                 adjacentPage: Int,
                                 requestedLoadSize: Int,
                                 initialCallback: LoadInitialCallback<Int, Movie>?,
                                 callback: LoadCallback<Int, Movie>?) {
        compositeDisposable.add(
            movieListRepository.getPopularMovies(requestedPage * requestedLoadSize)
                .subscribe(
                    { response ->
                        initialCallback?.onResult(response.movieList, null, adjacentPage)
                        callback?.onResult(response.movieList, adjacentPage)
                    },
                    { t ->
                        Log.d("MDB_Log", "Error loading page: $requestedPage", t)
                    }
                )
        )
    }

}