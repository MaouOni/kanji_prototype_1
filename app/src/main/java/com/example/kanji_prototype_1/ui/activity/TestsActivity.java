package com.example.kanji_prototype_1.ui.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.kanji_prototype_1.R;

public class TestsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);  // This is the androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolbar);  // Sets the toolbar as the ActionBar

        // Enable back button on the toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();  // Navigate back to previous activity
        return true;
    }
}
