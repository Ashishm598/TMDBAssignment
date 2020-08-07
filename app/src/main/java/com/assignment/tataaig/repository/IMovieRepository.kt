package com.assignment.tataaig.repository

import com.assignment.tataaig.model.MovieDetailResponseModel
import com.assignment.tataaig.model.PopularMovieResponseModel
import io.reactivex.Observable
import io.reactivex.Single

interface IMovieRepository {

    fun getMoviesList(pageNo : Int) : Observable<PopularMovieResponseModel>
    fun getMovieDetails(movieId: Int): Single<MovieDetailResponseModel>

}