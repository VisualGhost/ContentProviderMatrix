package com.contentprovidermatrix;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.hello_world);
        getSupportLoaderManager().initLoader(0, null, this);
        mTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final ContentValues contentValues = new ContentValues();
                contentValues.put(MatrixContentProvider.IS_LOADED_COLUMN, 1);
                getContentResolver().insert(MatrixContentProvider.URI, contentValues);
                return true;
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, MatrixContentProvider.URI, new String[]{MatrixContentProvider.IS_LOADED_COLUMN}, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.e("Test", "000>");
        if (cursor != null && cursor.moveToFirst()) {
            mTextView.setText("Loaded: " + (cursor.getString(cursor.getColumnIndex(MatrixContentProvider.IS_LOADED_COLUMN))));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
