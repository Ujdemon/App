package com.ujdemon.findmybook;

import android.content.AsyncTaskLoader;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookListLoader extends AsyncTaskLoader<List<BookList>> {

    private static final String LOG_TAG = BookListLoader.class.getName();
    private String mUrl;

    public BookListLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<BookList> loadInBackground() {
        List<BookList> bookLists = QueryUtils.fetchBookList(mUrl);
        return bookLists;
    }
}
