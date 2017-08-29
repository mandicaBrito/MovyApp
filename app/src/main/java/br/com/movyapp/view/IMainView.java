package br.com.movyapp.view;

import java.util.List;

import br.com.movyapp.model.Movie;

public interface IMainView {

    void updateMovieList(List<Movie> changesList);

    void onUpcomingMoviesError(String message);

    void showDialog();

    void closeDialog();
}
