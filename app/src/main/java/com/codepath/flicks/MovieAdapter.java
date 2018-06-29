package com.codepath.flicks;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.codepath.flicks.models.Config;
import com.codepath.flicks.models.MovieData;

import java.util.ArrayList;

//import com.bumptech.glide.Glide;

//import com.bumptech.glide.Glide;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    // list of movies
    ArrayList<MovieData> movies;
    // initialize with list
    //config needed for image urls
    Config config;
    Context context;

    public MovieAdapter(ArrayList<MovieData> movies) {
        this.movies = movies;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config){
        this.config = config;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the context and create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // create the view using the item_movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        // return a new ViewHolder
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the movie data at the specified position
        MovieData movie = movies.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());


        // build url for poster image
        String imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());

        // load image using glide
        GlideApp.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(holder.ivPosterImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }



    // create tge viewholder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // track view objects
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            ivPosterImage = (ImageView)itemView.findViewById(R.id.ivPosterImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }
}
