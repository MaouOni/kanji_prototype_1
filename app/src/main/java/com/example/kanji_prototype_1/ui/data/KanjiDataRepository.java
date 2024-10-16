package com.example.kanji_prototype_1.ui.data;

import android.content.Context;
import com.example.kanji_prototype_1.ui.model.Kanji;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class KanjiDataRepository {

    private Context context;

    public KanjiDataRepository(Context context) {
        this.context = context;
    }

    // Method to load Kanji list from a JSON file
    public List<Kanji> getKanjiList() {
        try {
            // Open the JSON file from the assets folder
            InputStream inputStream = context.getAssets().open("kanji_data.json");
            InputStreamReader reader = new InputStreamReader(inputStream);

            // Use Gson to parse the JSON into a List of Kanji objects
            Gson gson = new Gson();
            Type kanjiListType = new TypeToken<List<Kanji>>() {}.getType();

            // Return the parsed Kanji list
            return gson.fromJson(reader, kanjiListType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
