package br.com.movyapp.view.detail.injection;


import br.com.movyapp.view.detail.MovieDetailContract;
import dagger.Module;
import dagger.Provides;

@Module
public class DetailModule {

    private final MovieDetailContract.View view;

    public DetailModule(MovieDetailContract.View view) {
        this.view = view;
    }

    @Provides
    public MovieDetailContract.View providesDetailView() {
        return this.view;
    }
}
