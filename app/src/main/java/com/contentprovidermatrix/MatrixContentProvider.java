package com.contentprovidermatrix;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.util.Arrays;

public class MatrixContentProvider extends ContentProvider {

    private boolean mIsLoaded;

    public static final String IS_LOADED_COLUMN = "is_loaded";
    public static final Uri URI = Uri.parse("content://com.contentprovidermatrix.provider/");
    private static final String PATH = "path_to_variable";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = new MatrixCursor(projection);
        MatrixCursor.RowBuilder builder = cursor.newRow();
        Log.e("Test", "Projection: " + Arrays.toString(projection));
        for (String column : projection) {
            if (column.equals(IS_LOADED_COLUMN)) {
                builder.add(mIsLoaded);
            }
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        mIsLoaded = ((Integer) values.get(IS_LOADED_COLUMN)) == 1;
        Uri uri1 = Uri.withAppendedPath(URI, PATH);
        getContext().getContentResolver().notifyChange(uri1, null);
        return uri1;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
