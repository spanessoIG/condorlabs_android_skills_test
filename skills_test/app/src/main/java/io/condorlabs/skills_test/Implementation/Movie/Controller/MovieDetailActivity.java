package io.condorlabs.skills_test.Implementation.Movie.Controller;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import android.widget.CompoundButton;

import java.util.List;

import io.condorlabs.skills_test.Base.App.BaseActivity;
import io.condorlabs.skills_test.Base.App.Constants;
import io.condorlabs.skills_test.Base.App.MyApp;
import io.condorlabs.skills_test.Implementation.Movie.Model.Movie;
import io.condorlabs.skills_test.Implementation.Movie.Repository.IMovieResponseHandler;
import io.condorlabs.skills_test.Implementation.Movie.Repository.MovieRepository;
import io.condorlabs.skills_test.R;
import io.condorlabs.skills_test.Utils.DialogsManager;
import io.condorlabs.skills_test.Utils.Models.Error;

public class MovieDetailActivity extends BaseActivity {

    public ProgressDialog mProgressDialog;
    public ImageView imgMoviePoster;
    public Switch switchLike;
    public TextView tvMovieTitle;
    public TextView tvMovieDate;
    public TextView tvMovieOverview;
    public TextView tvMovieRate;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imgMoviePoster = (ImageView) findViewById(R.id.imgMoviePoster);
        switchLike = (Switch) findViewById(R.id.switchLike);
        tvMovieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        tvMovieDate = (TextView) findViewById(R.id.tvMovieDate);
        tvMovieOverview = (TextView) findViewById(R.id.tvMovieOverview);
        tvMovieRate = (TextView) findViewById(R.id.tvMovieRate);


        switchLike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                MovieRepository.changeLikeMovie(mMovie.getId(), bChecked);
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String movieJson = extras.getString("movie");

            mMovie = new Gson().fromJson(movieJson, Movie.class);
            showMovieData();

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void showMovieData() {
        Picasso.with(MyApp.getContext()).load(Constants.MovieDBAPI.IMAGE_URL + mMovie.getPoster_path()).into(imgMoviePoster);
        tvMovieTitle.setText(mMovie.getTitle());
        tvMovieDate.setText(mMovie.getRelease_date());
        tvMovieOverview.setText(mMovie.getOverview());
        tvMovieRate.setText(""+mMovie.getVote_count());

        if (mMovie.isVideo()) {
            Toast.makeText(MovieDetailActivity.this, Constants.Application.MOVIE_DONT_VIDEO,
                    Toast.LENGTH_LONG).show();
        }
        switchLike.setChecked(mMovie.isLiked());
    }


}
