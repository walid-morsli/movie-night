package com.example.movienight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienight.Listeners.OnHomeListener;
import com.example.movienight.Models.Movie;
import com.example.movienight.Models.TopRatedMoviesApiResponse;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    RequestManager requestManager;
    TopRatedMoviesApiResponse movies;
    TextView movieTitle;
    ImageView movieImage;
    Button addButton;
    Button nextButton;
    Button listButton;
    DataBaseHelper db;
    Movie movie;
    Movie movieOffLine;
    Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieTitle = (TextView) findViewById(R.id.movie_name) ;
        movieImage = (ImageView) findViewById(R.id.movie_poster);
        addButton = (Button) findViewById(R.id.add_movie);
        nextButton = (Button) findViewById(R.id.next_movie);
        listButton = (Button) findViewById(R.id.list_movie);

        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                requestManager.getMovies(onHomeListener, random.nextInt(478));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                db = new DataBaseHelper(MainActivity.this);
                boolean result;

                if(movie != null)
                    result = db.addMovie(movie);
                else
                    result = db.addMovie(movieOffLine);

                if(result)
                    Toast.makeText(MainActivity.this, "Movie Added Successfully To Your List", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "This Movie is Already in Your List", Toast.LENGTH_SHORT).show();
            }
        });

        listButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                db = new DataBaseHelper(MainActivity.this);
                List<Movie> movies = db.getSavedMovies();
                if(movies.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Your List Is Empty", Toast.LENGTH_SHORT).show();
                }
                 else {
                    Intent myIntent = new Intent(MainActivity.this, FavouriteActivity.class);
                    startActivity(myIntent);
                }
            }
        });

        movieImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                if(movie != null){
                    intent.putExtra("title", movie.getTitle());
                    intent.putExtra("vote", String.valueOf(movie.getVote_average()));
                    intent.putExtra("year", movie.getRelease_date().substring(0,4));
                    intent.putExtra("image", movie.getPoster_path());
                    intent.putExtra("overview", movie.getOverview());
                }
                else {
                    intent.putExtra("title", movieOffLine.getTitle());
                    intent.putExtra("vote", String.valueOf(movieOffLine.getVote_average()));
                    intent.putExtra("year", movieOffLine.getRelease_date().substring(0,4));
                    intent.putExtra("image", "offline");
                    intent.putExtra("overview", movieOffLine.getOverview());
                }
                startActivity(intent);
            }
        });

        random = new Random();
        requestManager = new RequestManager(this);

        requestManager.getMovies(onHomeListener, random.nextInt(478));
    }

    private final OnHomeListener onHomeListener = new OnHomeListener() {
        @Override
        public void onResponse(TopRatedMoviesApiResponse response) {
            if(response == null){
                Toast.makeText(MainActivity.this, "No Data Available, Hit Next Again", Toast.LENGTH_SHORT).show();
                return;
            }
            showResult(response);
        }

        @Override
        public void onError(String message) {
            offLineMode();
        }
    };

    private void showResult(TopRatedMoviesApiResponse response) {
        movie = response.getResults().get(random.nextInt(20));
        movieTitle.setText(movie.getTitle());
        movieTitle.setSelected(true);
        Picasso.get()
                .load("https://image.tmdb.org/t/p/original"+movie.getPoster_path())
                .into(movieImage);

    }

    private void offLineMode() {
        movie = null;
        movieOffLine = new Movie();
        movieOffLine.setId(2034);
        movieOffLine.setOverview("Police drama about a veteran officer who escorts a rookie " +
                "on his first day with the LAPD's tough inner-city narcotics unit." +
                " \"Training Day\" is a blistering action drama that asks the audience " +
                "to decide what is necessary, what is heroic and what crosses the line " +
                "in the harrowing gray zone of fighting urban crime. Does law-abiding " +
                "law enforcement come at the expense of justice and public safety?");
        movieOffLine.setVote_average(7.5);
        movieOffLine.setTitle("Training Day");
        movieOffLine.setRelease_date("2001-11-07");
        movieTitle.setText(movieOffLine.getTitle());
        Picasso.get()
                .load(R.drawable.placehoolder)
                .into(movieImage);
        Toast.makeText(MainActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
    }

}