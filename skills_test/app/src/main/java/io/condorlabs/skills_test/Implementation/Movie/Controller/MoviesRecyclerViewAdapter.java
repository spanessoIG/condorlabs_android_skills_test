package io.condorlabs.skills_test.Implementation.Movie.Controller;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.condorlabs.skills_test.Base.App.Constants;
import io.condorlabs.skills_test.Base.App.MyApp;
import io.condorlabs.skills_test.Implementation.Movie.Model.Movie;
import io.condorlabs.skills_test.R;

/**
 * Created by sapanesso on 16/01/17.
 */

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie> mMovieList;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    MoviesRecyclerViewAdapter() {
        this.mMovieList = new ArrayList();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        CardView movieCardView;
        ImageView imgMoviePoster;
        TextView tvMovieTitle;
        TextView tvMovieAverage;

        public MovieViewHolder(View movieItem) {
            super(movieItem);
            movieCardView = (CardView) movieItem.findViewById(R.id.movieCardView);
            imgMoviePoster = (ImageView) movieItem.findViewById(R.id.imgMoviePoster);
            tvMovieTitle = (TextView) movieItem.findViewById(R.id.tvMovieTitle);
            tvMovieAverage = (TextView) movieItem.findViewById(R.id.tvMovieAverage);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        FooterViewHolder(View footerCardView) {
            super(footerCardView);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {

            View movieCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_movie_list_item, parent, false);
            MovieViewHolder movieViewHolder = new MovieViewHolder(movieCardView);
            return movieViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_list,parent,false);
            FooterViewHolder footerViewHolder = new FooterViewHolder(footerView);
            return footerViewHolder;
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MovieViewHolder) {
             final Movie movie = this.mMovieList.get(position);


            ((MovieViewHolder) holder).tvMovieTitle.setText(movie.getTitle());
            ((MovieViewHolder) holder).tvMovieAverage.setText("Vote average: "+movie.getVote_average());
            Picasso.with(MyApp.getContext()).load(Constants.MovieDBAPI.IMAGE_URL+movie.getPoster_path()).into(((MovieViewHolder) holder).imgMoviePoster);

            ((MovieViewHolder) holder).movieCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = new Intent(MyApp.getContext(),MovieDetailActivity.class);
                    intent.putExtra("movie",new Gson().toJson(movie));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApp.getContext().startActivity(intent);
                }
            });

            if(movie.isLiked()){
                ((MovieViewHolder) holder).movieCardView.setBackgroundColor(
                        MyApp.getContext().getResources().getColor(R.color.colorPrimary));
            }else{
                ((MovieViewHolder) holder).movieCardView.setBackgroundColor(
                        MyApp.getContext().getResources().getColor(R.color.cardview_light_background));
            }

        }


    }

    @Override
    public int getItemCount() {
        return mMovieList.size() > 0 ? mMovieList.size() + 1 : mMovieList.size();
    }

    public void setMovieList(List<Movie> movieList) {
        this.mMovieList = movieList;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    private boolean isPositionFooter(int position) {
        return position == mMovieList.size();
    }

}
