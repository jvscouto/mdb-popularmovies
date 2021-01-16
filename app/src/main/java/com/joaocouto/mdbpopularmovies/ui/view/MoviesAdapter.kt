package com.joaocouto.mdbpopularmovies.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.joaocouto.mdbpopularmovies.R
import com.joaocouto.mdbpopularmovies.data.api.POSTER_BASE_URL
import com.joaocouto.mdbpopularmovies.data.model.Movie
import kotlinx.android.synthetic.main.row_movie.view.*

class MoviesAdapter : PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder>(movieDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_movie, parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie?) {
            itemView.txtMovieTitle.text = movie?.title
            itemView.txtMovieReleaseDate.text = movie?.releaseDate

            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.ivMoviePoster.context)
                .load(moviePosterURL)
                .into(itemView.ivMoviePoster)

            val bundle = Bundle()
            bundle.putInt("id", movie?.id!!)

            itemView.setOnClickListener {
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