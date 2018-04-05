package com.pixalione.monetoring.data_preprocessing.Service;

import com.pixalione.monetoring.data_preprocessing.Models.StemmResult;
import com.pixalione.monetoring.data_preprocessing.Models.WordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

interface StemmerImpl {
    StemmResult doStemm(AbstractStemmer.LANGUAGE language, String words);
}

/**
 * Created by Ouasmine on 09/01/2018.
 */
@Component
public abstract class AbstractStemmer implements StemmerImpl {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${nlp.exception.word}")
    protected String ExceptionsWord;
    protected Map<LANGUAGE, Set<WordException>> wordsExceptionByLanguage;

    @PostConstruct
    private void Init() {
        InitFilterException();
    }

    protected void InitFilterException() {
        wordsExceptionByLanguage = new HashMap<>();
        EnumSet.allOf(LANGUAGE.class).parallelStream().forEach(
                language -> wordsExceptionByLanguage.put(language,
                        Utils.readWordExceptionFromCsv(ExceptionsWord.replace("{LANGUAGE}", language.name()), ",", language))
        );
    }

    protected String checkTheException(LANGUAGE language, Stream<String> words) {
        return String.join(" ", words.parallel().map(word -> replaceException(language, word)).collect(Collectors.toList()));
    }

    protected String replaceException(LANGUAGE language, String target) {
        for (WordException exc : this.wordsExceptionByLanguage.get(language)) {
            if (target.toLowerCase().contains(exc.getOrigin())) {
                String resultException = target.replace(exc.getOrigin(), exc.getLemma());
                logger.info("Exception found [" + exc.getOrigin() + "] - [" + target.toLowerCase() + "] => [" + resultException + "]");

                if (resultException.contains(" "))
                    return stemm(language, resultException).getValue();
                else
                    return resultException;
            }
        }
        return "";
    }

    protected abstract String stemmOneWord(LANGUAGE language, String word);

    protected StemmResult stemm(LANGUAGE language, String words) {
        String[] tokens = words.split(" ");
        return new StemmResult(language, String.join(" ", Arrays.stream(tokens).
                map(word -> {
                    String exception = replaceException(language, word);

                    if (exception.isEmpty())
                        return stemmOneWord(language, word);
                    else
                        return exception;
                }).
                collect(Collectors.toList())));
    }

    public enum LANGUAGE { french,english,italian,romanian,russian,spanish,portuguese }
}
