package thiago.paiva.movies.utils;

import android.os.AsyncTask;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import thiago.paiva.movies.objects.MoviesReport;

public class MovieTaskUtils extends AsyncTask<String, Void, MoviesReport>{

    private final MoviesReport.MoviesDelegate delegate;

    public MovieTaskUtils(MoviesReport.MoviesDelegate moviesDelegate){
        this.delegate = moviesDelegate;
    }

    @Override
    protected MoviesReport doInBackground(String... params) {
        MoviesReport report = new MoviesReport();
        try {
            URL url = NetworkUtils.buildUrl(params[0]);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            report = MovieJsonUtils.getMoviesFromJson(json);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    protected void onPostExecute(MoviesReport report) {
        delegate.handleMoviesList(report);
    }
}
