package com.joaocouto.mdbpopularmovies.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.joaocouto.mdbpopularmovies.BuildConfig
import com.joaocouto.mdbpopularmovies.data.utils.Status
import com.joaocouto.mdbpopularmovies.databinding.FragmentMovieDetailsBinding
import com.joaocouto.mdbpopularmovies.ui.extension.loadImage
import com.joaocouto.mdbpopularmovies.ui.viewmodel.MovieDetailsViewModel

class MovieDetailsFragment : Fragment() {

    private var movieId: Int = 0
    private lateinit var movieDetailsViewModel: MovieDetailsViewModel

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movieId = arguments?.getInt("id")!!

        movieDetailsViewModel = MovieDetailsViewModel(movieId)

        setupObserver()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

    private fun setupObserver() {
        movieDetailsViewModel.getMovieDetails().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBarMovieDetails.visibility = View.GONE
                    it.data?.let { movieDetails ->

                        (activity as MainActivity).setActionBarTitle(movieDetails.title)

                        binding.txtMovieTitle.text = movieDetails.title
                        binding.txtMovieTagline.text = movieDetails.tagline
                        binding.txtMovieDescription.text = movieDetails.overview

                        binding.rbMovieAvaliation.rating = movieDetails.voteAverage.toFloat() / 2

                        val movieBannerUrl: String = BuildConfig.MdbImageBaseUrl + movieDetails.backdropPath
                        binding.ivBanner.loadImage(movieBannerUrl)

                        val moviePosterUrl: String = BuildConfig.MdbImageBaseUrl + movieDetails.posterPath
                        binding.ivPoster.loadImage(moviePosterUrl)

                    }
                    binding.llMovieDetails.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBarMovieDetails.visibility = View.VISIBLE
                    binding.llMovieDetails.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBarMovieDetails.visibility = View.GONE
                }
            }
        })
    }


}