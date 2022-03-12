package com.example.finalproject;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;
    private List<GoogleBookModel> bookList = new ArrayList<>(); // list of bookmodels

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("IMPORTANT EXCECUTE: ", bookList.toString());
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;
            // TODO: something is somehow wrong with these other variables despite being the same as title and authors (?)
            String subtitle = null;
            String description = null;
            String publisher = null;

            // Look for results in the items array, exiting
            // check to see it has authors title subtitle description and publisher
            // TODO: this might be needed for the loop? && subtitle == null && description == null && publisher == null
            while (i < itemsArray.length() &&
                    (authors == null && title == null)) {
                // Get the current item information.
                GoogleBookModel bookModel = new GoogleBookModel();
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the JSON data from current item
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    subtitle = volumeInfo.getString("subtitle");
                    description = volumeInfo.getString("description");
                    publisher = volumeInfo.getString("publisher");
                    bookModel.addTitle(title);
                    bookModel.addAuthors(authors);
                    bookModel.addSubtitle(subtitle);
                    bookModel.addDescription(description);
                    bookModel.addPublisher(publisher);
                    addBookToList(bookModel); // add JSON this object with the JSON data to a LIST OF OBJECTS
                    Log.d("IMPORTANT book: ", bookList.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Move to the next item
                i++;
            }
        } catch (Exception e) {
            // getting search result failed. Maybe change to a toast message?
            mTitleText.get().setText(R.string.no_response);
            mAuthorText.get().setText("");
        }
        Log.d("IMPORTANT DONE: ", bookList.toString());
    }

    public void addBookToList(GoogleBookModel addedBook)
    {
        bookList.add(addedBook);
        Log.d("IMPORTANT ADDED: ", bookList.toString());
    }

    public List<GoogleBookModel> getBookList()
    {
        return bookList;
    }

    FetchBook(TextView titleText, TextView authorText) {
        Log.d("IMPORTANT STARTING: ", bookList.toString());
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }
    // TODO: Overall goal of the class is to create a LIST of GOOGLEBOOKMODELS based on the search input. Then be able to have that list retrievable to main acticity
}
