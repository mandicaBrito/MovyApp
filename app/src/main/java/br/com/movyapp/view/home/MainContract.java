package br.com.movyapp.view.home;

import android.content.Context;

import java.util.List;

import br.com.movyapp.domain.model.Movie;

/**
 * Main contract to help define MVP pattern and handle view actions
 */
public interface MainContract {

    interface View {

        int INITIAL_PAGE = 1;

        void updateMovieList(List<Movie> changesList);

        void onUpcomingMoviesError(String message);

        void showDialog();

        void closeDialog();

        Context getContext();
    }

    interface Presenter {
        void getMovies(int page);
    }
}
