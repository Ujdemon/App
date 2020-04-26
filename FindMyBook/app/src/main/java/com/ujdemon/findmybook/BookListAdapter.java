package com.ujdemon.findmybook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookListAdapter extends ArrayAdapter<BookList> {
    public BookListAdapter(@NonNull Context context, List<BookList>bookLists) {
        super(context,0,bookLists);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView = convertView;
        if(listView == null) {
           listView =  LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);
        }

        BookList bookList = getItem(position);

        TextView titleView = listView.findViewById(R.id.title);

        titleView.setText(bookList.getTitle());

        TextView author1View = listView.findViewById(R.id.author_1);

        author1View.setText(bookList.getAuthor1());

        TextView author2View = listView.findViewById(R.id.author_2);

        author2View.setText(bookList.getAuthor2());

        TextView publisherView = listView.findViewById(R.id.publisher);

        publisherView.setText(bookList.getPublisher());

        return listView;

    }
}
