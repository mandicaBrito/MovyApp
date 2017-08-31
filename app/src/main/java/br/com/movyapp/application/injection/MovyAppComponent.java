package br.com.movyapp.application.injection;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MovyAppModule.class)
public interface MovyAppComponent {

}
