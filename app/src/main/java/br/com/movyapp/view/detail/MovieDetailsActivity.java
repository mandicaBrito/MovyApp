package br.com.movyapp.view.detail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.movyapp.R;
import br.com.movyapp.domain.model.Movie;

public class MovieDetailsActivity extends Activity implements MovieDetailContract.View {

    private ImageView moviePoster;

    private TextView movieTitle;

    private TextView movieDesc;

    private TextView movieReleaseDate;

    private TextView movieGenre;

    private MovieDetailContract.Presenter presenter;

    private Movie item;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(final @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_details);
        moviePoster = (ImageView) findViewById(R.id.imv_movie_detail_poster);
        movieTitle = (TextView) findViewById(R.id.txv_movie_detail_title);
        movieDesc = (TextView) findViewById(R.id.txv_movie_detail_description);
        movieReleaseDate = (TextView) findViewById(R.id.txv_movie_detail_release_date);
        movieGenre = (TextView) findViewById(R.id.txv_movie_detail_genre);

        presenter = new MovieDetailPresenter();
        presenter.setView(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            item = (Movie) extras.getSerializable("movieItem");
            presenter.getMovieGenres(item.getId());
        }

    }

    @Override
    public void showDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading));
        dialog.show();
    }

    public void onGenreLoadSuccess(final String genre) {
        setMovieContent(genre);
    }

    public void setMovieContent(String genre) {
        movieTitle.setText(item.getTitle());
        movieReleaseDate.setText(item.getReleaseDate());
        movieGenre.setText((genre != null && !genre.isEmpty()) ? genre : getText(R.string.no_description));

        movieDesc.setText(item.getOverview() != null && !item.getOverview().isEmpty() ? item.getOverview() : getText(R.string.no_description));

        if (item.getPosterPath() != null) {
            Picasso.with(this)
                    .load("https://image.tmdb.org/t/p/w500".concat(item.getPosterPath()))
                    .into(moviePoster);
        }
    }

    @Override
    public void onGenreLoadError() {
        setMovieContent(null);
    }

    @Override
    public void closeDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }
}
