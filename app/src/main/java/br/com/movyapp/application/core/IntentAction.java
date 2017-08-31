package br.com.movyapp.application.core;

/**
 * Intent action constants to help start viewa
 */
public enum IntentAction {

    MOVIE_DETAILS("movyapp.intent.action.activity.MOVIE_DETAILS");

    private String action;

    IntentAction(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
