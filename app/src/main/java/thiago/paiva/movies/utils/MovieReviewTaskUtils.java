package thiago.paiva.movies.utils;

import android.os.AsyncTask;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;

import thiago.paiva.movies.objects.MovieReviewsReport;

public class MovieReviewTaskUtils extends AsyncTask<String, Void, MovieReviewsReport> {

    private final MovieReviewsReport.MovieReviewsDelegate delegate;

    public MovieReviewTaskUtils(MovieReviewsReport.MovieReviewsDelegate movieReviewsDelegate){
        this.delegate = movieReviewsDelegate;
    }

    @Override
    protected MovieReviewsReport doInBackground(String... params) {
        MovieReviewsReport report = new MovieReviewsReport();
        try {
            URL url = NetworkUtils.buildUrl(params[0], "reviews");
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            report = MovieJsonUtils.getMovieReviewsFromJson(json);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    protected void onPostExecute(MovieReviewsReport report) {
        delegate.handleMovieReviewsList(report);
    }
}
