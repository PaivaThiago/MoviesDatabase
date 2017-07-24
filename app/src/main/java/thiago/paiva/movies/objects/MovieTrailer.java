package thiago.paiva.movies.objects;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class MovieTrailer implements Parcelable {

    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };
    private String key;
    private String name;
    private String site;
    private String type;

    public MovieTrailer() {
    }

    private MovieTrailer(Parcel in) {
        key = in.readString();
        name = in.readString();
        site = in.readString();
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
        dest.writeString(site);
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Uri getUri() {
        return new Uri.Builder()
                .scheme("https")
                .authority("www.youtube.com")
                .appendPath("watch")
                .appendQueryParameter("v", getKey())
                .build();
    }
}
