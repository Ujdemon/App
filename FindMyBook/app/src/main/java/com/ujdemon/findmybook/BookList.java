package com.ujdemon.findmybook;

public class BookList {
    private String mAuthor1;
    private String mAuthor2;
    private String mTitle;
    private String mPublisher;
// format of url ::::: https://www.googleapis.com/apiName/apiVersion/resourcePath?parameters
// "https://www.googleapis.com/books/v1/{collectionName}/resourceID?parameters"
//  https://www.googleapis.com/books/v1/volumes?q=quilting
    public BookList(String title, String author1,String author2,String publisher){
        mTitle = title;
        mAuthor1 = author1;
        mAuthor2 = author2;
        mPublisher = publisher;
    }

    public String getAuthor1() {
        return mAuthor1;
    }

    public String getAuthor2() {
        return mAuthor2;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public String getTitle(){
        return mTitle;
    }
}
