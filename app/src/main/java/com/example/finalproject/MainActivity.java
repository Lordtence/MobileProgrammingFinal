package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.CallAdapter;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ListItemClickListener, Serializable {

    private static final int NUM_LIST_ITEMS = 21;
    private static final int MAX_RESULTS = 21;
    private List<GoogleBookModel> MainBookList;

    private ItemAdapter mAdapter;
    private RecyclerView mNumbersList;

    // BOOK FETCH VARS
    private EditText mBookInput;
    private ApiService api;
    private List<Item> volumeInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = RetroClient.getApiService();

        mBookInput = (EditText)findViewById(R.id.bookInput);
        // create empty booklist
        MainBookList = new ArrayList<>();
        MainBookList.clear();

        // CREATE RECYCLE VIEW
        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new ItemAdapter(this, NUM_LIST_ITEMS);
        mNumbersList.setAdapter(mAdapter);
    }

    // overiding on click from ItemAdapter so that this runs when an item is clicked
    @Override
    public void onListItemClick(int clickedItemIndex, View itemView) {
        itemView.setBackgroundColor(Color.RED);
        Intent intent = new Intent(MainActivity.this, SearchedActivity.class);
        // TODO: Pass the specefic book object through intent (maybe through implementing "serailizable" interface?)
        // get the specefic book object that was clicked, call the intent passing the clicked book object
        GoogleBookModel book = MainBookList.get(clickedItemIndex);
        //To pass:
        intent.putExtra("MainActivity", book);

        startActivity(intent);
    }
    // TODO: Goal of onListItemClick is to call to a new activity, passing an intent of the GoogleBookModel object,
    // todo: which was clicked at a cickedItemIndex.

    //When you press the search button, you query the endpoint, generate a List of book models, assign it to the adapter, and refresh the adapter.
    // this runs when search button is clicked
    public void searchBooks(View view) {
        // query the endpoint
        // private static final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";
        // Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
        //                    .appendQueryParameter(QUERY_PARAM, queryString)
        //                    .appendQueryParameter(MAX_RESULTS, "10")
        //                    .appendQueryParameter(PRINT_TYPE, "books")
        //                    .build();
        String queryString = mBookInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        Call<BookResponse> call = api.getMyJSON(queryString, MAX_RESULTS);
        call.enqueue(new Callback<BookResponse>() {
                         @Override
                         public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                             if (response.isSuccessful()) {
                                 volumeInfoList = response.body().getItems();
                                 mAdapter.setVolumeInfo(volumeInfoList);
                             }
                         }

                         @Override
                         public void onFailure(Call<BookResponse> call, Throwable t) {
                         }
                     });
                         // generate a List of book models

        // set adapter to have booklist

        //refresh adapter
    }

    // TODO: Goal of searchBooks, it will first call FetchBook to search for books with input given, then
    // todo: retrieve the list of GoogleBookModels from Fetchbook. It should then, create the RecycleView
    // todo: passing through the bookList of GoogleBookModels in order to create the search results
}

