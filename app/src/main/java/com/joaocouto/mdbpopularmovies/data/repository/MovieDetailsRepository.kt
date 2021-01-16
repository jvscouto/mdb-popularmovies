package com.joaocouto.mdbpopularmovies.data.repository

import com.joaocouto.mdbpopularmovies.data.api.TheMovieDBInterface
import com.joaocouto.mdbpopularmovies.data.model.MovieDetails
import io.reactivex.Single

class MovieDetailsRepository(private val theMovieDBInterface: TheMovieDBInterface){

    fun getMovieDetails(movieId: Int): Single<MovieDetails> {
        return theMovieDBInterface.getMovieDetails(movieId)
    }
}