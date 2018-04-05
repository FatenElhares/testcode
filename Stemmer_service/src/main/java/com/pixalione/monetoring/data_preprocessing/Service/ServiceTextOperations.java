package com.pixalione.monetoring.data_preprocessing.Service;

import com.pixalione.monetoring.data_preprocessing.Models.StemmResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Ouasmine on 02/01/2018.
 */
@Service
public class ServiceTextOperations {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String doNormalizedText(String textToNormaliz) {
        return org.apache.commons.lang3.StringUtils.stripAccents(textToNormaliz).toLowerCase();
    }

    public StemmResult doNormalizedLemmaResult(StemmResult stemmResult) {
        String resultNormalization = doNormalizedText(stemmResult.getValue());
        logger.info("Normalizing ... (" + stemmResult.getValue() + ") -> (" + resultNormalization + ")");
        stemmResult.setValue(resultNormalization);
        return stemmResult;
    }
}
