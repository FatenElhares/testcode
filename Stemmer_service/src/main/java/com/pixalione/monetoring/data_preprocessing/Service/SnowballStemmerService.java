package com.pixalione.monetoring.data_preprocessing.Service;

import com.pixalione.monetoring.data_preprocessing.Models.StemmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.*;

/**
 * Created by Ouasmine on 07/12/2017.
 */
@Service
public class SnowballStemmerService extends AbstractStemmer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String stemmOneWord(LANGUAGE language, String word) {
        SnowballStemmer snowballStemmer;
        switch (language){
            case english:
                snowballStemmer = new englishStemmer();
                break;
            case french:
                snowballStemmer = new frenchStemmer();
                break;
            case italian:
                snowballStemmer = new italianStemmer();
                break;
            case romanian:
                snowballStemmer = new romanianStemmer();
                break;
            case russian:
                snowballStemmer = new russianStemmer();
                break;
            case spanish:
                snowballStemmer = new spanishStemmer();
                break;
            case portuguese:
                snowballStemmer = new portugueseStemmer();
                break;
            default:
                snowballStemmer = new englishStemmer();
                break;
        }
        logger.info("Stemming the Words [" + word + "] On Language [" + language + "]");
        snowballStemmer.setCurrent(word);
        snowballStemmer.stem();
        return snowballStemmer.getCurrent();
    }

    @Override
    public StemmResult doStemm(LANGUAGE language, String words) {
        return stemm(language, words);
    }
}
