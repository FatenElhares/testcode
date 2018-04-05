package com.pixalione.monetoring.data_preprocessing.Models;


import com.pixalione.monetoring.data_preprocessing.Service.AbstractStemmer;

/**
 * Created by Ouasmine on 02/01/2018.
 */
public class StemmResult {
    private String value;
    private String language;

    public StemmResult(AbstractStemmer.LANGUAGE language,String value) {
        this.value = value;
        this.language = language.name();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
