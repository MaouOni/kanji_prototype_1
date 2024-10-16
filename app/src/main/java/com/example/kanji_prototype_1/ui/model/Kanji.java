package com.example.kanji_prototype_1.ui.model;

import java.util.List;

public class Kanji {
    private int id;
    private String character;
    private String heisig;
    private String meaning;
    private List<String> onyomi;
    private List<String> kunyomi;
    private List<String> examples;
    private String strokeOrderGif;

    public Kanji(int i, String 二, String two, String s, String[] strings, String[] strings1, String[] strings2, String s1) {
    }

    // Getters and setters for each field
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getHeisig() {
        return heisig;
    }

    public void setHeisig(String heisig) {
        this.heisig = heisig;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public List<String> getOnyomi() {
        return onyomi;
    }

    public void setOnyomi(List<String> onyomi) {
        this.onyomi = onyomi;
    }

    public List<String> getKunyomi() {
        return kunyomi;
    }

    public void setKunyomi(List<String> kunyomi) {
        this.kunyomi = kunyomi;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public String getStrokeOrderGif() {
        return strokeOrderGif;
    }

    public void setStrokeOrderGif(String strokeOrderGif) {
        this.strokeOrderGif = strokeOrderGif;
    }
}
