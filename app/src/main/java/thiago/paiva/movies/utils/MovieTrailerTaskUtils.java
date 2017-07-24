package thiago.paiva.movies.utils;

import android.os.AsyncTask;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import thiago.paiva.movies.objects.MovieTrailersReport;

public class MovieTrailerTaskUtils extends AsyncTask<String, Void, MovieTrailersReport>{

    private final MovieTrailersReport.MovieTrailersDelegate delegate;

    public MovieTrailerTaskUtils(MovieTrailersReport.MovieTrailersDelegate movieTrailersDelegate){
        this.delegate = movieTrailersDelegate;
    }

    @Override
    protected MovieTrailersReport doInBackground(String... params) {
        MovieTrailersReport report = new MovieTrailersReport();
        try {
            URL url = NetworkUtils.buildUrl(params[0], "videos");
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            report = MovieJsonUtils.getMovieTrailersFromJson(json);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return report;
    }

    @Override
    protected void onPostExecute(MovieTrailersReport report) {
        delegate.handleMovieTrailersList(report);
    }
}
