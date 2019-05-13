package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;

import com.openclassrooms.realestatemanager.database.dao.AddressDao;
import com.openclassrooms.realestatemanager.database.dao.AgentDao;
import com.openclassrooms.realestatemanager.database.dao.AttractingPointDao;
import com.openclassrooms.realestatemanager.database.dao.PhotoDao;
import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.database.dao.StatusDao;
import com.openclassrooms.realestatemanager.database.dao.TypeOfPropertyDao;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.AttractingPoint;
import com.openclassrooms.realestatemanager.models.AttractingPropertyJoin;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import org.w3c.dom.Attr;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Created by Anne-Charlotte Vivant on 10/05/2019.
 */
@Database(entities =
        {Address.class, Agent.class, AttractingPoint.class, AttractingPropertyJoin.class, Photo.class, Property.class, Status.class, TypeOfProperty.class},
        version = 1,
        exportSchema = false)
public abstract class RealEstateDatabase extends RoomDatabase {

    // --- SINGLETON ---
    private static volatile RealEstateDatabase INSTANCE;

    // --- DAO ---
    public abstract AddressDao addressDao();
    public abstract AgentDao agentDao();
    public abstract AttractingPointDao attractingPointDao();
    public abstract PhotoDao photoDao();
    public abstract PropertyDao propertyDao();
    public abstract StatusDao statusDao();
    public abstract TypeOfPropertyDao typeOfPropertyDao();

    // --- INSTANCE ---
    public static RealEstateDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RealEstateDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateDatabase.class, "real_estate_database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(prepopulateDatabase())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                //Address
                ContentValues addressContentValues = new ContentValues();
                addressContentValues.put("numberInStreet", "6");
                addressContentValues.put("street", "rue Alexandre Dumas");
                addressContentValues.put("street2", "Appt 5");
                addressContentValues.put("zipcode", "60800");
                addressContentValues.put("town", "Crépy-en-Valois");
                addressContentValues.put("country","France");

                db.insert("address",OnConflictStrategy.IGNORE, addressContentValues );

                ContentValues address1ContentValues = new ContentValues();
                address1ContentValues.put("numberInStreet", "10");
                address1ContentValues.put("street", "rue de Soissons");
                address1ContentValues.put("street2", "");
                address1ContentValues.put("zipcode", "60800");
                address1ContentValues.put("town", "Crépy-en-Valois");
                address1ContentValues.put("country","France");

                db.insert("address",OnConflictStrategy.IGNORE, address1ContentValues );

                //Agent
                ContentValues agentContentValues = new ContentValues();
                agentContentValues.put("agentName", "Pierre Robes");
                db.insert("agent",OnConflictStrategy.IGNORE, agentContentValues );

                ContentValues agent1ContentValues = new ContentValues();
                agent1ContentValues.put("agentName", "Ines Pairé");
                db.insert("agent",OnConflictStrategy.IGNORE, agent1ContentValues );


                // Attracting points
                ContentValues attractContentValues = new ContentValues();
                attractContentValues.put("attractType", "school");
                db.insert("attracting_point",OnConflictStrategy.IGNORE, attractContentValues );

                ContentValues attract1ContentValues = new ContentValues();
                attract1ContentValues.put("attractType", "shops");
                db.insert("attracting_point",OnConflictStrategy.IGNORE, attract1ContentValues );

                ContentValues attract2ContentValues = new ContentValues();
                attract2ContentValues.put("attractType", "park");
                db.insert("attracting_point",OnConflictStrategy.IGNORE, attract2ContentValues );

                ContentValues attract3ContentValues = new ContentValues();
                attract3ContentValues.put("attractType", "museum");
                db.insert("attracting_point",OnConflictStrategy.IGNORE, attract3ContentValues );

                //Status
                ContentValues statusContentValues = new ContentValues();
                statusContentValues.put("statusText", "en vente");
                db.insert("status",OnConflictStrategy.IGNORE, statusContentValues );

                ContentValues status1ContentValues = new ContentValues();
                status1ContentValues.put("statusText", "compromis signé");
                db.insert("status",OnConflictStrategy.IGNORE, status1ContentValues );

