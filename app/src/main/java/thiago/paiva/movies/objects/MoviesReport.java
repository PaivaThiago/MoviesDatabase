package thiago.paiva.movies.objects;

public class MoviesReport {

    private MovieDetails[] movies;
    private String message;

    public MoviesReport() {
        movies = null;
        message = "";
    }

    public MovieDetails[] getMovies() {
        return movies;
    }

    public void setMovies(MovieDetails[] movies) {
        this.movies = movies;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public interface MoviesDelegate {
        void handleMoviesList(MoviesReport report);
    }
}
