package com.ujdemon.findmybook;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    public static List<BookList> fetchBookListData(String requestUrl)throws IOException {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Problem making Http Request",e);
        }
        List<BookList> bookLists = extractFeaturesFromJson(jsonResponse);

        return bookLists;
    }

    private static List<BookList> extractFeaturesFromJson(String jsonResponse) {

        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<BookList> bookLists = new ArrayList<>();
        try{
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray itemsArray = baseJsonResponse.getJSONArray("items");
            int i;
            for(i = 0; i<itemsArray.length(); i++){
                JSONObject  bookObject= itemsArray.getJSONObject(i);
                JSONObject volumeInfo = bookObject.getJSONObject("volumeInfo");
                JSONArray authors = volumeInfo.getJSONArray("authors");

                String title = volumeInfo.getString("title");
                String author1 = authors.getString(0);
                String author2 = authors.getString(1);
                String publisher = volumeInfo.getString("publisher");


                BookList bookList = new BookList(title,author1,author2,publisher);
                bookLists.add(bookList);
            }
        }
        catch (JSONException e){
            Log.e(LOG_TAG,"Problem parsing the book list JSON results",e);
        }
        return bookLists;
    }

    private static String makeHttpRequest(URL url)throws IOException {
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"Error Response Code"+urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem Retrieving Book List",e);
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }
        Log.e(LOG_TAG,"JSON HERE is : "+jsonResponse);
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream)throws IOException {
        StringBuilder output = new StringBuilder();
        while(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            output.append(line);
        }
        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try{
            url = new URL(requestUrl);
        }catch (MalformedURLException e){
//            Log.e(LOG_TAG,"Problem Building URL"+url);
            Log.e(LOG_TAG,"Problem Building URL",e);
        }
        return url;
    }

}
