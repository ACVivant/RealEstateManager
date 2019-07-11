package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Property;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.junit.Assert.assertTrue;

/**
 * Created by Anne-Charlotte Vivant on 13/05/2019.
 */
//@RunWith(AndroidJUnit4.class)
public class PropertyDaoTest {

    // FOR DATA
    private PropertyDao mPropertyDao;
    private RealEstateDatabase database;

    // DATA SET FOR TEST
    private static long PROPERTY_ID = 1;
    private static String url = "https://www.entre-hotes.com/images/chambres/insolite/chambre-7701.jpg?auto=compress,format&q=80&h=100&dpr=2";
    private static Property PROPERTY_DEMO = new Property(100000, 4, 3, 2, "property démo", 20190628, 20190629, 85, false, false, false, true, 1, 2, 3, url, 4, "6", "rue Alexandre Dumas", "Appt 5", "60800", "Crépy-en-Valois", "France" );


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                RealEstateDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void testInsertAndGetUser() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.propertyDao().insertProperty(PROPERTY_DEMO);
        // TEST
        Property property = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertyFromId(1));
        assertTrue(property.getTown().equals(PROPERTY_DEMO.getTown()) && property.getPropertyId() == PROPERTY_ID);
    }
}