                ContentValues status2ContentValues = new ContentValues();
                status2ContentValues.put("statusText", "vendu");
                db.insert("status",OnConflictStrategy.IGNORE, status2ContentValues );

                // Type of property
                ContentValues typeContentValues = new ContentValues();
                typeContentValues.put("typeText", "appartement");
                db.insert("type_of_property",OnConflictStrategy.IGNORE, typeContentValues );

                ContentValues type1ContentValues = new ContentValues();
                type1ContentValues.put("typeText", "loft");
                db.insert("type_of_property",OnConflictStrategy.IGNORE, type1ContentValues );

                ContentValues type2ContentValues = new ContentValues();
                type2ContentValues.put("typeText", "maison");
                db.insert("type_of_property",OnConflictStrategy.IGNORE, type2ContentValues );

                ContentValues type3ContentValues = new ContentValues();
                type3ContentValues.put("typeText", "manoir");
                db.insert("type_of_property",OnConflictStrategy.IGNORE, type3ContentValues );

                ContentValues type4ContentValues = new ContentValues();
                type4ContentValues.put("typeText", "château");
                db.insert("type_of_property",OnConflictStrategy.IGNORE, type4ContentValues );

                // Property
                ContentValues propertyContentValues = new ContentValues();
                propertyContentValues.put("price", 450000);
                propertyContentValues.put("rooms", 3);
                propertyContentValues.put("bedrooms", 1);
                propertyContentValues.put("bathrooms", 1);
                propertyContentValues.put("description", "une affaire à ne pas manquer, design, charme et localisation extraordinaire!!!");
                propertyContentValues.put("upForSaleDate", 20181002);
                propertyContentValues.put("soldOnDate", 20181005);
                propertyContentValues.put("surface", 75);
                propertyContentValues.put("type", 1);
                propertyContentValues.put("address", 1);
                propertyContentValues.put("agent", 1);
                propertyContentValues.put("status",3);
                db.insert("property",OnConflictStrategy.IGNORE, propertyContentValues );

                ContentValues property1ContentValues = new ContentValues();
                property1ContentValues.put("price", 578230);
                property1ContentValues.put("rooms", 5);
                property1ContentValues.put("bedrooms", 3);
                property1ContentValues.put("bathrooms", 2);
                property1ContentValues.put("description", "Quelle surpsies derrière cette façade de découvrir une maison si moderne et entièrement équipée!!!");
                property1ContentValues.put("upForSaleDate", 20190305);
                property1ContentValues.put("surface", 253);
                propertyContentValues.put("type", 3);
                propertyContentValues.put("address", 2);
                propertyContentValues.put("agent", 1);
                propertyContentValues.put("status",1);
                db.insert("property",OnConflictStrategy.IGNORE, property1ContentValues );

