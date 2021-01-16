package com.joaocouto.mdbpopularmovies.data.paging

import androidx.paging.DataSource
import com.joaocouto.mdbpopularmovies.data.model.Movie
import com.joaocouto.mdbpopularmovies.data.repository.MovieListRepository
import io.reactivex.disposables.CompositeDisposable

class MoviePagingDataSourceFactory (
        private val movieListRepository: MovieListRepository,
        private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, Movie>() {

    override fun create(): DataSource<Int, Movie> {
        return MoviePagingSource(movieListRepository, compositeDisposable)
    }
}