package com.example.kanji_prototype_1.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.kanji_prototype_1.R;
import com.example.kanji_prototype_1.ui.data.KanjiDataRepository;
import com.example.kanji_prototype_1.ui.model.Kanji;

import java.util.List;

public class KanjiDetailsActivity extends AppCompatActivity {

    private KanjiDataRepository kanjiDataRepository;
    private List<Kanji> kanjiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_details);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize KanjiDataRepository and load Kanji list
        kanjiDataRepository = new KanjiDataRepository(this);
        kanjiList = kanjiDataRepository.getKanjiList(); // Load from JSON

        // Get the Kanji ID passed from the previous activity
        int kanjiId = getIntent().getIntExtra("KANJI_ID", -1);

        // Fetch the Kanji object by its ID
        Kanji kanji = getKanjiById(kanjiId);

        if (kanji != null) {
            populateKanjiDetails(kanji); // Populate UI with Kanji details
        }
    }

    // Method to get Kanji by its ID
    private Kanji getKanjiById(int id) {
        if (kanjiList != null) {
            for (Kanji kanji : kanjiList) {
                if (kanji.getId() == id) {
                    return kanji;
                }
            }
        }
        return null; // Return null if Kanji with the given ID is not found
    }

    // Method to populate Kanji details in the UI
    private void populateKanjiDetails(Kanji kanji) {
        // Populate the Kanji details in the layout
        TextView kanjiCharacter = findViewById(R.id.kanji_character);
        TextView heisigDescription = findViewById(R.id.heisig_description);
        TextView onyomiReading = findViewById(R.id.onyomi_reading);
        TextView kunyomiReading = findViewById(R.id.kunyomi_reading);
        TextView meaning = findViewById(R.id.meaning);
        TextView example1 = findViewById(R.id.example_1);
        TextView example2 = findViewById(R.id.example_2);
        ImageView strokeOrderGif = findViewById(R.id.stroke_order_gif);

        // Set Kanji details
        kanjiCharacter.setText(kanji.getKanji());
        heisigDescription.setText("Heisig: " + kanji.getHeisig());
        onyomiReading.setText("Onyomi: " + String.join(", ", kanji.getOnyomi()));
        kunyomiReading.setText("Kunyomi: " + String.join(", ", kanji.getKunyomi()));
        meaning.setText("Meaning: " + kanji.getMeaning());
        example1.setText(kanji.getExamples().size() > 0 ? kanji.getExamples().get(0) : "");
        example2.setText(kanji.getExamples().size() > 1 ? kanji.getExamples().get(1) : "");

        // Optional: Load the stroke order GIF if available
        // Glide.with(this).load(kanji.getStrokeOrderGif()).into(strokeOrderGif);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();  // Navigate back to previous activity
        return true;
    }
}
