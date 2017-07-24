package thiago.paiva.movies.objects;

public class MovieReviewsReport {

    private MovieReview[] movieReviews;
    private String message;

    public MovieReviewsReport() {
        movieReviews = null;
        message = "";
    }

    public MovieReview[] getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(MovieReview[] movieReviews) {
        this.movieReviews = movieReviews;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface MovieReviewsDelegate {
        void handleMovieReviewsList(MovieReviewsReport report);
    }
}
