package com.example.movienight.Listeners;

import com.example.movienight.Models.TopRatedMoviesApiResponse;

public interface OnMovieClickListener {

    void onResponse(TopRatedMoviesApiResponse response);
    void onError(String message);

}
