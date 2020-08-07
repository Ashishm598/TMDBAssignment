package com.assignment.tataaig.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.assignment.tataaig.model.PopularMovieResponseModel

interface MainActivityContract {

    interface View {

    }

    interface ViewModel {
        fun getPopularMovies(pageNo: Int)
        fun getPopularMoviesResultsLD(): LiveData<List<PopularMovieResponseModel.Result>>
        fun getProgressStatusLD(): LiveData<Boolean>
        fun loadNextPage()
    }

}