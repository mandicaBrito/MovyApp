package br.com.movyapp.presenter;

import br.com.movyapp.view.IMovieDetailView;

public interface IMovieDetailPresenter {

    void setView(final IMovieDetailView view);

    void getMovieGenres(Long id);
}
