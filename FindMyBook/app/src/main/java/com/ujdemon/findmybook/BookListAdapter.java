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

    public BookListAdapter(@NonNull Context context, List<BookList> bookList) {
        super(context, 0,bookList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if(listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item,parent,false);
        }

        BookList currentBook = getItem(position);

        TextView titleView = listItemView.findViewById(R.id.title);
        titleView.setText(currentBook.getTitle());

        TextView author1View = listItemView.findViewById(R.id.author_1);
        author1View.setText(currentBook.getAuthor1());

        TextView author2View = listItemView.findViewById(R.id.author_2);
        author2View.setText(currentBook.getAuthor2());

        TextView publisherView = listItemView.findViewById(R.id.publisher);
        publisherView.setText(currentBook.getPublisher());

        return listItemView;
    }
}
