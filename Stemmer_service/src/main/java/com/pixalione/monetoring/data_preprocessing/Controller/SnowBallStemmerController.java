package com.pixalione.monetoring.data_preprocessing.Controller;

import com.pixalione.monetoring.data_preprocessing.Models.StemmResult;
import com.pixalione.monetoring.data_preprocessing.Service.ServiceTextOperations;
import com.pixalione.monetoring.data_preprocessing.Service.SnowballStemmerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ouasmine on 07/12/2017.
 */
@RestController
public class SnowBallStemmerController {

    @Autowired
    private SnowballStemmerService snowballStemmerService;
    @Autowired
    private ServiceTextOperations serviceTextOperations;

    @GetMapping("Stemm")
    public StemmResult Stemm(@RequestParam SnowballStemmerService.LANGUAGE language, @RequestParam String word){
        return snowballStemmerService.doStemm(language, word);
    }

    @GetMapping("StemmWithNormalization")
    public StemmResult StemmWithNormalization(@RequestParam SnowballStemmerService.LANGUAGE language, @RequestParam String word){
        return serviceTextOperations.doNormalizedLemmaResult(snowballStemmerService.doStemm(language, word));
    }

}
