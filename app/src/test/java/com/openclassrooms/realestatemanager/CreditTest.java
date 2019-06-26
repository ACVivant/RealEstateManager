package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Anne-Charlotte Vivant on 26/06/2019.
 */
public class CreditTest {

    CreditActivity test = new CreditActivity();

    @Test
    public void  calculateMonth_test() {
        double input = 100000;
        double interest = 10;
        int lenght = 10;

        assertEquals("1321.51", String.valueOf(test.calculateMonth(input, interest, lenght)));
    }

    @Test
    public void calculateCost_test() {
        double input = 100000;
        double interest = 10;
        int lenght = 10;
        double month = 1321.51;

        assertEquals("58581.2", String.valueOf(test.calculateCost(input, interest, lenght, month)));
    }
}
