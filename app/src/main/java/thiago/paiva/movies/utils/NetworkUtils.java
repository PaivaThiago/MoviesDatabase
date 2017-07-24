package thiago.paiva.movies.utils;

import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    final private static String API_KEY = "";
    final private static String API_BASE_URL = "https://api.themoviedb.org/3/movie/";
    final private static String LANG = "en-US";
    final private static String API_PARAM = "api_key";
    final private static String LANG_PARAM = "language";

    public static URL buildUrl(String path) {
        Uri builtUri = Uri.parse(API_BASE_URL)
                .buildUpon()
                .appendPath(path)
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(LANG_PARAM, LANG)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildUrl(String path1, String path2) {
        Uri builtUri = Uri.parse(API_BASE_URL)
                .buildUpon()
                .appendPath(path1)
                .appendPath(path2)
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter(LANG_PARAM, LANG)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}