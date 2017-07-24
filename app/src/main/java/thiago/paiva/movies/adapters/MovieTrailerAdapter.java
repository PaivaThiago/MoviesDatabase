package thiago.paiva.movies.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import thiago.paiva.movies.R;
import thiago.paiva.movies.objects.MovieTrailer;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieViewHolder> {

    private MovieTrailer[] movieTrailers = null;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_trailers, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieTrailer movieTrailer = movieTrailers[position];
        holder.trailerName.setText(movieTrailer.getName());
        holder.playTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, movieTrailer.getUri());
                v.getContext().startActivity(intent);
            }
        });
        holder.shareTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) v.getContext();
                Intent intent = ShareCompat.IntentBuilder
                        .from(activity)
                        .setType("text/plain")
                        .setChooserTitle(movieTrailer.getName())
                        .setText(movieTrailer.getUri().toString())
                        .createChooserIntent();
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (movieTrailers != null)
            return movieTrailers.length;
        else
            return 0;
    }

    public void setMovieTrailers(MovieTrailer[] moviesTrailers) {
        this.movieTrailers = moviesTrailers;
        this.notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movieTrailerNameTextView)
        TextView trailerName;
        @BindView(R.id.playTrailerImageButton)
        ImageButton playTrailer;
        @BindView(R.id.shareTrailerImageButton)
        ImageButton shareTrailer;

        MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
