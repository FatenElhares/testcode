package com.pixalione.monetoring.data_preprocessing.businessTest;

import com.pixalione.monetoring.data_preprocessing.Controller.SnowBallStemmerController;
import com.pixalione.monetoring.data_preprocessing.Service.SnowballStemmerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by OUASMINE Mohammed Amine on 04/01/2018.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class NazaninTest {

    @Autowired
    private SnowBallStemmerController snowBallStemmerController;

    private Set<com.pixalione.monetoring.data_preprocessing.Models.Test> testSet;
    @Before
    public void setUp() throws Exception {
        this.testSet = com.pixalione.monetoring.data_preprocessing.Models.Test.importTests("BusinessTests/test - ws.csv");
    }

    @Test
    public void NazaninBusinessTest() throws Exception {
        if(this.testSet != null){
            for(com.pixalione.monetoring.data_preprocessing.Models.Test test : testSet){
                testUnit(test);
            }
        }

    }

    private void testUnit(com.pixalione.monetoring.data_preprocessing.Models.Test test) throws Exception {
        assertEquals(test.getExpectedResult(),
                snowBallStemmerController.StemmWithNormalization(
                        SnowballStemmerService.LANGUAGE.french, test.getInput()).getValue());
    }
}
