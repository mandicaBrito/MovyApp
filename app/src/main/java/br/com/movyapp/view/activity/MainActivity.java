package br.com.movyapp.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.movyapp.R;
import br.com.movyapp.adapter.MoviesListAdapter;
import br.com.movyapp.core.IntentAction;
import br.com.movyapp.model.Movie;
import br.com.movyapp.presenter.IMainPresenter;
import br.com.movyapp.presenter.impl.MainPresenter;
import br.com.movyapp.view.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView, MoviesListAdapter.RecyclerViewClickListener {

    private RecyclerView moviesList;

    private MoviesListAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    private IMainPresenter presenter;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        moviesList = (RecyclerView) findViewById(R.id.rcv_movie_list);
        moviesList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        moviesList.setLayoutManager(layoutManager);

        presenter = new MainPresenter();
        presenter.setView(this);
        presenter.getMovies();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void recyclerViewListClicked(View vw, int position, Movie item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("movieItem", item);

        Intent intent = new Intent(IntentAction.MOVIE_DETAILS.getAction());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void updateMovieList(List<Movie> changesList) {
        adapter = new MoviesListAdapter(changesList, this.getApplicationContext());
        adapter.setRecyclerViewListClicked(this);
        moviesList.setAdapter(adapter);
    }

    @Override
    public void onUpcomingMoviesError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading_movies));
        dialog.show();
    }

    @Override
    public void closeDialog() {
        if(dialog != null){
            dialog.cancel();
        }
    }
}
