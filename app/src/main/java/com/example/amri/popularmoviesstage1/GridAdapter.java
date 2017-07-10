package com.example.amri.popularmoviesstage1;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amri on 7/10/17.
 */

public class GridAdapter extends BaseAdapter {
    public LayoutInflater inflater;

    public Context context;

    public ArrayList<Movie> movieLists;
    public ArrayList<String> images;

    public GridAdapter(Context context, ArrayList<Movie> movieLists, ArrayList<String> images){
        this.context=context;
        this.movieLists=movieLists;
        this.images=images;
    }

    public GridAdapter(MainActivity context, ArrayList<Movie> movieLists, ArrayList<String> images) {
    }

    @Override
    public int getCount() { return MainActivity.movieLists.size();}

    @Override
    public Object getItem(int position) {return MainActivity.movieLists.get(position);}

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridLayout = convertView;

        if (convertView == null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridLayout=inflater.inflate(R.layout.movie_item,null);
        }

        TextView movieName=(TextView) gridLayout.findViewById(R.id.movieName);
        ImageView movieImage=(ImageView)gridLayout.findViewById(R.id.movieImage);

        Picasso.with(context).load(MainActivity.images.get(position)).into(movieImage);
        movieName.setText(MainActivity.movieLists.get(position).title);

        return gridLayout;
    }
}

