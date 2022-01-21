package com.example.movienight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    TextView movieName;
    TextView movieVote;
    TextView movieYear;
    TextView movieOverview;
    ImageView movieImage;
    Button detailsBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        movieName = (TextView) findViewById(R.id.movie_name);
        movieVote = (TextView) findViewById(R.id.movie_vote);
        movieYear = (TextView) findViewById(R.id.movie_year);
        movieOverview = (TextView) findViewById(R.id.movie_overview);
        movieImage = (ImageView) findViewById(R.id.movie_image);

        movieName.setText(intent.getStringExtra("title"));
        movieVote.setText("Note: " + intent.getStringExtra("vote"));
        movieYear.setText("Year: " + intent.getStringExtra("year"));
        movieOverview.setText(intent.getStringExtra("overview"));
        movieOverview.setMovementMethod(new ScrollingMovementMethod());
        movieName.setSelected(true);

        String imagePath = intent.getStringExtra("image");
        if(imagePath.equals("offline")) {
            Picasso.get()
                    .load(R.drawable.placehoolder)
                    .into(movieImage);
        }
        else {
            Picasso.get()
                    .load("https://image.tmdb.org/t/p/original"+imagePath)
                    .into(movieImage);
        }

        detailsBack = (Button) findViewById(R.id.details_back);
        detailsBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}