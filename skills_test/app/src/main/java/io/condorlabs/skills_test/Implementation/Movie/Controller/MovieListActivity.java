package io.condorlabs.skills_test.Implementation.Movie.Controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

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

public class MovieListActivity extends BaseActivity implements IMovieResponseHandler {

    private Switch swShowHigerVote;
    private RecyclerView rvMovieListRecyclerView;
    private MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;
    private LinearLayoutManager mLayoutManager;
    public ProgressDialog mProgressDialog;
    private List<Movie> mMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        mLayoutManager = new LinearLayoutManager(MovieListActivity.this);
        mMoviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter();
        rvMovieListRecyclerView = (RecyclerView) findViewById(R.id.rvMovieList);
        swShowHigerVote = (Switch) findViewById(R.id.swShowHigerVote);
        rvMovieListRecyclerView.setLayoutManager(this.mLayoutManager);
        rvMovieListRecyclerView.setHasFixedSize(true);
        rvMovieListRecyclerView.setAdapter(this.mMoviesRecyclerViewAdapter);


        swShowHigerVote.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {

                if (bChecked) {
                    mMovieList = MovieRepository.getStorageMovieListVoteHiger();
                    showMovieList();
                } else {
                    getMovieList();
                }


            }
        });


        mProgressDialog = DialogsManager.createSimpleProgressDialog(
                Constants.Application.PLEASE_WAIT,
                Constants.Application.CHARGIN_MOVIE_LIST,
                MovieListActivity.this
        );


        getMovieList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getMovieList();
    }

    private void getMovieList() {

        swShowHigerVote.setChecked(false);

        mProgressDialog.show();
        MovieRepository.getMovieList(this);
    }

    private void showMovieList() {

        if (mMovieList.size() > 0) {
            mMoviesRecyclerViewAdapter.setMovieList(mMovieList);
            mMoviesRecyclerViewAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onAPIResponse(Error error, List<Movie> movies) {
        mProgressDialog.dismiss();
        mProgressDialog.cancel();
        mMovieList = movies;
        showMovieList();
    }
}
