package com.joaocouto.mdbpopularmovies.ui.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.joaocouto.mdbpopularmovies.R
import com.joaocouto.mdbpopularmovies.data.api.POSTER_BASE_URL
import com.joaocouto.mdbpopularmovies.data.api.TheMovieDB
import com.joaocouto.mdbpopularmovies.data.repository.MovieDetailsRepository
import com.joaocouto.mdbpopularmovies.data.utils.Status
import com.joaocouto.mdbpopularmovies.ui.viewmodel.MovieDetailsViewModel
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieDetailsFragment : Fragment(R.layout.fragment_movie) {

    private var movieId: Int = 0
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = arguments?.getInt("id")!!

        movieDetailsViewModel = MovieDetailsViewModel(MovieDetailsRepository(TheMovieDB.getClient()), movieId)

        setupObserver()
    }

    private fun setupObserver() {
        movieDetailsViewModel.getMovieDetails().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBarMovieDetails.visibility = View.GONE
                    it.data?.let { movieDetails ->

                        (activity as MainActivity).setActionBarTitle(movieDetails.title)

                        txtMovieTitle.text = movieDetails.title
                        txtMovieTagline.text = movieDetails.tagline
                        txtMovieDescription.text = movieDetails.overview

                        rbMovieAvaliation.rating = movieDetails.voteAverage.toFloat() / 2

                        val movieBannerUrl: String = POSTER_BASE_URL + movieDetails.backdropPath
                        Glide.with(this)
                            .load(movieBannerUrl)
                            .into(ivBanner)

                        val moviePosterUrl: String = POSTER_BASE_URL + movieDetails.posterPath
                        Glide.with(this)
                            .load(moviePosterUrl)
                            .into(ivPoster)

                    }
                    llMovieDetails.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBarMovieDetails.visibility = View.VISIBLE
                    llMovieDetails.visibility = View.GONE
                }
                Status.ERROR -> {
                    progressBarMovieDetails.visibility = View.GONE
                }
            }
        })
    }


}