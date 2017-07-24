package thiago.paiva.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesDb.db";
    private static final int VERSION = 1;

    MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE " +
            MovieContract.MovieEntry.TABLE_NAME + " (" +
            MovieContract.MovieEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
            MovieContract.MovieEntry.COLUMN_TITLE + " TEXT, " +
            MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
            MovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT, " +
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +
            MovieContract.MovieEntry.COLUMN_USER_RATING + " TEXT, " +
            MovieContract.MovieEntry.COLUMN_POSTER + " TEXT);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
