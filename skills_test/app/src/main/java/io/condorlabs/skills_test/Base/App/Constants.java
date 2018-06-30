package io.condorlabs.skills_test.Base.App;

import android.os.Environment;

/**
 * Created by Sebastian on 10/02/16.
 */
public class Constants {

    public interface MovieDBAPI {
        String BASE_URL = "https://api.themoviedb.org/3";
        String API_KEY = "api_key=d0c432f0720a1dd0588882b723ed5989";
        String IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    }

    public interface Application {
        String STORAGE_LIST = "Movies";
        String MOVIE_DONT_VIDEO = "Movie don't have video.";
        String PLEASE_WAIT = "Un momento por favor";
        String CHARGIN_MOVIE_LIST = "Charging movie list.";
    }

}


