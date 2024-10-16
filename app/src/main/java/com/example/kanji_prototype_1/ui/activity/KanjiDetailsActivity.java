package com.example.kanji_prototype_1.ui.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.kanji_prototype_1.R;
import com.example.kanji_prototype_1.data.KanjiDataRepository;
import com.example.kanji_prototype_1.ui.model.Kanji;

public class KanjiDetailsActivity extends AppCompatActivity {

    private KanjiDataRepository kanjiDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kanji_details);

        kanjiDataRepository = new KanjiDataRepository(this);

        // Get the Kanji ID passed from the previous activity
        int kanjiId = getIntent().getIntExtra("KANJI_ID", -1);

        // Fetch Kanji data using the ID
        Kanji kanji = kanjiDataRepository.getKanjiById(kanjiId);

        if (kanji != null) {
            populateKanjiDetails(kanji);
        }
    }

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
        kanjiCharacter.setText(kanji.getCharacter());
        heisigDescription.setText("Heisig Description: " + kanji.getHeisig());
        onyomiReading.setText("Onyomi: " + String.join(", ", kanji.getOnyomi()));
        kunyomiReading.setText("Kunyomi: " + String.join(", ", kanji.getKunyomi()));
        meaning.setText("Meaning: " + kanji.getMeaning());
        example1.setText(kanji.getExamples().get(0));
        example2.setText(kanji.getExamples().get(1));

        // Load the stroke order GIF (using Glide or another image loader)
        Glide.with(this).load(kanji.getStrokeOrderGif()).into(strokeOrderGif);
    }
}
