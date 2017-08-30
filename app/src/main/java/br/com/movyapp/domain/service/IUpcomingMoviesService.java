package br.com.movyapp.domain.service;

import br.com.movyapp.domain.model.MovieGenreList;
import br.com.movyapp.domain.model.MovieList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IUpcomingMoviesService {

    String API_KEY = "1f54bd990f1cdfb230adb312546d765d";

    @GET("movie/upcoming")
    Call<MovieList> getUpcomingMovies(@Query("api_key") String key, @Query("page") int page);

    @GET("movie/{movieId}")
    Call<MovieGenreList> getGenre(@Path("movieId") Long id, @Query("api_key") String key);
}
