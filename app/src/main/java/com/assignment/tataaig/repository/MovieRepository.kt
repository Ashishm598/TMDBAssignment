package com.assignment.tataaig.repository

import com.assignment.tataaig.model.MovieDetailResponseModel
import com.assignment.tataaig.model.PopularMovieResponseModel
import com.assignment.tataaig.service.MovieService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieRepository(private val movieService: MovieService) : IMovieRepository {


    override fun getMoviesList(pageNo: Int): Observable<PopularMovieResponseModel> {
        return getMoviesListFromNetwork(pageNo)
    }

    override fun getMovieDetails(movieId: Int): Single<MovieDetailResponseModel> {
        return getMovieDetailsFromNetwork(movieId)
    }

    private fun getMoviesListFromNetwork(pageNo: Int): Observable<PopularMovieResponseModel> {
        return movieService.getPopularMovies(pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getMovieDetailsFromNetwork(movieId: Int): Single<MovieDetailResponseModel> {
        return movieService.getMovieDetailsById(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}