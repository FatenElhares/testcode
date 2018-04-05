package com.pixalione.monetoring.data_preprocessing.Service;

import com.pixalione.monetoring.data_preprocessing.Models.WordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Ouasmine on 03/01/2018.
 */
public class Utils {
    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    public static Set<WordException> readWordExceptionFromCsv(String csvFile, String csvSplitBy, AbstractStemmer.LANGUAGE language){
        Set<WordException> wordExceptions = new HashSet<>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                WordException wordException;

                if(data[0].endsWith("'"))
                    wordException = new WordException(data[0].trim(), data[1].trim(), data[2].trim()+ " ", language);
                else
                    wordException = new WordException(data[0].trim(), data[1].trim(), data[2].trim(), language);

                wordExceptions.add(wordException);
                logger.debug(wordException.toString());
            }
        } catch (FileNotFoundException e) {
            logger.warn("File ["+csvFile+"] not found !");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return wordExceptions;
    }

}
