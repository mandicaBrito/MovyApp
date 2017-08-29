package br.com.movyapp.service;

import br.com.movyapp.model.MovieGenreList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMoviesGenreService {

    String API_KEY = "1f54bd990f1cdfb230adb312546d765d";

    @GET("movie/{movieId}")
    Call<MovieGenreList> getGenre(@Path("movieId") Long id, @Query("api_key") String key);
}
