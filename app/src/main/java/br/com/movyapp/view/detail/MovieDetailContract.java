package br.com.movyapp.view.detail;

import android.content.Context;

import java.util.List;

import br.com.movyapp.domain.model.Movie;

/**
 * Contract to help define MVP pattern and handle view actions
 */
public interface MovieDetailContract {

    interface View {
        void onGenreLoadSuccess(final String genres);

        void onGenreLoadError();

        void showDialog();

        void closeDialog();

        Context getContext();
    }

    interface Presenter {
        void getMovieGenres(final Long id);
    }
}