                // Photos
                ContentValues photoContentValues = new ContentValues();
                photoContentValues.put("photoText", "facade");
                photoContentValues.put("photoUri", "https://www.parklex.com/wp-content/uploads/2015/11/WoodviewMews-GeraghtyTaylorArchitects-London-UK-2015-Parklex-Facade-Gold-02.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photoContentValues.put("property", 1);
                db.insert("photo",OnConflictStrategy.IGNORE, photoContentValues );

                ContentValues photo1ContentValues = new ContentValues();
                photo1ContentValues.put("photoText", "living room");
                photo1ContentValues.put("photoUri", "https://img-3.journaldesfemmes.fr/qLiTvl6g4A4iw_56ntaA4-cOa1w=/819x546/smart/54dc4627fbce4251b7ffbc0702a26dc4/ccmcms-jdf/11014669.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photo1ContentValues.put("property", 1);
                db.insert("photo",OnConflictStrategy.IGNORE, photo1ContentValues );

                ContentValues photo2ContentValues = new ContentValues();
                photo2ContentValues.put("photoText", "bedroom");
                photo2ContentValues.put("photoUri", "http://static.cotemaison.fr/medias_11749/w_2048,h_1146,c_crop,x_0,y_164/w_640,h_360,c_fill,g_north/v1518095166/chambre-parentale-avec-mur-bleu_6015724.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photo2ContentValues.put("property", 1);
                db.insert("photo",OnConflictStrategy.IGNORE, photo2ContentValues );

                ContentValues photo3ContentValues = new ContentValues();
                photo3ContentValues.put("photoText", "kichen");
                photo3ContentValues.put("photoUri", "https://www.ma-petite-cuisine.fr/wp-content/uploads/2017/04/elancon-chene-cuisine-equipee.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photo3ContentValues.put("property", 1);
                db.insert("photo",OnConflictStrategy.IGNORE, photo3ContentValues );

                ContentValues photoAContentValues = new ContentValues();
                photoAContentValues.put("photoText", "facade");
                photoAContentValues.put("photoUri", "http://espace-facades.fr/wp-content/uploads/sites/8/2018/09/ravalement-facade-maison-blagnac-avant.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photoAContentValues.put("property", 2);
                db.insert("photo",OnConflictStrategy.IGNORE, photoAContentValues );

                ContentValues photoA1ContentValues = new ContentValues();
                photoA1ContentValues.put("photoText", "living room");
                photoA1ContentValues.put("photoUri", "https://cache.marieclaire.fr/data/photo/w1000_ci/4y/salon-design-new-york.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photoA1ContentValues.put("property", 2);
                db.insert("photo",OnConflictStrategy.IGNORE, photoA1ContentValues );

                ContentValues photoA2ContentValues = new ContentValues();
                photoA2ContentValues.put("photoText", "bedroom");
                photoA2ContentValues.put("photoUri", "https://www.turbulences-deco.fr/wp-content/uploads/2015/05/Chambre-blanche-et-rose-via-VTwonen.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photoA2ContentValues.put("property", 2);
                db.insert("photo",OnConflictStrategy.IGNORE, photoA2ContentValues );

                ContentValues photoA3ContentValues = new ContentValues();
                photoA3ContentValues.put("photoText", "kichen");
                photoA3ContentValues.put("photoUri", "https://www.ateliersjacob.com/imports/images/realisations/fr/cuisine-contemporaine/cuisine-contemporaine-1-1.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photoA3ContentValues.put("property", 2);
                db.insert("photo",OnConflictStrategy.IGNORE, photoA3ContentValues );

                ContentValues photoA4ContentValues = new ContentValues();
                photoA4ContentValues.put("photoText", "bedroom");
                photoA4ContentValues.put("photoUri", "https://www.petiteamelie.fr/media/wysiwyg/homepage-images/chambre-bebe-petite-amelie.jpg?auto=compress,format&q=80&h=100&dpr=2");
                photoA4ContentValues.put("property", 2);
                db.insert("photo",OnConflictStrategy.IGNORE, photoA4ContentValues );

                // AttractingPropertyJoin
                ContentValues attracPropertyContentValues = new ContentValues();
                attracPropertyContentValues.put("propertyId", 1);
                attracPropertyContentValues.put("attractingId", 1);
                db.insert("attracting_property_join",OnConflictStrategy.IGNORE, attracPropertyContentValues );

                ContentValues attracProperty1ContentValues = new ContentValues();
                attracProperty1ContentValues.put("propertyId", 1);
                attracProperty1ContentValues.put("attractingId", 3);
                db.insert("attracting_property_join",OnConflictStrategy.IGNORE, attracProperty1ContentValues );

                ContentValues attracProperty2ContentValues = new ContentValues();
                attracProperty2ContentValues.put("propertyId", 2);
                attracProperty2ContentValues.put("attractingId", 1);
                db.insert("attracting_property_join",OnConflictStrategy.IGNORE, attracProperty2ContentValues );

                ContentValues attracProperty3ContentValues = new ContentValues();
                attracProperty3ContentValues.put("propertyId", 2);
                attracProperty3ContentValues.put("attractingId", 2);
                db.insert("attracting_property_join",OnConflictStrategy.IGNORE, attracProperty3ContentValues );
            }
        };
    }
}
