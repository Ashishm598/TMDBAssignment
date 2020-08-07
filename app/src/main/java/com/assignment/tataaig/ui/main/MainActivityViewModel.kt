package com.assignment.tataaig.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.TorrMonk.extension.log
import com.assignment.tataaig.app.network.ServiceProvider
import com.assignment.tataaig.model.MovieDetailResponseModel
import com.assignment.tataaig.model.PopularMovieResponseModel
import com.assignment.tataaig.repository.MovieRepository
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainActivityViewModel : ViewModel(), MainActivityContract.ViewModel {

    private val compositeDisposable: CompositeDisposable
    private val movieRepository: MovieRepository
    private val mutablePopularMovieListLD: MutableLiveData<List<PopularMovieResponseModel.Result>>
    private val mutableProgressStatusLD: MutableLiveData<Boolean>
    private var currentPage: PopularMovieResponseModel? = null

    companion object {
        const val STARTING_PAGE_INDEX = 1
    }

    init {
        mutableProgressStatusLD = MutableLiveData<Boolean>()
        compositeDisposable = CompositeDisposable()
        movieRepository = MovieRepository(ServiceProvider.getMovieService())
        mutablePopularMovieListLD = MutableLiveData()
        getPopularMovies(STARTING_PAGE_INDEX)
    }

    override fun getPopularMovies(pageNo: Int) {
        mutableProgressStatusLD.value = true
        movieRepository.getMoviesList(pageNo)
            .subscribe(object : Observer<PopularMovieResponseModel> {
                override fun onComplete() {
                    mutableProgressStatusLD.value = false
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(respose: PopularMovieResponseModel) {
                    currentPage = respose
                    mutablePopularMovieListLD.value = respose.results
                }

                override fun onError(error: Throwable) {
                    mutableProgressStatusLD.value = false
                    error.log("Rx")
                }
            })
    }

    override fun getPopularMoviesResultsLD(): LiveData<List<PopularMovieResponseModel.Result>> {
        return mutablePopularMovieListLD
    }

    override fun getProgressStatusLD(): LiveData<Boolean> {
        return mutableProgressStatusLD
    }

    override fun loadNextPage() {
        currentPage?.let { currentPage ->
            if (currentPage.page < currentPage.total_pages) {
                getPopularMovies(currentPage.page + 1)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}