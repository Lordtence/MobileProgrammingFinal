package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class SearchedActivity extends AppCompatActivity implements Serializable {
    private GoogleBookModel book;
    private TextView mTitleText;
    private TextView mAuthorText;
    private TextView mSubtitleText;
    private TextView mDescriptionText;
    private TextView mPublisherText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched);
        // get intent
        Intent intent = getIntent();

        // implement intent into book object
        book = (GoogleBookModel)intent.getSerializableExtra("MyClass");

        // Set the text fields to the information from Book object
        mTitleText = findViewById(R.id.title_text);
        mTitleText.setText(book.getTitle());

        mAuthorText = findViewById(R.id.authors_text);
        mAuthorText.setText(book.getAuthors());

        mSubtitleText = findViewById(R.id.subtitle_text);
        mSubtitleText.setText(book.getSubtitle());

        mDescriptionText = findViewById(R.id.description_text);
        mDescriptionText.setText(book.getDescription());

        mPublisherText = findViewById(R.id.publisher_text);
        mPublisherText.setText(book.getPublisher());

    }
}
