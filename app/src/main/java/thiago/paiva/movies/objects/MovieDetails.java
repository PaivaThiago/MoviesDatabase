package thiago.paiva.movies.objects;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetails implements Parcelable {

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };
    private String id;
    private String title;
    private String originalTitle;
    private String overview;
    private String releaseDate;
    private String userRating;
    private String poster;
    private boolean favorite;
    private MovieTrailer[] movieTrailers;
    private MovieReview[] movieReviews;

    public MovieDetails() {
        this.favorite = false;
    }

    private MovieDetails(Parcel in) {
        id = in.readString();
        title = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        userRating = in.readString();
        poster = in.readString();
        favorite = in.readByte() != 0;
        movieTrailers = in.createTypedArray(MovieTrailer.CREATOR);
        movieReviews = in.createTypedArray(MovieReview.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(userRating);
        dest.writeString(poster);
        dest.writeByte((byte) (favorite ? 1 : 0));
        dest.writeTypedArray(movieTrailers, 0);
        dest.writeTypedArray(movieReviews, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public MovieTrailer[] getMovieTrailers() {
        return movieTrailers;
    }

    public void setMovieTrailers(MovieTrailer[] movieTrailers) {
        this.movieTrailers = movieTrailers;
    }

    public MovieReview[] getMovieReviews() {
        return movieReviews;
    }

    public void setMovieReviews(MovieReview[] movieReviews) {
        this.movieReviews = movieReviews;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = formatDate(releaseDate);
    }

    public String getUserRating() {
        return userRating;
    }

    public void setUserRating(String userRating) {
        this.userRating = userRating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    private String formatDate(String date) {
        String[] newDate = date.split("-");
        return newDate[2] + "/" + newDate[1] + "/" + newDate[0];
    }
}
