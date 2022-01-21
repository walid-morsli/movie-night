package com.example.movienight;

import android.content.Context;
import android.widget.Toast;

import com.example.movienight.Listeners.OnHomeListener;
import com.example.movienight.Listeners.OnMovieClickListener;
import com.example.movienight.Models.TopRatedMoviesApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {

    String key = "7e2e712418c792514224baa30e012c7d";
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getMovies(OnHomeListener listener, int page) {
        MoviesService getMovies = retrofit.create(RequestManager.MoviesService.class);
        Call<TopRatedMoviesApiResponse> call = getMovies.getMovies(key, page);

        call.enqueue(new Callback<TopRatedMoviesApiResponse>() {
            @Override
            public void onResponse(Call<TopRatedMoviesApiResponse> call, Response<TopRatedMoviesApiResponse> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(context, "Couldn't fetch data, Hit Next Again", Toast.LENGTH_SHORT).show();
                    return;
                }
                listener.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<TopRatedMoviesApiResponse> call, Throwable t) {
                listener.onError(t.getMessage());
            }
        });
    }

    public interface MoviesService {
        @Headers({
                "Accept: application/json",
                "api_key: 7e2e712418c792514224baa30e012c7d"
        })
        @GET("top_rated")
        Call<TopRatedMoviesApiResponse> getMovies(
                @Query("api_key") String key,
                @Query("page") int page
        );
    }

}
