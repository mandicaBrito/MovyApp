package br.com.movyapp.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MovieGenreList implements Serializable {

    @SerializedName("genres")
    private List<MovieGenre> genres;

    public List<MovieGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<MovieGenre> genres) {
        this.genres = genres;
    }
}
