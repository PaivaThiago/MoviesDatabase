package thiago.paiva.movies.adapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import thiago.paiva.movies.R;
import thiago.paiva.movies.activities.MovieActivity;
import thiago.paiva.movies.objects.MovieDetails;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private MovieDetails[] movies = null;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_banner, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieDetails movie = movies[position];

        Uri url = Uri.parse("http://image.tmdb.org/t/p/w185/" + movie.getPoster());
        Picasso.with(holder.poster.getContext())
                .load(url)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_loading)
                .into(holder.poster);
        holder.poster.setContentDescription(movie.getTitle());
        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), MovieActivity.class);
                it.putExtra("movie", movie);
                v.getContext().startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movies != null)
            return movies.length;
        else
            return 0;
    }

    public void setMovies(MovieDetails[] movies) {
        this.movies = movies;
        this.notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_banner_ImageView)
        ImageView poster;

        MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
