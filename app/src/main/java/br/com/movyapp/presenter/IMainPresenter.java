package br.com.movyapp.presenter;

import br.com.movyapp.view.IMainView;

public interface IMainPresenter {

    void setView(final IMainView view);

    void getMovies();
}
