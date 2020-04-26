package com.ujdemon.findmybook;

public class BookList {

    private String mUrl;
    private String mAuthor1;
    private String mAuthor2;
    private String mPublisher;
    private String mTitle;

    public BookList(String title,String author1,String author2,String publisher){
        mTitle = title;
        mAuthor2 = author2;
        mAuthor1 = author1;
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

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
