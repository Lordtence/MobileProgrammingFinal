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

    // THESE NUMBERS HAVE TO BE EQUAL
    private static final int NUM_LIST_ITEMS = 21;
    private static final int MAX_RESULTS = 21;

    private ItemAdapter mAdapter;
    private EditText mBookInput;
    private ApiService api;
    private List<Item> volumeInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initiate API
        api = RetroClient.getApiService();

        // set bookInput field
        mBookInput = (EditText)findViewById(R.id.bookInput);

        // CREATE RECYCLE VIEW
        RecyclerView mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);
        mAdapter = new ItemAdapter(this, NUM_LIST_ITEMS);
        mNumbersList.setAdapter(mAdapter);
    }

    // overiding on click from ItemAdapter so that this runs when an item is clicked
    @Override
    public void onListItemClick(int clickedItemIndex, View itemView) {
        // if there is no recycle view set, do nothing
        if(volumeInfoList == null) return;
        // make background dark gray for clicked view
        itemView.setBackgroundColor(Color.DKGRAY);
        // intent to switch activity to SearchedActivity
        Intent intent = new Intent(MainActivity.this, SearchedActivity.class);

        // store the Data inside the volumeInfoList at the index it was clicked to a new item
        Item chosenBook = volumeInfoList.get(clickedItemIndex);
        // Use that item to then get the VolumeInfo which has the "authors description" etc
        VolumeInfo intendedVolumeiInfo = chosenBook.getVolumeInfo();
        // Directly pass the Volume Info to the Searched Activity
        intent.putExtra("MainActivity", intendedVolumeiInfo);
        // switch activity
        startActivity(intent);
    }

    // When you press the search button, you query the endpoint, generate a List of book models, assign it to the adapter, and refresh the adapter.
    // this runs when search button is clicked
    public void searchBooks(View view) {
        // Get the bookInput text for the query
        String queryString = mBookInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        // hide keyboard after typing
        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        // ---- CALLING THE API TO SEARCH FOR BOOK
        // query JSON with the String, MAX RESULTS is how many search results are shown
        Call<BookResponse> call = api.getMyJSON(queryString, MAX_RESULTS);
        call.enqueue(new Callback<BookResponse>() {
                         @Override
                         public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                             // call was successful
                             if (response.isSuccessful()) {
                                 // Get the results and put them into the volumeInfoList
                                 volumeInfoList = response.body().getItems();
                                 // Pass the volumeInfoList data into the RecycleView
                                 mAdapter.setVolumeInfo(volumeInfoList);
                             }
                         }
                        // call failed
                         @Override
                         public void onFailure(Call<BookResponse> call, Throwable t) {
                         }
                     });
    }
}

