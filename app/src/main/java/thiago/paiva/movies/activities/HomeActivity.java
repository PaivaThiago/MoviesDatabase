package thiago.paiva.movies.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import thiago.paiva.movies.R;
import thiago.paiva.movies.adapters.MovieAdapter;
import thiago.paiva.movies.data.MovieContract;
import thiago.paiva.movies.objects.MovieDetails;
import thiago.paiva.movies.objects.MoviesReport;
import thiago.paiva.movies.utils.MovieTaskUtils;

public class HomeActivity extends AppCompatActivity implements MoviesReport.MoviesDelegate, LoaderManager.LoaderCallbacks<Cursor> {

    private static final int ID_FAVORITE_MOVIES_LOADER = 999;
    @BindView(R.id.home_ProgressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.home_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.home_error_message_TextView)
    TextView mTextView;
    private MovieAdapter mAdapter;
    private MovieDetails[] movies;
    private MovieDetails[] favoriteMovies;
    private SharedPreferences lastPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        lastPage = getPreferences(Context.MODE_PRIVATE);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(getBaseContext(), numberOfColumns());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MovieAdapter();
        mRecyclerView.setAdapter(mAdapter);
        int page = lastPage.getInt("pageIndex", R.id.action_upcoming);

        if (savedInstanceState != null) {
            movies = (MovieDetails[]) savedInstanceState.getParcelableArray("movies");
            favoriteMovies = (MovieDetails[]) savedInstanceState.getParcelableArray("favMovies");
            setTitle(lastPage.getString("title", getResources().getString(R.string.app_name)));
            mAdapter.setMovies(movies);
            hideProgressBar();
        } else {
            searchMovies(page);
            getSupportLoaderManager().initLoader(ID_FAVORITE_MOVIES_LOADER, null, this);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray("movies", movies);
        outState.putParcelableArray("favMovies", favoriteMovies);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.getParcelableArray("movies");
        savedInstanceState.getParcelableArray("favMovies");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void handleMoviesList(MoviesReport report) {
        if (report.getMovies() != null) {
            hideErrorMessage();
            movies = report.getMovies();
            checkFavoriteStatus();
            mAdapter.setMovies(movies);
            hideProgressBar();
        } else {
            if (!report.getMessage().isEmpty())
                mTextView.setText(report.getMessage());
            showErrorMessage();
        }
    }

    private void checkFavoriteStatus() {
        if (favoriteMovies != null && movies != null)
            for (MovieDetails favorite : favoriteMovies) {
                for (MovieDetails movie : movies) {
                    if (favorite.getId().equals(movie.getId()))
                        movie.setFavorite(true);
                }
            }
    }

    private void getMoviesSearch(String search) {
        setTitle(lastPage.getString("title", getResources().getString(R.string.app_name)));
        showProgressBar();
        new MovieTaskUtils(this).execute(search);
    }

    private void getFavoriteMovies() {
        setTitle(lastPage.getString("title", getResources().getString(R.string.app_name)));
        showProgressBar();
        if (favoriteMovies != null && favoriteMovies.length > 0) {
            hideErrorMessage();
            mAdapter.setMovies(favoriteMovies);
            hideProgressBar();
        } else {
            hideProgressBar();
            mTextView.setText(R.string.no_favorite_movies);
            mAdapter.setMovies(null);
            showErrorMessage();
        }
    }

    private void showProgressBar() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage() {
        mTextView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int widthDivider = 500;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(ID_FAVORITE_MOVIES_LOADER, null, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int pageIndex = item.getItemId();

        if (pageIndex == R.id.action_most_popular | pageIndex == R.id.action_top_rated
                | pageIndex == R.id.action_upcoming | pageIndex == R.id.action_favorites) {
            lastPage.edit().putInt("pageIndex", pageIndex).apply();
            return searchMovies(pageIndex);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean searchMovies(int page) {
        switch (page) {
            case R.id.action_most_popular:
                lastPage.edit().putString("title", getResources().getString(R.string.most_popular_movies)).apply();
                getMoviesSearch("popular");
                return true;
            case R.id.action_top_rated:
                lastPage.edit().putString("title", getResources().getString(R.string.top_rated_movies)).apply();
                getMoviesSearch("top_rated");
                return true;
            case R.id.action_upcoming:
                lastPage.edit().putString("title", getResources().getString(R.string.upcoming_movies)).apply();
                getMoviesSearch("upcoming");
                return true;
            case R.id.action_favorites:
                lastPage.edit().putString("title", getResources().getString(R.string.favorite_movies)).apply();
                getFavoriteMovies();
                return true;
            default:
                return false;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id) {

            case ID_FAVORITE_MOVIES_LOADER:

                return new CursorLoader(this,
                        MovieContract.MovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        MovieContract.MovieEntry.COLUMN_TITLE + " ASC");

            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            int idIndex = 0;
            int titleIndex = 1;
            int originalTitleIndex = 2;
            int overviewIndex = 3;
            int releaseDateIndex = 4;
            int userRatingIndex = 5;
            int posterIndex = 6;

            data.moveToPosition(0);
            List<MovieDetails> movies = new ArrayList<>();
            int i = 0;
            while (!data.isAfterLast()) {
                MovieDetails movie = new MovieDetails();
                movie.setId(data.getString(idIndex));
                movie.setTitle(data.getString(titleIndex));
                movie.setOriginalTitle(data.getString(originalTitleIndex));
                movie.setOverview(data.getString(overviewIndex));
                movie.setReleaseDate(data.getString(releaseDateIndex));
                movie.setUserRating(data.getString(userRatingIndex));
                movie.setPoster(data.getString(posterIndex));
                movie.setFavorite(true);

                movies.add(movie);
                data.moveToNext();
                i++;
            }

            data.close();
            favoriteMovies = new MovieDetails[movies.size()];
            for (i = 0; i < movies.size(); i++)
                favoriteMovies[i] = movies.get(i);
            if(lastPage.getInt("pageIndex", R.id.action_upcoming) == R.id.action_favorites)
                getFavoriteMovies();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        favoriteMovies = null;
    }
}
