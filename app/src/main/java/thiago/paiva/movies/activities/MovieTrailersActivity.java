package thiago.paiva.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import thiago.paiva.movies.R;
import thiago.paiva.movies.adapters.MovieTrailerAdapter;
import thiago.paiva.movies.objects.MovieDetails;
import thiago.paiva.movies.objects.MovieTrailer;

public class MovieTrailersActivity extends AppCompatActivity {

    @BindView(R.id.trailers_recycler_view)
    RecyclerView mRecyclerView;
    private MovieTrailer[] movieTrailers;
    private MovieDetails movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailers);
        ButterKnife.bind(this);

        MovieTrailerAdapter mAdapter;
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieTrailerAdapter();
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            if (intent.hasExtra("movie")) {
                movieDetails = intent.getParcelableExtra("movie");
                movieTrailers = movieDetails.getMovieTrailers();
                mAdapter.setMovieTrailers(movieTrailers);
                setTitle(movieDetails.getTitle());
            }
        } else {
            movieDetails = savedInstanceState.getParcelable("movie");
            if (movieDetails != null)
                movieTrailers = movieDetails.getMovieTrailers();
            mAdapter.setMovieTrailers(movieTrailers);
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
