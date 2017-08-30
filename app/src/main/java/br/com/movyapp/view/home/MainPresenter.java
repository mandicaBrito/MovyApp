package br.com.movyapp.view.home;

import java.net.HttpURLConnection;

import br.com.movyapp.domain.model.MovieList;
import br.com.movyapp.domain.service.IUpcomingMoviesService;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Presenter, Callback<MovieList> {

    private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 2;

    private MainContract.View view;

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void getMovies(int page) {

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
        Call<MovieList> call = service.getUpcomingMovies(IUpcomingMoviesService.API_KEY, page);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<MovieList> call, Response<MovieList> response) {

        view.closeDialog();

        if (response.isSuccessful() &&
                response.raw().networkResponse() != null &&
                response.raw().networkResponse().code() ==
                        HttpURLConnection.HTTP_NOT_MODIFIED) {
            // not modified: returns the cache info.
            return;
        }

        final MovieList result = response.body();
        view.updateMovieList(result.getMovies());

    }

    @Override
    public void onFailure(Call<MovieList> call, Throwable t) {
        view.closeDialog();
        view.onUpcomingMoviesError(t.getMessage());
    }
}
