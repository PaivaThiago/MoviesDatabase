package thiago.paiva.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import thiago.paiva.movies.R;
import thiago.paiva.movies.adapters.MovieReviewsAdapter;
import thiago.paiva.movies.objects.MovieDetails;
import thiago.paiva.movies.objects.MovieReview;

public class MovieReviewsActivity extends AppCompatActivity {

    @BindView(R.id.reviews_recycler_view)
    RecyclerView mRecyclerView;
    private MovieReview[] movieReviews;
    private MovieDetails movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_reviews);
        ButterKnife.bind(this);

        MovieReviewsAdapter mAdapter;
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieReviewsAdapter();
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent.hasExtra("movie")) {
                movieDetails = intent.getParcelableExtra("movie");
                movieReviews = movieDetails.getMovieReviews();
                mAdapter.setMovieReviews(movieReviews);
                setTitle(movieDetails.getTitle());
            }
        } else {
            movieDetails = savedInstanceState.getParcelable("movie");
            if (movieDetails != null)
                movieReviews = movieDetails.getMovieReviews();
            mAdapter.setMovieReviews(movieReviews);
            setTitle(movieDetails.getTitle());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movie", movieDetails);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.getParcelable("movie");
        super.onRestoreInstanceState(savedInstanceState);
    }
}
