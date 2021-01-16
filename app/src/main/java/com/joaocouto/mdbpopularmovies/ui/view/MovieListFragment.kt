package com.joaocouto.mdbpopularmovies.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaocouto.mdbpopularmovies.R
import com.joaocouto.mdbpopularmovies.ui.viewmodel.MovieListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_movies.*

class MovieListFragment : Fragment(R.layout.fragment_movies) {

    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this).get(MovieListViewModel::class.java)
    }

    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        subscribeToList()
    }

    private fun setupUI() {

       rvMovies.layoutManager = GridLayoutManager(context, 3)
       rvMovies.addItemDecoration(
               DividerItemDecoration(
                       rvMovies.context,
                       (rvMovies.layoutManager as LinearLayoutManager).orientation
               )
       )

       rvMovies.adapter = adapter

    }

    private fun subscribeToList() {
        val disposable = viewModel.movieList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { list ->
                    adapter.submitList(list)
                },
                { e ->
                    Log.e("MDB_Log", "Error", e)
                }
            )
    }

}