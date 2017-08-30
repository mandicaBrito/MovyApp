package br.com.movyapp.view.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.movyapp.R;
import br.com.movyapp.application.core.IntentAction;
import br.com.movyapp.domain.model.Movie;
import br.com.movyapp.view.adapter.MoviesListAdapter;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        MoviesListAdapter.RecyclerViewClickListener, MoviesListAdapter.LoadItemsListener {

    private RecyclerView moviesList;

    private MoviesListAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    private MainContract.Presenter presenter;

    private ProgressDialog dialog;

    private TextView noMoviesListTxv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noMoviesListTxv = (TextView) findViewById(R.id.txv_no_data);
        moviesList = (RecyclerView) findViewById(R.id.rcv_movie_list);
        moviesList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        moviesList.setLayoutManager(layoutManager);

        adapter = new MoviesListAdapter(this.getApplicationContext(), moviesList, 1);
        adapter.setRecyclerViewListClicked(this);
        adapter.setLoadItemsListener(this);
        moviesList.setAdapter(adapter);

        presenter = new MainPresenter();
        presenter.setView(this);
        presenter.getMovies(1);

    }

    @Override
    public void recyclerViewListClicked(View vw, int position, Movie item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("movieItem", item);

        Intent intent = new Intent(IntentAction.MOVIE_DETAILS.getAction());
        intent.putExtras(bundle);
        startActivity(intent);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//
//        MenuItem search = menu.findItem(R.id.search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
//        searchMovie(searchView);
//
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void searchMovie(SearchView searchView) {
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return true;
//            }
//        });
//    }

    @Override
    public void updateMovieList(List<Movie> changesList) {
        moviesList.setVisibility(View.VISIBLE);
        noMoviesListTxv.setVisibility(View.GONE);
        adapter.setData(changesList);
        adapter.notifyDataSetChanged();
        adapter.setLoaded();
    }

    @Override
    public void onUpcomingMoviesError(String message) {
        if (adapter.getItemCount() == 0) {
            moviesList.setVisibility(View.GONE);
            noMoviesListTxv.setVisibility(View.VISIBLE);
        }

        adapter.setLoaded();
        Toast.makeText(this, getString(R.string.loading_movies_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.loading_movies));
        dialog.show();
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

    @Override
    public void loadItems(int page) {
        presenter.getMovies(page);
    }
}
