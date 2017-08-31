package br.com.movyapp.view.home.injection;

import br.com.movyapp.view.home.MainActivity;
import dagger.Component;

@Component(modules = MainModule.class)
public interface MainComponent {

    void inject(MainActivity loginActivity);
}
