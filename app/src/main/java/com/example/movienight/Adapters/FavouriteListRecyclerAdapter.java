package com.example.movienight.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movienight.DataBaseHelper;
import com.example.movienight.MainActivity;
import com.example.movienight.Models.Movie;
import com.example.movienight.R;

import java.util.List;

public class FavouriteListRecyclerAdapter extends RecyclerView.Adapter<FavouriteViewHolder>{

    Context context;
    List<Movie> movies;

    public FavouriteListRecyclerAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteViewHolder(LayoutInflater.from(context).inflate(R.layout.favourite_movies_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.movieTitle.setSelected(true);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper db = new DataBaseHelper(context);
                if(db.deleteMovie(movies.get(holder.getAdapterPosition()).getId())){
                    Toast.makeText(context, "Movie Deleted Successfully From Your List", Toast.LENGTH_SHORT).show();
                    movies.remove(holder.getAdapterPosition());
                    if (movies.isEmpty())
                        Toast.makeText(context, "Your List Is Empty", Toast.LENGTH_SHORT).show();
                    notifyItemRemoved(holder.getAdapterPosition());
                }
                else {
                    Toast.makeText(context, "Failed To Delete This Movie From Your List", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}

class FavouriteViewHolder extends RecyclerView.ViewHolder {
    LinearLayout container;
    TextView movieTitle;
    Button deleteButton;

    public FavouriteViewHolder(@NonNull View itemView) {
        super(itemView);
        movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
        deleteButton = (Button) itemView.findViewById(R.id.delete_movie);
        container = (LinearLayout) itemView.findViewById(R.id.movies_container);
    }
}
