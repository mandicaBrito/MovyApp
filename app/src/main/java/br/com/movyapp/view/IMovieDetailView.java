package br.com.movyapp.view;

public interface IMovieDetailView {

    void onGenreLoadSuccess(String genres);

    void onGenreLoadError();
}
