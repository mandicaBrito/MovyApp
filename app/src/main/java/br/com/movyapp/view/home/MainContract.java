package br.com.movyapp.view.home;

import android.content.Context;

import java.util.List;

import br.com.movyapp.domain.model.Movie;

public interface MainContract {

    interface View {
        void updateMovieList(List<Movie> changesList);

        void onUpcomingMoviesError(String message);

        void showDialog();

        void closeDialog();

        Context getContext();
    }

    interface Presenter {
        void setView(final MainContract.View view);

        void getMovies(int page);
    }
}
