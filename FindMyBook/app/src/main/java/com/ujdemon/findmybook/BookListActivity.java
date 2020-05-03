package com.ujdemon.findmybook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<BookList>> {

    private static final String LOG_TAG = BookListActivity.class.getSimpleName();
    private static final String mUrl="https://www.googleapis.com/books/v1/volumes?q=";
    private BookListAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private static final int BOOK_LIST_LOADER_ID = 1;
    private String mUrlFinal;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);

        ListView listView = findViewById(R.id.list);

        mEmptyStateTextView  = findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);
        searchView = findViewById(R.id.search_view);


        mAdapter = new BookListAdapter(this,new ArrayList<BookList>());



        listView.setAdapter(mAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mUrlFinal=(mUrl+searchView.getQuery()).trim();
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if(networkInfo!=null && networkInfo.isConnected()){
                    LoaderManager.getInstance(BookListActivity.this).initLoader(BOOK_LIST_LOADER_ID,null,BookListActivity.this);

                }else{
                    View loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(View.GONE);

                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



    }

    @NonNull
    @Override
    public Loader<List<BookList>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e(LOG_TAG," onCreateLoader  "+mUrlFinal);
        return new BookListLoader(this,mUrlFinal);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookList>> loader, List<BookList> bookLists) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        Log.e(LOG_TAG," onLoadFinish  "+mUrlFinal);
        mEmptyStateTextView.setText(R.string.no_books_found);

        mAdapter.clear();

        if(bookLists!=null && !bookLists.isEmpty()){
            mAdapter.addAll(bookLists);
        }
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<BookList>> loader) {
        mAdapter.clear();
    }
}
