package io.condorlabs.skills_test.Implementation.Movie.Repository;



import java.util.List;

import io.condorlabs.skills_test.Implementation.Movie.Model.Movie;
import io.condorlabs.skills_test.Utils.Models.Error;

/**
 * Created by sapanesso on 16/01/17.
 */

public interface IMovieResponseHandler {
    public void onAPIResponse(Error error, List<Movie> movieList);
}
