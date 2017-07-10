package com.example.amri.popularmoviesstage1;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by amri on 7/10/17.
 */

public class FetchMovies extends AsyncTask<String, Void, String> {

    public Context currentConext;

    public FetchMovies(Context context){
        currentConext=context;
    }


    protected String doInBackground(String... URLS) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(URLS[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        //update UI, processing Data
        super.onPostExecute(result);

        if (MainActivity.connectionEnabled) {
            MainActivity.movieLists = new ArrayList<>();
            MainActivity.images = new ArrayList<>();

            try {
                JSONObject moviesObject = new JSONObject(result);
                JSONArray moviesArray = moviesObject.getJSONArray("results");
                final int numberOfMovies = moviesArray.length();

                for (int i = 0; i < numberOfMovies; i++) {
                    JSONObject movie = moviesArray.getJSONObject(i);
                    Movie movieItem = new Movie();
                    movieItem.setTitle(movie.getString("title"));
                    movieItem.setId(movie.getInt("id"));
                    movieItem.setBackdrop_path(movie.getString("backdrop_path"));
                    movieItem.setOriginal_title(movie.getString("original_title"));
                    movieItem.setOriginal_language(movie.getString("original_language"));
                    if (movie.getString("overview") == "null") {
                        movieItem.setOverview("No Overview was Found");
                    } else {
                        movieItem.setOverview(movie.getString("overview"));
                    }
                    if (movie.getString("release_date") == "null") {
                        movieItem.setRelease_date("Unknown Release Date");
                    } else {
                        movieItem.setRelease_date(movie.getString("release_date"));
                    }
                    movieItem.setPopularity(movie.getString("popularity"));
                    movieItem.setVote_average(movie.getString("vote_average"));
                    movieItem.setPoster_path(movie.getString("poster_path"));
                    if (movie.getString("poster_path") == "null") {
                        MainActivity.images.add(APIString.IMAGE_NOT_FOUND);
                        movieItem.setPoster_path(APIString.IMAGE_NOT_FOUND);
                    } else {
                        MainActivity.images.add(APIString.IMAGE_URL + APIString.IMAGE_SIZE_185 + movie.getString("poster_path"));
                    }
                    MainActivity.movieLists.add(movieItem);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            MainActivity.gridAdapter.notifyDataSetChanged();
        }
        else{

            Toast.makeText(currentConext, "Network Is Not Available", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }
}