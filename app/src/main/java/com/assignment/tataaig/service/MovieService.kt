package com.assignment.tataaig.service

import com.assignment.tataaig.constant.Constant
import com.assignment.tataaig.model.MovieDetailResponseModel
import com.assignment.tataaig.model.PopularMovieResponseModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") pageNo: Int): Observable<PopularMovieResponseModel>

    @GET("movie/{movie_id}")
    fun getMovieDetailsById(@Path("movie_id") movie_id: Int): Single<MovieDetailResponseModel>

}