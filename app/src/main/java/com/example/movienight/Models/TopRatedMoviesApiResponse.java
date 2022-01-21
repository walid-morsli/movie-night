package com.example.movienight.Models;

import java.util.List;

public class TopRatedMoviesApiResponse {

    int page = 0;
    List<Movie> results = null;
    int total_results = 0;
    int total_pages = 0;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> movies) {
        this.results = movies;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }
}
