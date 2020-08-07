package com.assignment.tataaig.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.TorrMonk.extension.log
import com.assignment.tataaig.app.network.ServiceProvider
import com.assignment.tataaig.model.MovieDetailResponseModel
import com.assignment.tataaig.repository.MovieRepository
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DetailActivityViewModel : ViewModel(), DetailActivityContract.ViewModel {

    private val compositeDisposable = CompositeDisposable()
    private val movieService = ServiceProvider.getMovieService()
    private val movieDetailsRepository = MovieRepository(movieService)
    private val mutableMovieDetailResponseModelLD = MutableLiveData<MovieDetailResponseModel>()
    private val mutableProgressStatusLD = MutableLiveData<Boolean>()


    override fun getMovieDetails(movieId: Int) {
        if (movieId > 0) {
            mutableProgressStatusLD.value = true
            movieDetailsRepository.getMovieDetails(movieId)
                .subscribe(object : SingleObserver<MovieDetailResponseModel> {
                    override fun onSuccess(responseModel: MovieDetailResponseModel) {
                        mutableProgressStatusLD.value = false
                        mutableMovieDetailResponseModelLD.value = responseModel
                    }

                    override fun onSubscribe(d: Disposable) {
                        compositeDisposable.add(d)
                    }

                    override fun onError(e: Throwable) {
                        mutableProgressStatusLD.value = false
                        e.log()
                    }
                })
        } else {
            // sweet alert msg
        }

    }

    override fun getMovieResponseModelLD(): LiveData<MovieDetailResponseModel> {
        return mutableMovieDetailResponseModelLD
    }

    override fun getProgressStatusLD(): LiveData<Boolean> {
        return mutableProgressStatusLD
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }


}