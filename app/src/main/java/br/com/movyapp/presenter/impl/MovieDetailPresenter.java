package br.com.movyapp.presenter.impl;

import br.com.movyapp.model.MovieGenre;
import br.com.movyapp.model.MovieGenreList;
import br.com.movyapp.presenter.IMovieDetailPresenter;
import br.com.movyapp.service.IMoviesGenreService;
import br.com.movyapp.view.IMovieDetailView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailPresenter implements IMovieDetailPresenter, Callback<MovieGenreList> {

    private IMovieDetailView view;

    @Override
    public void setView(IMovieDetailView view) {
        this.view = view;
    }

    @Override
    public void getMovieGenres(Long id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IMoviesGenreService service = retrofit.create(IMoviesGenreService.class);
        Call<MovieGenreList> call = service.getGenre(id, IMoviesGenreService.API_KEY);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<MovieGenreList> call, Response<MovieGenreList> response) {
        if (response.isSuccessful()) {
            MovieGenreList result = response.body();

            String genre = "";
            for (MovieGenre res : result.getGenres()) {
                genre += res.getName().concat("; ");
            }

            view.onGenreLoadSuccess(genre);
        } else {
            view.onGenreLoadError();
        }
    }

    @Override
    public void onFailure(Call<MovieGenreList> call, Throwable t) {
        view.onGenreLoadError();
    }
}
