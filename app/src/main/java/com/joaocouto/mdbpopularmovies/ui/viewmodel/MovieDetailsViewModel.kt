package com.joaocouto.mdbpopularmovies.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.joaocouto.mdbpopularmovies.data.model.MovieDetails
import com.joaocouto.mdbpopularmovies.data.repository.MovieDetailsRepository
import com.joaocouto.mdbpopularmovies.data.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsViewModel(private val movieDetailsRepository: MovieDetailsRepository, movieId: Int) : ViewModel() {

    private val movieDetails = MutableLiveData<Resource<MovieDetails>>()
    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    init {
        fetchMovieDetails(movieId)
    }

    private fun fetchMovieDetails(movieId: Int) {
        movieDetails.postValue(Resource.loading(null))
        compositeDisposable.add(
            movieDetailsRepository.getMovieDetails(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ details ->
                    movieDetails.postValue(Resource.success(details))
                }, { throwable ->
                    movieDetails.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    fun getMovieDetails(): LiveData<Resource<MovieDetails>> {
        return movieDetails
    }
}