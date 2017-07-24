package thiago.paiva.movies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import thiago.paiva.movies.objects.MovieDetails;
import thiago.paiva.movies.objects.MovieReview;
import thiago.paiva.movies.objects.MovieReviewsReport;
import thiago.paiva.movies.objects.MovieTrailer;
import thiago.paiva.movies.objects.MovieTrailersReport;
import thiago.paiva.movies.objects.MoviesReport;

public final class MovieJsonUtils {

    public static MoviesReport getMoviesFromJson(String movieJsonStr)
            throws JSONException {

        final String ID = "id";
        final String TITLE = "title";
        final String ORIGINAL_TITLE = "original_title";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String VOTE_AVERAGE = "vote_average";
        final String POSTER_PATH = "poster_path";
        final String STATUS_MESSAGE = "status_message";
        final String RESULTS = "results";

        MoviesReport report = new MoviesReport();
        JSONObject movieJson = new JSONObject(movieJsonStr);

        if (movieJson.has(STATUS_MESSAGE)) {
            report.setMessage(movieJson.optString(STATUS_MESSAGE));
            return report;
        }

        JSONArray movieArray = movieJson.getJSONArray(RESULTS);
        MovieDetails[] parsedMovies = new MovieDetails[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            MovieDetails movie = new MovieDetails();
            JSONObject movieObject = movieArray.optJSONObject(i);

            movie.setId(movieObject.optString(ID));
            movie.setTitle(movieObject.optString(TITLE));
            movie.setOriginalTitle(movieObject.optString(ORIGINAL_TITLE));
            movie.setOverview(movieObject.optString(OVERVIEW));
            movie.setReleaseDate(movieObject.optString(RELEASE_DATE));
            movie.setPoster(movieObject.optString(POSTER_PATH));
            movie.setUserRating(movieObject.optString(VOTE_AVERAGE));

            parsedMovies[i] = movie;
        }

        report.setMovies(parsedMovies);
        return report;
    }

    public static MovieTrailersReport getMovieTrailersFromJson(String movieJsonStr)
            throws JSONException {

        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";
        final String TYPE = "type";
        final String STATUS_MESSAGE = "status_message";
        final String RESULTS = "results";

        MovieTrailersReport report = new MovieTrailersReport();
        JSONObject movieJson = new JSONObject(movieJsonStr);

        if (movieJson.has(STATUS_MESSAGE)) {
            report.setMessage(movieJson.optString(STATUS_MESSAGE));
            return report;
        }

        JSONArray movieTrailerArray = movieJson.getJSONArray(RESULTS);
        List<MovieTrailer> movieTrailers = new ArrayList<>();

        for (int i = 0; i < movieTrailerArray.length(); i++) {
            MovieTrailer movieTrailer = new MovieTrailer();
            JSONObject movieTrailerObject = movieTrailerArray.optJSONObject(i);

            movieTrailer.setKey(movieTrailerObject.optString(KEY));
            movieTrailer.setName(movieTrailerObject.optString(NAME));
            movieTrailer.setSite(movieTrailerObject.optString(SITE));
            movieTrailer.setType(movieTrailerObject.optString(TYPE));

            if (movieTrailer.getType().equals("Trailer"))
                movieTrailers.add(movieTrailer);
        }

        MovieTrailer[] parsedMovieTrailers = new MovieTrailer[movieTrailers.size()];
        for (int i = 0; i < movieTrailers.size(); i++)
            parsedMovieTrailers[i] = movieTrailers.get(i);

        report.setMovieTrailers(parsedMovieTrailers);
        return report;
    }

    public static MovieReviewsReport getMovieReviewsFromJson(String movieJsonStr)
            throws JSONException {

        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String URL = "url";
        final String STATUS_MESSAGE = "status_message";
        final String RESULTS = "results";

        MovieReviewsReport report = new MovieReviewsReport();
        JSONObject movieReviewsJson = new JSONObject(movieJsonStr);

        if (movieReviewsJson.has(STATUS_MESSAGE)) {
            report.setMessage(movieReviewsJson.optString(STATUS_MESSAGE));
            return report;
        }

        JSONArray movieReviewsArray = movieReviewsJson.getJSONArray(RESULTS);
        MovieReview[] parsedMovieReviews = new MovieReview[movieReviewsArray.length()];

        for (int i = 0; i < movieReviewsArray.length(); i++) {
            MovieReview movieReview = new MovieReview();
            JSONObject movieReviewObject = movieReviewsArray.optJSONObject(i);

            movieReview.setAuthor(movieReviewObject.optString(AUTHOR));
            movieReview.setContent(movieReviewObject.optString(CONTENT));
            movieReview.setUrl(movieReviewObject.optString(URL));

            parsedMovieReviews[i] = movieReview;
        }

        report.setMovieReviews(parsedMovieReviews);
        return report;
    }

}