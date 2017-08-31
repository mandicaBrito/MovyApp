package br.com.movyapp.view.home.injection;

import br.com.movyapp.view.home.MainContract;
import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final MainContract.View view;

    public MainModule(MainContract.View view) {
        this.view = view;
    }

    @Provides
    public MainContract.View providesMainView() {
        return this.view;
    }

}
