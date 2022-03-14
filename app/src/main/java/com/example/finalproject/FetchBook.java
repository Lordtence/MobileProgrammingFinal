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

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        List<GoogleBookModel> bookList = new ArrayList<GoogleBookModel>();
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");


            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;

            // Look for results in the items array, exiting
            // when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length()) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                // create new bookmodel
                GoogleBookModel bookModel = new GoogleBookModel();

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Add values to book model
                bookModel.addTitle(title);
                bookModel.addAuthors(authors);
                // add bookmodel to list
                bookList.add(bookModel);
                // Move to the next item.
                i++;
            }

        } catch (Exception e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.get().setText("No Results");
            mAuthorText.get().setText("");
        }
        transferBooks(bookList);
    }

    public void transferBooks(List<GoogleBookModel> inputBookList)
    {
        // transfer book list to other class
        BookListTransfer transferList = new BookListTransfer(inputBookList);
        // this call sets the list in Main Activity
        transferList.setMainList();
    }

    FetchBook(TextView titleText, TextView authorText) {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }
    // TODO: Overall goal of the class is to create a LIST of GOOGLEBOOKMODELS based on the search input. Then be able to have that list retrievable to main acticity
}
