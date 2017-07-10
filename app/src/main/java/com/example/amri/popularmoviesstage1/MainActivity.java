package com.example.amri.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    static public ArrayList<com.example.amri.popularmoviesstage1.Movie> movieLists;
    static public ArrayList<String> images;
    public String mostPopular="http://api.themoviedb.org/3/movie/popular?api_key=8960a5c80c53d999db8b57d83896a9fc";
    public String highRated ="http://api.themoviedb.org/3/movie/top_rated?api_key=8960a5c80c53d999db8b57d83896a9fc";
    static public GridView gridView;
    static public GridAdapter gridAdapter;
    public static boolean connectionEnabled;
    public Context currentContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String result="";
        movieLists = new ArrayList<>();
        images = new ArrayList<>();
        currentContext = getApplicationContext();

        if (isNetworkAvailable() !=false) {
            connectionEnabled=true;
            getJsonData(0);
            new FetchMovies(currentContext);
            gridView =(GridView) findViewById(R.id.moviesGridView);
            gridAdapter = new GridAdapter(MainActivity.this, movieLists,images);
            gridView.setAdapter(gridAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent movieIntent=new Intent(getApplicationContext(),MovieActivity.class);
                    Log.i("Default position ", String.valueOf(position));
                    movieIntent.putExtra("position",position);
                    startActivity(movieIntent);
                }
            });
        }
        else{
            Toast.makeText(this, "Network Is Not Available", Toast.LENGTH_LONG).show();
            connectionEnabled=false;
        }

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



    public void getJsonData(int searchBy){
        FetchMovies downloadTask=new FetchMovies(currentContext);
        try {
            if (searchBy == 0 ){
                downloadTask.execute(mostPopular);
            }
            else if(searchBy == 1){
                downloadTask.execute(highRated);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.mostpopular_button) {
            if (isNetworkAvailable()!= false) {
                new FetchMovies(currentContext).execute(mostPopular);
                gridAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(this, "Network Is Not Available", Toast.LENGTH_LONG).show();
            }
        }
        else if (id== R.id.highrated_button){
            if (isNetworkAvailable()!= false) {
                new FetchMovies(currentContext).execute(highRated);
                gridAdapter.notifyDataSetChanged();
            }
            else{
                Toast.makeText(this, "Network Is Not Available", Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

}
