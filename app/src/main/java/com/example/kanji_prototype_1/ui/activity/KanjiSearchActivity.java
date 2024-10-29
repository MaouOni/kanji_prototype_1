package com.example.kanji_prototype_1.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kanji_prototype_1.R;
import com.example.kanji_prototype_1.ui.adapter.KanjiAdapter;
import com.example.kanji_prototype_1.ui.data.KanjiDataRepository;
import com.example.kanji_prototype_1.ui.model.Kanji;
import java.util.ArrayList;
import java.util.List;

public class KanjiSearchActivity extends AppCompatActivity {

    private List<Kanji> kanjiList;
    private KanjiAdapter kanjiAdapter;
    private RecyclerView recyclerView;
    private EditText searchText;
    private KanjiDataRepository kanjiDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_search);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize data from KanjiDataRepository
        kanjiDataRepository = new KanjiDataRepository(this);
        kanjiList = kanjiDataRepository.getKanjiList();

        // Initialize UI components
        searchText = findViewById(R.id.search_text);
        Button sendButton = findViewById(R.id.send_button);

        // Initialize RecyclerView with the adapter and click listener
        recyclerView = findViewById(R.id.kanji_results_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        kanjiAdapter = new KanjiAdapter(new ArrayList<>(), this::showKanjiDetails);
        recyclerView.setAdapter(kanjiAdapter);

        // Set up search function for the send button and editor action
        searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        sendButton.setOnClickListener(v -> performSearch());
    }

    private void performSearch() {
        String query = searchText.getText().toString().trim();
        if (!query.isEmpty()) {
            filterResults(query);
        }
    }

    private void filterResults(String query) {
        List<Kanji> filteredList = new ArrayList<>();
        for (Kanji kanji : kanjiList) {
            if (kanji.getKanji().contains(query) || kanji.getHeisig().toLowerCase().contains(query.toLowerCase()) || kanji.getMeaning().toLowerCase().contains(query.toLowerCase()) ||
                    kanji.getOnyomi().stream().anyMatch(onyomi -> onyomi.contains(query)) || kanji.getKunyomi().stream().anyMatch(kunyomi -> kunyomi.contains(query))) {
                filteredList.add(kanji);
            }
        }
        kanjiAdapter.updateData(filteredList);
    }

    // Launch KanjiDetailsActivity with selected Kanji's ID
    private void showKanjiDetails(Kanji kanji) {
        Intent intent = new Intent(this, KanjiDetailsActivity.class);
        intent.putExtra("KANJI_ID", kanji.getId());
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}