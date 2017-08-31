package br.com.movyapp.application;

import android.app.Application;

import br.com.movyapp.application.injection.DaggerMovyAppComponent;
import br.com.movyapp.application.injection.MovyAppComponent;
import br.com.movyapp.application.injection.MovyAppModule;

public class MovyApplication extends Application {

    private static MovyAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = initDagger();
    }

    public static MovyAppComponent getAppComponent() {
        return appComponent;
    }

    protected MovyAppComponent initDagger() {
        return DaggerMovyAppComponent.builder().movyAppModule(new MovyAppModule(this)).build();
    }

}
