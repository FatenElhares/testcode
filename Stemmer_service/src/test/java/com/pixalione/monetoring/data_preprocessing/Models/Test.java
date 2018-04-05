package com.pixalione.monetoring.data_preprocessing.Models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Created by OUASMINE Mohammed Amine on 04/01/2018.
 */
public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    private String expectedResult;
    private String input;

    public Test(String expectedResult, String input) {
        this.expectedResult = expectedResult;
        this.input = input;
    }

    public static Set<Test> importTests(String csvFile){
        return null;
        /*
        * Set<Test> testSet = new HashSet<>();
        BufferedReader br = null;
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Test test = new Test(data[1], data[0]);
                testSet.add(test);
                logger.debug(test.toString());
            }
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
        return testSet;
        * */
    }

    public String getExpectedResult() {
        return expectedResult;
    }

    public String getInput() {
        return input;
    }

    @Override
    public String toString() {
        return "Test{" +
                "expectedResult='" + expectedResult + '\'' +
                ", input='" + input + '\'' +
                '}';
    }
}
