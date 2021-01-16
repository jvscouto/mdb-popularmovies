package com.joaocouto.mdbpopularmovies.data.repository

import com.joaocouto.mdbpopularmovies.data.api.TheMovieDBInterface
import com.joaocouto.mdbpopularmovies.data.model.MovieResponse
import io.reactivex.Single

class MovieListRepository(private val theMovieDBInterface: TheMovieDBInterface) {

    fun getPopularMovies(page: Int): Single<MovieResponse>{
        return theMovieDBInterface.getPopularMovie(page)
    }

}

