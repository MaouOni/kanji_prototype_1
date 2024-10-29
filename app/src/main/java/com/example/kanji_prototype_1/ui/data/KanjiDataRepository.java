package com.example.kanji_prototype_1.ui.data;

import android.content.Context;
import android.util.Log;
import com.example.kanji_prototype_1.ui.model.Kanji;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class KanjiDataRepository {

    private List<Kanji> kanjiList;

    public KanjiDataRepository(Context context) {
        loadKanjiList(context);
    }

    private void loadKanjiList(Context context) {
        try {
            InputStream is = context.getAssets().open("kanji_data.json");
            InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8); // Set encoding to UTF-8
            Type listType = new TypeToken<List<Kanji>>() {}.getType();
            kanjiList = new Gson().fromJson(reader, listType);
            reader.close();
            is.close();

            // Log each Kanji character to confirm parsing
            for (Kanji kanji : kanjiList) {
                String kanjiCharacter = kanji.getKanji() != null ? kanji.getKanji() : "Unknown";
                String heisigDescription = kanji.getHeisig() != null ? kanji.getHeisig() : "Unknown";
                Log.d("KanjiDataRepository", "Loaded Kanji: " + kanjiCharacter + " - Heisig: " + heisigDescription);
            }

        } catch (Exception e) {
            Log.e("KanjiDataRepository", "Error loading Kanji data", e);
        }
    }

    public List<Kanji> getKanjiList() {
        return kanjiList;
    }
}
