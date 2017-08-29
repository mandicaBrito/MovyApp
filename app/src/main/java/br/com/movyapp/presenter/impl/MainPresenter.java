package br.com.movyapp.presenter.impl;

import br.com.movyapp.model.Movie;
import br.com.movyapp.model.MovieGenre;
import br.com.movyapp.model.MovieGenreList;
import br.com.movyapp.model.MovieList;
import br.com.movyapp.presenter.IMainPresenter;
import br.com.movyapp.service.IMoviesGenreService;
import br.com.movyapp.service.IUpcomingMoviesService;
import br.com.movyapp.view.IMainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements IMainPresenter, Callback<MovieList> {

    private IMainView view;

    @Override
    public void setView(IMainView view) {
        this.view = view;
    }

    @Override
    public void getMovies() {

        view.showDialog();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IUpcomingMoviesService service = retrofit.create(IUpcomingMoviesService.class);
        Call<MovieList> call = service.getUpcomingMovies(IUpcomingMoviesService.API_KEY);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<MovieList> call, Response<MovieList> response) {

        view.closeDialog();

        if (response.isSuccessful()) {
            final MovieList result = response.body();

            // TODO chamar generos aqui e devolver ja no movie objetc e remover o da activity de detail
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl("https://api.themoviedb.org/3/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//            IMoviesGenreService service = retrofit.create(IMoviesGenreService.class);
//
//            for (final Movie movie : result.getMovies()) {
//                Call<MovieGenreList> callbck = service.getGenre(movie.getId(), IMoviesGenreService.API_KEY);
//                callbck.enqueue(new Callback<MovieGenreList>() {
//                    @Override
//                    public void onResponse(Call<MovieGenreList> callb, Response<MovieGenreList> response) {
//                        if (response.isSuccessful()) {
//                            MovieGenreList result = response.body();
//
//                            String genre = "";
//                            for (MovieGenre res : result.getGenres()) {
//                                genre += res.getName().concat("; ");
//                            }
//
//                            movie.setGenre(genre);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<MovieGenreList> call, Throwable t) {
//                        view.onUpcomingMoviesError(t.getMessage());
//                    }
//                });
//            }

            view.updateMovieList(result.getMovies());

        } else {
            view.onUpcomingMoviesError(response.message());
        }
    }

    @Override
    public void onFailure(Call<MovieList> call, Throwable t) {
        view.closeDialog();
        view.onUpcomingMoviesError(t.getMessage());
    }
}
