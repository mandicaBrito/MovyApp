package br.com.movyapp.view.detail;

import android.content.Context;

import java.util.List;

import br.com.movyapp.domain.model.Movie;

public interface MovieDetailContract {

    interface View {
        void onGenreLoadSuccess(String genres);

        void onGenreLoadError();

        void showDialog();

        void closeDialog();

        Context getContext();
    }

    interface Presenter {
        void setView(final MovieDetailContract.View view);

        void getMovieGenres(Long id);
    }
}
