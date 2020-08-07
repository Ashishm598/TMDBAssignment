package com.assignment.tataaig.model

data class PopularMovieResponseModel(
    var page: Int,
    var results: List<Result>,
    var total_pages: Int,
    var total_results: Int
) {
    data class Result(
        var adult: Boolean,
        var backdrop_path: String,
        var genre_ids: List<Int>,
        var id: Int,
        var original_language: String,
        var original_title: String,
        var overview: String,
        var popularity: Double,
        var poster_path: String,
        var release_date: String,
        var title: String,
        var video: Boolean,
        var vote_average: Double,
        var vote_count: Int
    )
}