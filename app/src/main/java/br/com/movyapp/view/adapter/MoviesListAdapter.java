package br.com.movyapp.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import br.com.movyapp.R;
import br.com.movyapp.domain.model.Movie;

public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<Movie> data;

    private Context context;

    private final int VIEW_ITEM = 0;

    private final int VIEW_LOADING = 1;

    private int visibleThreshold = 5;

    private int lastVisibleItem;

    private int totalItemCount;

    private int pageCount;

    private boolean isLoading;

    private List<Movie> filteredData;

    public MoviesListAdapter() {
    }

    public MoviesListAdapter(Context context, RecyclerView recyclerView, int page) {
        this.data = new ArrayList<>();
        this.filteredData = new ArrayList<>();
        this.context = context;
        this.pageCount = page;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (loadItemsListener != null) {
                        pageCount += 1;
                        loadItemsListener.loadItems(pageCount);
                    }
                    isLoading = true;
                }
            }
        });
    }

    public void setLoaded() {
        isLoading = false;
    }

    public void setData(List<Movie> data) {
        this.data.addAll(data);
        this.filteredData = this.data;
    }

    public RecyclerViewClickListener recyclerViewListClicked;

    public LoadItemsListener loadItemsListener;

    public void setRecyclerViewListClicked(final RecyclerViewClickListener recyclerViewListClicked) {
        this.recyclerViewListClicked = recyclerViewListClicked;
    }

    public void setLoadItemsListener(LoadItemsListener loadItemsListener) {
        this.loadItemsListener = loadItemsListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

            final ViewHolder viewHolder = new ViewHolder(view);

            viewHolder.movieTitle = (TextView) view.findViewById(R.id.txv_movie_title);
            viewHolder.movieDescription = (TextView) view.findViewById(R.id.txv_movie_description);
            viewHolder.moviePoster = (ImageView) view.findViewById(R.id.imv_movie_poster);
            viewHolder.movieItem = (CardView) view.findViewById(R.id.cdv_list_item_content);
            viewHolder.movieItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vw) {
                    Movie item = data.get(viewHolder.getAdapterPosition());
                    recyclerViewListClicked.recyclerViewListClicked(vw, viewHolder.getAdapterPosition(), item);
                }
            });

            return viewHolder;
        }

        if (viewType == VIEW_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_loading, parent, false);
            final LoadingViewHolder holder = new LoadingViewHolder(view);

            holder.progressBar = (ProgressBar) view.findViewById(R.id.pgb_movie_item);

            return holder;
        }

        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder itemHolder = (ViewHolder) holder;

            Movie item = data.get(position);
            itemHolder.movieTitle.setText(item.getTitle());
            itemHolder.movieDescription.setText(item.getOverview() != null
                    && !item.getOverview().isEmpty() ? item.getOverview()
                    : context.getText(R.string.no_description));

            if (item.getPosterPath() != null) {
                Picasso.with(context)
                        .load("https://image.tmdb.org/t/p/w300".concat(item.getPosterPath()))
                        .into(itemHolder.moviePoster);
            }
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString().toLowerCase();

                if (charString.isEmpty()) {
                    filteredData = data;
                } else {

                    List<Movie> filteredList = new ArrayList<>();

                    for (Movie movie : data) {

                        if (movie.getTitle().toLowerCase().contains(charString)) {
                            filteredList.add(movie);
                        }
                    }

                    filteredData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredData = (List<Movie>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView movieTitle;

        public TextView movieDescription;

        public ImageView moviePoster;

        public CardView movieItem;

        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public View view;

        public LoadingViewHolder(View view) {
            super(view);
            this.view = view;
        }

    }

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View vw, int position, Movie item);
    }

    public interface LoadItemsListener {
        void loadItems(int page);
    }

}
