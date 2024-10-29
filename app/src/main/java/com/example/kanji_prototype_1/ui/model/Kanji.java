package com.example.kanji_prototype_1.ui.model;

import java.util.List;

public class Kanji {
    private int id;
    private String kanji;  // Matches JSON field name
    private String heisig;
    private String meaning;
    private List<String> onyomi;
    private List<String> kunyomi;
    private List<String> examples;
    private String strokeOrderGif;

    public Kanji(int id, String kanji, String heisig, String meaning, List<String> onyomi, List<String> kunyomi, List<String> examples, String strokeOrderGif) {
        this.id = id;
        this.kanji = kanji;
        this.heisig = heisig;
        this.meaning = meaning;
        this.onyomi = onyomi;
        this.kunyomi = kunyomi;
        this.examples = examples;
        this.strokeOrderGif = strokeOrderGif;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getKanji() { return kanji; }
    public String getHeisig() { return heisig; }
    public String getMeaning() { return meaning; }
    public List<String> getOnyomi() { return onyomi; }
    public List<String> getKunyomi() { return kunyomi; }
    public List<String> getExamples() { return examples; }
    public String getStrokeOrderGif() { return strokeOrderGif; }
}
