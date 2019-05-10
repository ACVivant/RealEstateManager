package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by Anne-Charlotte Vivant on 25/04/2019.
 */

public class UtilsTest {
    Utils test = new Utils();

    @Test
    public void convertDollarToEuro_test() {
        assertEquals(81.2, test.convertDollarToEuro(100), 0.5);
    }

    @Test
    public void convertEuroToDollar_test() {
        assertEquals(123, test.convertEuroToDollar(100), 0.5);
    }

        @Test
    public void  getFormatDate_test() {

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2019);
        cal.set(Calendar.MONTH, 03); // Months are denombrated from 0 to 11
        cal.set(Calendar.DAY_OF_MONTH, 25);
        Date date = cal.getTime();

        assertEquals("25/04/2019", test.getFormatDate(date));
    }
}
