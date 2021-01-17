package com.joaocouto.mdbpopularmovies.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.joaocouto.mdbpopularmovies.BuildConfig
import com.joaocouto.mdbpopularmovies.R
import com.joaocouto.mdbpopularmovies.data.model.Movie
import com.joaocouto.mdbpopularmovies.databinding.MovieListItemBinding
import com.joaocouto.mdbpopularmovies.ui.extension.loadImage

class MovieListAdapter : PagedListAdapter<Movie, MovieListAdapter.MovieItemViewHolder>(movieDiff) {

    private var _binding: MovieListItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        _binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)


        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieItemViewHolder(private val viewBinding: MovieListItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(movie: Movie?) {
            viewBinding.txtMovieTitle.text = movie?.title
            viewBinding.txtMovieReleaseDate.text = movie?.releaseDate

            val moviePosterURL = BuildConfig.MdbImageBaseUrl + movie?.posterPath
            viewBinding.ivMoviePoster.loadImage(moviePosterURL)

            val bundle = Bundle()
            bundle.putInt("id", movie?.id!!)

            viewBinding.root.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_moviesFragment_to_movieFragment, bundle)
            }
        }
    }

    companion object {
        val movieDiff = object: DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(old: Movie, new: Movie): Boolean {
                return old.id == new.id

            }

            override fun areContentsTheSame(old: Movie, new: Movie): Boolean {
                return old == new
            }

        }
    }

}