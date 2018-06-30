package io.condorlabs.skills_test.Implementation.Movie.Repository;

import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.condorlabs.skills_test.Base.App.Constants;
import io.condorlabs.skills_test.Base.App.MyApp;
import io.condorlabs.skills_test.Implementation.Movie.Model.Movie;
import io.condorlabs.skills_test.Utils.Models.Error;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by sapanesso on 16/01/17.
 */

public class MovieRepository {
    public static final String PREFS_NAME = "MyApp_Settings";


    public static void getMovieList(final IMovieResponseHandler handler) {
        if (!isSharePreferencesFull()) {
            List<Movie> movies = getStorageMovieList();
            handler.onAPIResponse(null, movies);
        } else {
            getMoviePopularListAPI(handler);
        }
    }

    public static void getMoviePopularListAPI(final IMovieResponseHandler handler) {

        String url = Constants.MovieDBAPI.BASE_URL + "/discover/movie?" + Constants.MovieDBAPI.API_KEY + "&sort_by=popularity.desc&include_video=true&page=90";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String results = jsonObj.getString("results");

                            saveMovieList(results);

                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<Movie>>() {
                            }.getType();
                            ArrayList<Movie> movies = gson.fromJson(results, listType);
                            handler.onAPIResponse(null, movies);

                        } catch (JSONException e) {
                            Error responseError = new Error("400", "Error al traer lista de películas.");
                            handler.onAPIResponse(responseError, null);
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Error responseError = new Error("400", "Error de conexión");
                        handler.onAPIResponse(responseError, null);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(MyApp.getContext());
        requestQueue.add(stringRequest);

    }

    public static boolean isSharePreferencesFull() {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0);
        String storageList = settings.getString(Constants.Application.STORAGE_LIST, "");
        return storageList.isEmpty();

    }

    public static void saveMovieList(String json) {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0);

        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constants.Application.STORAGE_LIST, json);
        editor.commit();
    }


    public static List<Movie> getStorageMovieList() {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0);
        String storageList = settings.getString(Constants.Application.STORAGE_LIST, "");

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Movie>>() {
        }.getType();
        ArrayList<Movie> movies = gson.fromJson(storageList, listType);


        return movies;
    }

    public static List<Movie> getStorageMovieListVoteHiger() {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0);
        String storageList = settings.getString(Constants.Application.STORAGE_LIST, "");

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Movie>>() {
        }.getType();
        ArrayList<Movie> movies = gson.fromJson(storageList, listType);



        ArrayList<Movie> higerMoview = new ArrayList<Movie>();

        for (Movie movie : movies) {
            if (movie.getVote_count() >= 2000) {
                higerMoview.add(movie);
            }
        }

        return higerMoview;
    }

    public static void changeLikeMovie(int id, boolean like) {
        SharedPreferences settings = MyApp.getContext().getSharedPreferences(PREFS_NAME, 0);
        String storageList = settings.getString(Constants.Application.STORAGE_LIST, "");

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Movie>>() {
        }.getType();
        ArrayList<Movie> movies = gson.fromJson(storageList, listType);

        for (Movie movie : movies) {
            if (movie.getId() == id) {
                movie.setLiked(like);
            }
        }

        String json = new Gson().toJson(movies);
        saveMovieList(json);


    }

}
