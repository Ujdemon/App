package com.ujdemon.findmybook;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookList>> {

    private static final String URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookListAdapter mAdapter;
    private TextView mEmptyStateTextView;

    private static final int BOOKLIST_LOADER_ID = 1;
    private String url_part;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_list);

        ListView bookListView = findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);


        mAdapter = new BookListAdapter(this, new ArrayList<BookList>());

        bookListView.setAdapter(mAdapter);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(BOOKLIST_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                url_part = newText;
                return false;
            }
        });

    }

    @NonNull
    @Override
    public Loader<List<BookList>> onCreateLoader(int id, @Nullable Bundle args) {
        String url = "";
        return new BookListLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookList>> loader, List<BookList> booklist) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_books);
        mAdapter.clear();

        if (booklist != null && !booklist.isEmpty()) {
            mAdapter.addAll(booklist);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookList>> loader) {
        mAdapter.clear();
    }


}