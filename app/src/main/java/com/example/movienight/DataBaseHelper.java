package com.example.movienight;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.movienight.Models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String MOVIE_TABLE = "MOVIE_TABLE";
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String POSTER_PATH = "POSTER_PATH";
    public static final String RELEASE_DATE = "RELEASE_DATE";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "movie.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + MOVIE_TABLE
                + " (" + ID + " INTEGER PRIMARY KEY, "
                + TITLE + " TEXT, "
                + POSTER_PATH + " TEXT, "
                + RELEASE_DATE + " TEXT)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ID, movie.getId());
        cv.put(TITLE, movie.getTitle());
        cv.put(POSTER_PATH, movie.getPoster_path());
        cv.put(RELEASE_DATE, movie.getRelease_date());
        long insert = db.insert(MOVIE_TABLE, null, cv);
        if(insert == -1) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public List<Movie> getSavedMovies() {

        List<Movie> returnList = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + MOVIE_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {

            do{
                Movie movie = new Movie();
                movie.setId(cursor.getInt(0));
                movie.setTitle(cursor.getString(1));
                movie.setPoster_path(cursor.getString(2));
                movie.setRelease_date(cursor.getString(3));
                returnList.add(0, movie);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public boolean deleteMovie(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long insert = db.delete(MOVIE_TABLE, ID + "=" + id, null);
        if(insert == -1) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }
}
