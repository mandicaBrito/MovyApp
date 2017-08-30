package br.com.movyapp.view.detail;

import java.net.HttpURLConnection;

import br.com.movyapp.domain.model.MovieGenre;
import br.com.movyapp.domain.model.MovieGenreList;
import br.com.movyapp.domain.service.IUpcomingMoviesService;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieDetailPresenter implements MovieDetailContract.Presenter, Callback<MovieGenreList> {

    private MovieDetailContract.View view;

    private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 2;

    @Override
    public void setView(MovieDetailContract.View view) {
        this.view = view;
    }

    @Override
    public void getMovieGenres(Long id) {

        view.showDialog();

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.cache(
                new Cache(view.getContext().getCacheDir(), CACHE_SIZE_BYTES));
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        IUpcomingMoviesService service = retrofit.create(IUpcomingMoviesService.class);
        Call<MovieGenreList> call = service.getGenre(id, IUpcomingMoviesService.API_KEY);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<MovieGenreList> call, Response<MovieGenreList> response) {

        view.closeDialog();

        if (response.isSuccessful() &&
                response.raw().networkResponse() != null &&
                response.raw().networkResponse().code() ==
                        HttpURLConnection.HTTP_NOT_MODIFIED) {
            // not modified: returns the cache info.
            return;
        }

        if (response.isSuccessful()) {
            MovieGenreList result = response.body();

            String genre = "";
            for (MovieGenre res : result.getGenres()) {
                genre += res.getName().concat("; ");
            }

            view.onGenreLoadSuccess(genre);
        }
    }

    @Override
    public void onFailure(Call<MovieGenreList> call, Throwable t) {
        view.closeDialog();
        view.onGenreLoadError();
    }
}
