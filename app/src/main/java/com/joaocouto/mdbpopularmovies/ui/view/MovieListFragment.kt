package com.joaocouto.mdbpopularmovies.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaocouto.mdbpopularmovies.databinding.FragmentMovieListBinding
import com.joaocouto.mdbpopularmovies.ui.viewmodel.MovieListViewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieListViewModel by lazy {
        ViewModelProvider(this).get(MovieListViewModel::class.java)
    }

    private val adapter: MovieListAdapter by lazy {
        MovieListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObserver()
    }

    private fun setupUI() {

        binding.rvMovies.layoutManager = GridLayoutManager(context, 3)
        binding.rvMovies.addItemDecoration(
               DividerItemDecoration(
                   binding.rvMovies.context,
                       (binding.rvMovies.layoutManager as LinearLayoutManager).orientation
               )
       )

        binding.rvMovies.adapter = adapter

    }

    private fun setupObserver(){
        viewModel.getMovieList().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }

}