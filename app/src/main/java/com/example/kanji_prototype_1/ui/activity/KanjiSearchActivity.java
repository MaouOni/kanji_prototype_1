package com.example.kanji_prototype_1.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kanji_prototype_1.R;
import com.example.kanji_prototype_1.ui.data.KanjiDataRepository;
import com.example.kanji_prototype_1.ui.adapter.KanjiAdapter;
import com.example.kanji_prototype_1.ui.model.Kanji;
import com.example.kanji_prototype_1.ui.view.KanjiCanvasView;

import java.util.ArrayList;
import java.util.List;

public class KanjiSearchActivity extends AppCompatActivity {

    private List<Kanji> kanjiList;
    private KanjiAdapter kanjiAdapter;
    private RecyclerView recyclerView;
    private KanjiCanvasView kanjiCanvasView;
    private EditText searchText;
    private KanjiDataRepository kanjiDataRepository; // New

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_search);

        // Initialize data from KanjiDataRepository
        kanjiDataRepository = new KanjiDataRepository(this);
        kanjiList = kanjiDataRepository.getKanjiList(); // Load from JSON

        // Initialize UI components
        searchText = findViewById(R.id.search_text);
        kanjiCanvasView = findViewById(R.id.drawing_canvas);
        View drawingContainer = findViewById(R.id.drawing_container);
        Button drawButton = findViewById(R.id.draw_button);
        Button sendButton = findViewById(R.id.send_button);

        // Initialize RecyclerView with an empty list
        recyclerView = findViewById(R.id.kanji_results_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kanjiAdapter = new KanjiAdapter(new ArrayList<>()); // Start with an empty list
        recyclerView.setAdapter(kanjiAdapter);

        // Show drawing canvas when draw button is clicked
        drawButton.setOnClickListener(v -> {
            drawingContainer.setVisibility(View.VISIBLE);
        });

        // Send button logic for text search or drawing submission
        sendButton.setOnClickListener(v -> {
            String query = searchText.getText().toString();

            if (!query.isEmpty()) {
                // Perform text-based search
                filterResults(query);
            } else if (kanjiCanvasView.getVisibility() == View.VISIBLE) {
                // Process drawing and search the result
                Bitmap kanjiBitmap = kanjiCanvasView.getBitmap(); // Get the drawn Kanji bitmap
                String drawnKanji = processDrawingToKanji(kanjiBitmap); // Mocked processing method

                // Set the drawn Kanji to the search text
                searchText.setText(drawnKanji);

                // Perform search with the drawn Kanji
                filterResults(drawnKanji);
            } else {
                Toast.makeText(KanjiSearchActivity.this, "Ingrese texto o dibuje un kanji", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Filter Kanji list based on search input
    private void filterResults(String query) {
        List<Kanji> filteredList = new ArrayList<>();
        for (Kanji kanji : kanjiList) {
            if (kanji.getCharacter() != null && kanji.getCharacter().contains(query)) {
                filteredList.add(kanji);
            }
        }
        kanjiAdapter.updateData(filteredList);
    }

    // Mock method to process drawn Kanji into a character (replace with actual neural network)
    private String processDrawingToKanji(Bitmap bitmap) {
        // Simulate drawing recognition (use random Kanji for now)
        return kanjiList.get(0).getCharacter(); // Return the first Kanji character for now
    }
}
