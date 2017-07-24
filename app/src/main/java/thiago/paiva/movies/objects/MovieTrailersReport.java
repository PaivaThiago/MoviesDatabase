package thiago.paiva.movies.objects;

public class MovieTrailersReport {

    private MovieTrailer[] movieTrailers;
    private String message;

    public MovieTrailersReport() {
        movieTrailers = null;
        message = "";
    }

    public MovieTrailer[] getMovieTrailers() {
        return movieTrailers;
    }

    public void setMovieTrailers(MovieTrailer[] movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface MovieTrailersDelegate {
        void handleMovieTrailersList(MovieTrailersReport report);
    }
}
