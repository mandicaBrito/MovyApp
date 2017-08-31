package br.com.movyapp.view.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import br.com.movyapp.R;
import br.com.movyapp.application.core.IntentAction;
import br.com.movyapp.domain.model.Movie;
import br.com.movyapp.view.adapter.MoviesListAdapter;
import br.com.movyapp.view.home.injection.DaggerMainComponent;
import br.com.movyapp.view.home.injection.MainModule;

public class MainActivity extends AppCompatActivity implements MainContract.View,
        MoviesListAdapter.RecyclerViewClickListener, MoviesListAdapter.LoadItemsListener,
        SearchView.OnQueryTextListener {

    public RecyclerView moviesList;

    private MoviesListAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    @Inject
    public MainPresenter presenter;

    private ProgressDialog dialog;

    private TextView noMoviesListTxv;

    private boolean isSearch = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerMainComponent.builder().mainModule(new MainModule(this)).build().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noMoviesListTxv = (TextView) findViewById(R.id.txv_no_data);
        moviesList = (RecyclerView) findViewById(R.id.rcv_movie_list);
        moviesList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        moviesList.setLayoutManager(layoutManager);

        adapter = new MoviesListAdapter(this.getApplicationContext(), moviesList, MainContract.View.INITIAL_PAGE);
        adapter.setRecyclerViewListClicked(this);
        adapter.setLoadItemsListener(this);
        moviesList.setAdapter(adapter);

        presenter.getMovies(MainContract.View.INITIAL_PAGE);

    }

    @Override
    public void recyclerViewListClicked(final View vw, final int position, final Movie item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("movieItem", item);

        Intent intent = new Intent(IntentAction.MOVIE_DETAILS.getAction());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem search = menu.findItem(R.id.search);

        MenuItemCompat.setOnActionExpandListener(search,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(final MenuItem menuItem) {
                        isSearch = true;
                        return true;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(final MenuItem menuItem) {
                        isSearch = false;
                        adapter.onLoadingComplete();
                        return true;
                    }
                });

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateMovieList(final List<Movie> changesList) {
        moviesList.setVisibility(View.VISIBLE);
        noMoviesListTxv.setVisibility(View.GONE);
        adapter.setData(changesList);
        adapter.notifyDataSetChanged();
        adapter.onLoadingComplete();
    }

    @Override
    public void onUpcomingMoviesError(final String message) {
        if (adapter.getItemCount() == 0) {
            moviesList.setVisibility(View.GONE);
            noMoviesListTxv.setVisibility(View.VISIBLE);
        }

        adapter.onLoadingComplete();
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
    public void loadItems(final int page) {
        if (!isSearch) {
            presenter.getMovies(page);
        }
    }

    @Override
    public boolean onQueryTextSubmit(final String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {
        adapter.getFilter().filter(newText);
        return true;
    }
}
