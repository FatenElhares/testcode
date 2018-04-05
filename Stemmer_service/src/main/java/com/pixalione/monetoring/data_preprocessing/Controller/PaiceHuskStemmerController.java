package com.pixalione.monetoring.data_preprocessing.Controller;

import com.pixalione.monetoring.data_preprocessing.Models.StemmResult;
import com.pixalione.monetoring.data_preprocessing.Service.AbstractStemmer;
import com.pixalione.monetoring.data_preprocessing.Service.PaiceHuskStemmerService;
import com.pixalione.monetoring.data_preprocessing.Service.ServiceTextOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Ouasmine on 09/01/2018.
 */
@RestController
public class PaiceHuskStemmerController {

    @Autowired
    private PaiceHuskStemmerService paiceHuskStemmerService;
    @Autowired
    private ServiceTextOperations serviceTextOperations;

    @GetMapping("StemmWithHusk")
    public StemmResult StemmWithHusk(@RequestParam String word){
        return paiceHuskStemmerService.doStemm(AbstractStemmer.LANGUAGE.french, word);
    }

    @GetMapping("StemmWithHuskWithNormalization")
    public StemmResult StemmWithHuskWithNormalization(@RequestParam String word) {
        return serviceTextOperations.doNormalizedLemmaResult(paiceHuskStemmerService.doStemm(AbstractStemmer.LANGUAGE.french, word));
    }
}
