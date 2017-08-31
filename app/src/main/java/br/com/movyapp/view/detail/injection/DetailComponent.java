package br.com.movyapp.view.detail.injection;

import br.com.movyapp.view.detail.MovieDetailsActivity;
import dagger.Component;

@Component(modules = DetailModule.class)
public interface DetailComponent {

    void inject(MovieDetailsActivity target);
}
