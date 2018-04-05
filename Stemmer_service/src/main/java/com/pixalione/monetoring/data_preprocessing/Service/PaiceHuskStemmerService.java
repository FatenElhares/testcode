package com.pixalione.monetoring.data_preprocessing.Service;

import com.pixalione.monetoring.data_preprocessing.Core.PaiceHuskStemmer;
import com.pixalione.monetoring.data_preprocessing.Models.StemmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Ouasmine on 09/01/2018.
 */
@Service
public class PaiceHuskStemmerService extends AbstractStemmer{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String stemmOneWord(SnowballStemmerService.LANGUAGE language, String word) {
        switch (language){
            case french:
                logger.info("Stemming the Words [" + word + "] On Language [" + language + "]");
                return PaiceHuskStemmer.stemPaiceHusk(word);
            default:
                logger.error("PaiceHusk Stemmer not support language : " + language);
                return null;
        }
    }

    @Override
    public StemmResult doStemm(LANGUAGE language, String words) {
        return stemm(language, words);
    }
}
