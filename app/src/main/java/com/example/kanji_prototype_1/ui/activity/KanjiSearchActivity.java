package com.example.kanji_prototype_1.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import com.example.kanji_prototype_1.R;

import androidx.appcompat.app.AppCompatActivity;

public class KanjiSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_search);

        // Setup for text search and draw button logic
        setupSearch();
    }

    private void setupSearch() {
        EditText searchText = findViewById(R.id.search_text);
        Button drawButton = findViewById(R.id.draw_button);

        // Text-based search functionality
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Empty implementation (required method)
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call search function to filter results based on the input text
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
                // Empty implementation (required method)
            }
        });


        // Navigate to KanjiInputActivity for drawing
        drawButton.setOnClickListener(v -> {
            Intent intent = new Intent(KanjiSearchActivity.this, KanjiInputActivity.class);
            startActivity(intent);
        });
    }
}
