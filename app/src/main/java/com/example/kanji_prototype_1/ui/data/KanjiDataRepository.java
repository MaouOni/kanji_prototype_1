package com.example.kanji_prototype_1.data;

import android.content.Context;
import com.example.kanji_prototype_1.ui.model.Kanji;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class KanjiDataRepository {

    private Context context;
    private List<Kanji> kanjiList;

    public KanjiDataRepository(Context context) {
        this.context = context;
        loadKanjiData();
    }

    private void loadKanjiData() {
        try {
            // Load kanji_data.json from the assets folder
            InputStream is = context.getAssets().open("kanji_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            // Parse the JSON data
            kanjiList = new Gson().fromJson(json, new TypeToken<List<Kanji>>() {}.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch Kanji by ID
    public Kanji getKanjiById(int id) {
        for (Kanji kanji : kanjiList) {
            if (kanji.getId() == id) {
                return kanji;
            }
        }
        return null;
    }
}
