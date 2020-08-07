package com.assignment.tataaig.ui.detail

import androidx.lifecycle.LiveData
import com.assignment.tataaig.model.MovieDetailResponseModel

interface DetailActivityContract {

    interface View {

    }

    interface ViewModel {
        fun getMovieDetails(movieId: Int)
        fun getMovieResponseModelLD(): LiveData<MovieDetailResponseModel>
        fun getProgressStatusLD(): LiveData<Boolean>
    }

}