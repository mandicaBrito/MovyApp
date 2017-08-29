package br.com.movyapp.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.movyapp.R;
import br.com.movyapp.model.Movie;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.ViewHolder> {

    private List<Movie> data;

    private Context context;

    public MoviesListAdapter() {

    }

    public MoviesListAdapter(List<Movie> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public RecyclerViewClickListener recyclerViewListClicked;

    public void setRecyclerViewListClicked(final RecyclerViewClickListener recyclerViewListClicked) {
        this.recyclerViewListClicked = recyclerViewListClicked;
    }

    @Override
    public MoviesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);

        viewHolder.movieTitle = (TextView) view.findViewById(R.id.txv_movie_title);
        viewHolder.movieDescription = (TextView) view.findViewById(R.id.txv_movie_description);
        viewHolder.moviePoster = (ImageView) view.findViewById(R.id.imv_movie_poster);
        viewHolder.movieItem = (CardView) view.findViewById(R.id.cdv_list_item_content);
        viewHolder.movieReleaseDate = (TextView) view.findViewById(R.id.txv_movie_release_date);
        viewHolder.movieGenre = (TextView) view.findViewById(R.id.txv_movie_genre);
        viewHolder.movieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                Movie item = data.get(viewHolder.getAdapterPosition());
                recyclerViewListClicked.recyclerViewListClicked(vw, viewHolder.getAdapterPosition(), item);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie item = data.get(position);

        holder.movieTitle.setText(item.getTitle());
        holder.movieReleaseDate.setText(item.getReleaseDate());

        holder.movieDescription.setText(item.getOverview() != null && !item.getOverview().isEmpty() ? item.getOverview() : context.getText(R.string.no_description));

        if (item.getPosterPath() != null) {
            Picasso.with(context)
                    .load("https://image.tmdb.org/t/p/w300".concat(item.getPosterPath()))
                    .into(holder.moviePoster);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView movieTitle;

        public TextView movieDescription;

        public ImageView moviePoster;

        public CardView movieItem;

        private TextView movieGenre;

        private TextView movieReleaseDate;

        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }

    }

    public interface RecyclerViewClickListener {
        void recyclerViewListClicked(View vw, int position, Movie item);
    }

}
