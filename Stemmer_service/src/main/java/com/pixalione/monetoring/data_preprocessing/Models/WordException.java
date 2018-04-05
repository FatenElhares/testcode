package com.pixalione.monetoring.data_preprocessing.Models;


import com.pixalione.monetoring.data_preprocessing.Service.SnowballStemmerService;

/**
 * Created by Ouasmine on 07/12/2017.
 */
public class WordException {

    private String origin;
    private String type;
    private String lemma;
    private SnowballStemmerService.LANGUAGE language;

    public WordException(String origin, String type, String lemma, SnowballStemmerService.LANGUAGE language) {
        this.origin = origin;
        this.type = type;
        this.lemma = lemma;
        this.language = language;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public SnowballStemmerService.LANGUAGE getLanguage() {
        return language;
    }

    public void setLanguage(SnowballStemmerService.LANGUAGE language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "WordException{" +
                "origin='" + origin + '\'' +
                ", type='" + type + '\'' +
                ", lemma='" + lemma + '\'' +
                ", language=" + language +
                '}';
    }
}
