package com.assignment.tataaig.app.network;

import com.assignment.tataaig.service.MovieService;

public class ServiceProvider {

    private static MovieService movieService;

    public static MovieService getMovieService() {
        if (movieService == null) {
            movieService = RetrofitBuilder.buildMainApiService(MovieService.class);
        }
        return movieService;
    }
}
