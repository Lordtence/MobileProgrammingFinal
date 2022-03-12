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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 150;
    private FetchBook searchedBook;
    private List<GoogleBookModel> bookList = new ArrayList<>();

    private ItemAdapter mAdapter;
    private RecyclerView mNumbersList;

    // BOOK FETCH VARS
    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBookInput = (EditText)findViewById(R.id.bookInput);
        mTitleText = (TextView)findViewById(R.id.titleText);
        mAuthorText = (TextView)findViewById(R.id.authorText);

    }

    // overiding on click from ItemAdapter so that this runs when an item is clicked
    @Override
    public void onListItemClick(int clickedItemIndex, View itemView) {
        itemView.setBackgroundColor(Color.RED);
        Intent intent = new Intent(MainActivity.this, SearchedActivity.class);

        // TODO: Pass the specefic book object through intent (maybe through implementing "serailizable" interface?)
        // get the specefic book object that was clicked, call the intent passing the clicked book object
        GoogleBookModel book = bookList.get(clickedItemIndex);
        //To pass book object to intent
        //intent.putExtra("MyClass", book);

        startActivity(intent);
    }
    // TODO: Goal of onListItemClick is to call to a new activity, passing an intent of the GoogleBookModel object,
    // todo: which was clicked at a cickedItemIndex.


    // TODO: app crashes when search button is clicked, try to go line by line to find a problem
    // this runs when search button is clicked
    public void searchBooks(View view) {
        // Get the search string from input
        String queryString = mBookInput.getText().toString();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        // create a new FETCH or SEARCH for the input
        searchedBook = new FetchBook(mTitleText, mAuthorText);
        searchedBook.execute(queryString);

        // CREATE RECYCLE VIEW
        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);
        mNumbersList.setHasFixedSize(true);

        // copy booklist from fetchedbook into this variable
        bookList.addAll(searchedBook.getBookList());
        Log.d("IMPORTANT IMPORTANT: ", bookList.toString());
        // PASS LIST OF BOOKS INTO ITEMADAPTER CREATING RECYCLE VIEW
        mAdapter = new ItemAdapter(this, NUM_LIST_ITEMS, bookList);
        mNumbersList.setAdapter(mAdapter);
    }
    // TODO: Goal of searchBooks, it will first call FetchBook to search for books with input given, then
    // todo: retrieve the list of GoogleBookModels from Fetchbook. It should then, create the RecycleView
    // todo: passing through the bookList of GoogleBookModels in order to create the search results
}

