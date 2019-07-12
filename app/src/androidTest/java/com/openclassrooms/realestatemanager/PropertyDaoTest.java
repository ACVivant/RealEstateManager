package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.database.RealEstateDatabase;
import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

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
    private static Agent AGENT_1 = new Agent("Agent1");
    private static Agent AGENT_2 = new Agent("Agent2");
    private static Agent AGENT_3 = new Agent("Agent3");
    private static Status STATUS_1 = new Status("Status1");
    private static Status STATUS_2 = new Status("Status2");
    private static Status STATUS_3 = new Status("Status3");
    private static TypeOfProperty TYPE_1 = new TypeOfProperty("Type1");
    private static TypeOfProperty TYPE_2 = new TypeOfProperty("Type2");
    private static TypeOfProperty TYPE_3 = new TypeOfProperty("Type3");
    private static Property PROPERTY_DEMO = new Property(100000, 4, 3, 2, "property démo", 20190628, 20190629, 85, false, false, false, true, 1, 2, 2, url, 4, "6", "rue Alexandre Dumas", "Appt 5", "60800", "Crépy-en-Valois", "France" );
    private static Property PROPERTY_DEMO_2 = new Property(50000, 4, 3, 2, "property démo", 20190628, 20190629, 85, false, false, false, true, 1, 2, 2, url, 4, "6", "rue Alexandre Dumas", "Appt 5", "60800", "Crépy-en-Valois", "France" );


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                RealEstateDatabase.class)
                .allowMainThreadQueries()
                .build();

        // BEFORE : Adding  new property, type, status and agent
        this.database.agentDao().insertAgent(AGENT_1);
        this.database.agentDao().insertAgent(AGENT_2);
        this.database.agentDao().insertAgent(AGENT_3);

        this.database.typeOfPropertyDao().insertType(TYPE_1);
        this.database.typeOfPropertyDao().insertType(TYPE_2);
        this.database.typeOfPropertyDao().insertType(TYPE_3);

        this.database.statusDao().insertStatus(STATUS_1);
        this.database.statusDao().insertStatus(STATUS_2);
        this.database.statusDao().insertStatus(STATUS_3);

        this.database.propertyDao().insertProperty(PROPERTY_DEMO);
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void testInsertAndGetUser() throws InterruptedException {

        // TEST
        Property property = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertyFromId(1));
        assertTrue(property.getTown().equals(PROPERTY_DEMO.getTown()) && property.getPropertyId() == PROPERTY_ID);
    }

    @Test
    public void testInsertAndGetAgents() throws InterruptedException {
        List<Agent> items = LiveDataTestUtil.getValue(this.database.agentDao().getAllAgent());
        assertTrue(items.size() == 3);
    }

    @Test
    public void testInsertAndGetTypes() throws InterruptedException {
        List<TypeOfProperty> items = LiveDataTestUtil.getValue(this.database.typeOfPropertyDao().getAllTypes());
        assertTrue(items.size() == 3);
    }

    @Test
    public void testUpdateProperty() throws InterruptedException {
        PROPERTY_DEMO_2.setPropertyId(1);
        this.database.propertyDao().updateProperty(PROPERTY_DEMO_2);

        // TEST
        Property property = LiveDataTestUtil.getValue(this.database.propertyDao().getPropertyFromId(1));
        assertTrue(property.getPrice()==50000);
    }

    @Test
    public void testInsertProperty() throws InterruptedException {
        // BEFORE : Adding demo user & demo item. Next, get the item added & delete it.
        Property PROPERTY_DEMO_3 = new Property(750000, 4, 3, 2, "property démo 3", 20190628, 20190629, 85, false, false, false, true, 1, 2, 2, url, 4, "6", "rue Alexandre Dumas", "Appt 5", "60800", "Crépy-en-Valois", "France" );
        this.database.propertyDao().insertProperty(PROPERTY_DEMO_3);

        //TEST
        List<Property> items = LiveDataTestUtil.getValue(this.database.propertyDao().getAllProperty());
        assertTrue(items.size() == 2);

    }
}

