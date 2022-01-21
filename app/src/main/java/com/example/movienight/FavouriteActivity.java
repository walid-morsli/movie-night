package com.example.movienight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.movienight.Adapters.FavouriteListRecyclerAdapter;
import com.example.movienight.Models.Movie;

import java.util.List;

public class FavouriteActivity extends AppCompatActivity {
    FavouriteListRecyclerAdapter favouriteListRecyclerAdapter;
    RecyclerView favouriteListRecyclerView;
    Button favouriteBack;
    List<Movie> movies;
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        favouriteBack = (Button) findViewById(R.id.favourite_back);
        db = new DataBaseHelper(FavouriteActivity.this);
        movies = db.getSavedMovies();
        favouriteListRecyclerView = (RecyclerView) findViewById(R.id.favourite_list_recycler_view);
        favouriteListRecyclerView.setHasFixedSize(true);
        favouriteListRecyclerView.setLayoutManager(new GridLayoutManager(FavouriteActivity.this, 1));
        favouriteListRecyclerAdapter = new FavouriteListRecyclerAdapter(this, movies);
        favouriteListRecyclerView.setAdapter(favouriteListRecyclerAdapter);

        favouriteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}