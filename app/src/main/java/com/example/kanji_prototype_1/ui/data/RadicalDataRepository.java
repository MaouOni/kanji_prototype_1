package com.example.kanji_prototype_1.ui.data;

import android.content.Context;
import com.example.kanji_prototype_1.ui.model.Radical;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RadicalDataRepository {

    private Context context;
    private List<Radical> radicalList;

    public RadicalDataRepository(Context context) {
        this.context = context;
        loadRadicalData();
    }

    private void loadRadicalData() {
        try {
            InputStream is = context.getAssets().open("radicals_data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            radicalList = new Gson().fromJson(json, new TypeToken<List<Radical>>() {}.getType());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Radical getRadicalById(int id) {
        for (Radical radical : radicalList) {
            if (radical.getId() == id) {
                return radical;
            }
        }
        return null;
    }
}
