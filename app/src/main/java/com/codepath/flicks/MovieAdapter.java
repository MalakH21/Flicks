package com.codepath.flicks;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flicks.models.Config;
import com.codepath.flicks.models.MovieData;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

//import com.bumptech.glide.Glide;

//import com.bumptech.glide.Glide;

//import com.bumptech.glide.Glide;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
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

        // determine the current orientation
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;


        // build url for poster image
        String imageUrl = null;
        // if in portrait mode, load the poster image
        if (isPortrait){
            imageUrl = config.getImageUrl(config.getPosterSize(), movie.getPosterPath());
        }else{
            // load the backdrop image
            imageUrl = config.getImageUrl(config.getBackdropSize(),movie.getBackdropPath());
        }

        // get the correct placeholder and imageview for the current orientation
        int placeholderId = isPortrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPortrait ? holder.ivPosterImage : holder.ivBackdropImage;

        // load image using glide
        GlideApp.with(context)
                .load(imageUrl)
                .transform(new RoundedCornersTransformation( 25, 0))
                .placeholder(placeholderId)
                .error(placeholderId)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }



    // create the viewholder as a static inner class
    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        // track view objects
        //ImageView IvPrimaryImage;

        ImageView ivPosterImage; // still need yes or no?
        ImageView ivBackdropImage; // still need yes or no?
        TextView tvTitle;
        TextView tvOverview;

        ImageView ivPrimaryImage;
        public ViewHolder(View itemView) {
            super(itemView);
            //ivPrimaryImage = (ImageView) itemView.findViewById(R.id.ivPrimaryImage);

            ivPosterImage = (ImageView)itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdropImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                MovieData movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(MovieData.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }


}
