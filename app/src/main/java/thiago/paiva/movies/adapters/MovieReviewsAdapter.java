package thiago.paiva.movies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import thiago.paiva.movies.R;
import thiago.paiva.movies.objects.MovieReview;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieViewHolder> {

    private MovieReview[] movieReviews = null;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_reviews, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        final MovieReview movieReview = movieReviews[position];
        holder.reviewContent.setText(movieReview.getContent());
        holder.reviewAuthor.setText(movieReview.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (movieReviews != null)
            return movieReviews.length;
        else
            return 0;
    }

    public void setMovieReviews(MovieReview[] movieReviews) {
        this.movieReviews = movieReviews;
        this.notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movieReviewContentTextView)
        TextView reviewContent;
        @BindView(R.id.movieReviewAuthorTextView)
        TextView reviewAuthor;

        MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
