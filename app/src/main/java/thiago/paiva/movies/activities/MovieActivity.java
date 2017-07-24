package thiago.paiva.movies.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thiago.paiva.movies.R;
import thiago.paiva.movies.data.MovieContract;
import thiago.paiva.movies.objects.MovieDetails;
import thiago.paiva.movies.objects.MovieReviewsReport;
import thiago.paiva.movies.objects.MovieTrailersReport;
import thiago.paiva.movies.utils.MovieReviewTaskUtils;
import thiago.paiva.movies.utils.MovieTrailerTaskUtils;

public class MovieActivity extends AppCompatActivity
        implements MovieReviewsReport.MovieReviewsDelegate,
        MovieTrailersReport.MovieTrailersDelegate {

    @BindView(R.id.movie_detail_original_title_TextView)
    TextView originalTitle;
    @BindView(R.id.movie_detail_release_date_TextView)
    TextView releaseDate;
    @BindView(R.id.movie_detail_overview_TextView)
    TextView overview;
    @BindView(R.id.movie_detail_user_rating_TextView)
    TextView userRating;
    @BindView(R.id.movie_detail_banner_ImageView)
    ImageView poster;
    @BindView(R.id.movieTrailersTextView)
    TextView movieTrailersTextView;
    @BindView(R.id.movieTrailersImageButton)
    ImageButton movieTrailersButton;
    @BindView(R.id.movieReviewsTextView)
    TextView movieReviewsTextView;
    @BindView(R.id.movieReviewsButton)
    ImageButton movieReviewsButton;
    @BindView(R.id.movieAddFavorite)
    ImageButton movieAddFavoriteButton;
    private MovieDetails movieDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            movieDetails = savedInstanceState.getParcelable("movie");
            fillUI();
        } else {
            Intent it = getIntent();
            if (it.hasExtra("movie")) {
                movieDetails = it.getParcelableExtra("movie");
                new MovieReviewTaskUtils(this).execute(movieDetails.getId());
                new MovieTrailerTaskUtils(this).execute(movieDetails.getId());
                fillUI();
            }
        }
    }

    void fillUI() {
        setTitle(movieDetails.getTitle());
        originalTitle.setText(movieDetails.getOriginalTitle());
        releaseDate.setText(movieDetails.getReleaseDate());
        userRating.setText(movieDetails.getUserRating());

        if (!movieDetails.getOverview().isEmpty())
            overview.setText(movieDetails.getOverview());
        else
            overview.setText(R.string.no_synopsis_available);

        Uri url = Uri.parse("http://image.tmdb.org/t/p/w185/" + movieDetails.getPoster());
        Picasso.with(getBaseContext())
                .load(url)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_loading)
                .into(poster);

        if (movieDetails.isFavorite())
            movieAddFavoriteButton.setImageResource(R.drawable.ic_favorite_yes);
        else
            movieAddFavoriteButton.setImageResource(R.drawable.ic_favorite_no);
    }

    @OnClick(R.id.movieAddFavorite)
    public void onMovieAddFavoriteClicked() {
        if (movieDetails.isFavorite()) {
            movieDetails.setFavorite(false);
            movieAddFavoriteButton.setImageResource(R.drawable.ic_favorite_no);

            Uri uri = MovieContract.MovieEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(movieDetails.getId()).build();
            getContentResolver().delete(uri, null, null);
        } else {
            movieDetails.setFavorite(true);
            movieAddFavoriteButton.setImageResource(R.drawable.ic_favorite_yes);

            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieEntry.COLUMN_ID, movieDetails.getId());
            contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movieDetails.getTitle());
            contentValues.put(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE, movieDetails.getOriginalTitle());
            contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieDetails.getOverview());
            contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movieDetails.getReleaseDate());
            contentValues.put(MovieContract.MovieEntry.COLUMN_USER_RATING, movieDetails.getUserRating());
            contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER, movieDetails.getPoster());
            getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
        }
    }

    @OnClick(R.id.movieTrailersImageButton)
    public void onMovieTrailersButtonClicked() {
        if (movieDetails.getMovieTrailers() != null && movieDetails.getMovieTrailers().length > 0) {
            if (movieDetails.getMovieTrailers().length == 1) {
                Intent intent = new Intent(Intent.ACTION_VIEW, movieDetails.getMovieTrailers()[0].getUri());
                startActivity(intent);
            } else {
                Intent intent = new Intent(MovieActivity.this, MovieTrailersActivity.class);
                intent.putExtra("movie", movieDetails);
                startActivity(intent);
            }
        } else {
            Toast.makeText(getBaseContext(), getResources().getText(R.string.no_trailers_available), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.movieReviewsButton)
    public void onMovieReviewsButtonClicked() {
        if (movieDetails.getMovieReviews() != null && movieDetails.getMovieReviews().length > 0) {
            Intent intent = new Intent(MovieActivity.this, MovieReviewsActivity.class);
            intent.putExtra("movie", movieDetails);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), getResources().getText(R.string.no_reviews_available), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void handleMovieReviewsList(MovieReviewsReport report) {
        if (report.getMovieReviews() != null) {
            movieDetails.setMovieReviews(report.getMovieReviews());
        }
    }

    @Override
    public void handleMovieTrailersList(MovieTrailersReport report) {
        if (report.getMovieTrailers() != null) {
            movieDetails.setMovieTrailers(report.getMovieTrailers());
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
