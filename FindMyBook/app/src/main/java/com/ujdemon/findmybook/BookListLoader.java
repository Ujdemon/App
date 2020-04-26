package com.ujdemon.findmybook;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.util.List;

public class BookListLoader extends AsyncTaskLoader<List<BookList>> {

    private static final String LOG_TAG = BookListLoader.class.getSimpleName();

    private String mUrl;
    public BookListLoader(Context context, String url){
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
        if(mUrl==null){
            return null;
        }
        List<BookList> bookLists = null;
       try{
            bookLists = QueryUtils.fetchBookListData(mUrl);
       }catch (IOException e){
           Log.e(LOG_TAG,"Not able to retrieve book list",e);
       }
       return bookLists;
    }
}
