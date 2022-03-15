package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.List;

public class SearchedActivity extends AppCompatActivity implements Serializable {
    VolumeInfo recievedVolumeInfo;
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
        recievedVolumeInfo = (VolumeInfo)intent.getSerializableExtra("MainActivity");

        // Set the text fields to the information from Book object
        mTitleText = findViewById(R.id.title_text);
        mTitleText.setText(recievedVolumeInfo.getTitle());

        mAuthorText = findViewById(R.id.authors_text);
        List<String> setOfAuthors = recievedVolumeInfo.getAuthors();
        mAuthorText.setText(setOfAuthors.get(0));

        mSubtitleText = findViewById(R.id.subtitle_text);
        mSubtitleText.setText(recievedVolumeInfo.getPublishedDate());

        mDescriptionText = findViewById(R.id.description_text);
        mDescriptionText.setText(recievedVolumeInfo.getDescription());

        mPublisherText = findViewById(R.id.publisher_text);
        mPublisherText.setText(recievedVolumeInfo.getPublisher());

    }
}
