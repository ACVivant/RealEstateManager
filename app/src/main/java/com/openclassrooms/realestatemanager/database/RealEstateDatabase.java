package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.openclassrooms.realestatemanager.database.dao.AgentDao;
import com.openclassrooms.realestatemanager.database.dao.PhotoDao;
import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.database.dao.StatusDao;
import com.openclassrooms.realestatemanager.database.dao.TypeOfPropertyDao;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.nesertplusariennormalement.AttractingPoint;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Creation of the database and first implementation
 */


@Database(entities =
        {Address.class, Agent.class, AttractingPoint.class, Photo.class, Property.class, Status.class, TypeOfProperty.class},
        version = 1,
        exportSchema = false)
public abstract class RealEstateDatabase extends RoomDatabase {

    private static final String TAG = "RealEstateDatabase";

    // --- SINGLETON ---
    private static RealEstateDatabase INSTANCE;

    // --- DAO ---
    public abstract AgentDao agentDao();
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

    // ---------------------------------
    // Prepopulate database for demo
    // ---------------------------------

    private static Callback prepopulateDatabase(){
        return new Callback() {
            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValuesAgent = new ContentValues();
                contentValuesAgent.put("agentName", "Jules");
                db.insert("agent", OnConflictStrategy.REPLACE, contentValuesAgent);

                ContentValues contentValuesAgent2 = new ContentValues();
                contentValuesAgent2.put("agentName", "Marcelina");
                db.insert("agent", OnConflictStrategy.REPLACE, contentValuesAgent2);

                ContentValues contentValuesAgent3 = new ContentValues();
                contentValuesAgent3.put("agentName", "Victoire");
                db.insert("agent", OnConflictStrategy.REPLACE, contentValuesAgent3);

                ContentValues contentValuesStatus = new ContentValues();
                contentValuesStatus.put("statusText", "En cours de création");
                db.insert("status", OnConflictStrategy.REPLACE, contentValuesStatus);

                ContentValues contentValuesStatus1 = new ContentValues();
                contentValuesStatus1.put("statusText", "En vente");
                db.insert("status", OnConflictStrategy.REPLACE, contentValuesStatus1);

                ContentValues contentValuesStatus2 = new ContentValues();
                contentValuesStatus2.put("statusText", "En cours de négociation");
                db.insert("status", OnConflictStrategy.REPLACE, contentValuesStatus2);

                ContentValues contentValuesStatus3 = new ContentValues();
                contentValuesStatus3.put("statusText", "Vendu");
                db.insert("status", OnConflictStrategy.REPLACE, contentValuesStatus3);

                ContentValues contentValuesType = new ContentValues();
                contentValuesType.put("typeText", "Appartement" );
                db.insert("type_of_property", OnConflictStrategy.REPLACE, contentValuesType);

                ContentValues contentValuesType1 = new ContentValues();
                contentValuesType1.put("typeText", "Loft" );
                db.insert("type_of_property", OnConflictStrategy.REPLACE, contentValuesType1);

                ContentValues contentValuesType2 = new ContentValues();
                contentValuesType2.put("typeText", "Maison" );
                db.insert("type_of_property", OnConflictStrategy.REPLACE, contentValuesType2);

                ContentValues contentValuesType3 = new ContentValues();
                contentValuesType3.put("typeText", "Manoir" );
                db.insert("type_of_property", OnConflictStrategy.REPLACE, contentValuesType3);

                ContentValues contentValuesType4 = new ContentValues();
                contentValuesType4.put("typeText", "Château" );
                db.insert("type_of_property", OnConflictStrategy.REPLACE, contentValuesType4);

                ContentValues contentValuesProperty = new ContentValues();
                contentValuesProperty.put("price", 450000);
                contentValuesProperty.put("rooms", 3);
                contentValuesProperty.put("bedrooms", 1);
                contentValuesProperty.put("bathroom", 1);
                contentValuesProperty.put("description", "design, charme et localisation extraordinaire");
                contentValuesProperty.put("upForSaleDate", 20181002);
                contentValuesProperty.put("soldOnDate", 20181005);
                contentValuesProperty.put("surface", 75);
                contentValuesProperty.put("shop", false);
                contentValuesProperty.put("school", false);
                contentValuesProperty.put("museum", true);
                contentValuesProperty.put("park", true);
                contentValuesProperty.put("typeId", 1);
                contentValuesProperty.put("agentId", 1);
                contentValuesProperty.put("statusId", 4);
                contentValuesProperty.put("mainPhoto", "https://www.parklex.com/wp-content/uploads/2015/11/WoodviewMews-GeraghtyTaylorArchitects-London-UK-2015-Parklex-Facade-Gold-02.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesProperty.put("nbrePhotos", "5");
                contentValuesProperty.put("numberInStreet", "6");
                contentValuesProperty.put("street", "rue Alexandre Dumas");
                contentValuesProperty.put("street2", "Appt 5");
                contentValuesProperty.put("zipcode", "60800");
                contentValuesProperty.put("town", "Crépy-en-Valois");
                contentValuesProperty.put("country", "France");
                db.insert("property", OnConflictStrategy.REPLACE, contentValuesProperty);

                ContentValues contentValuesProperty1 = new ContentValues();
                contentValuesProperty1.put("price", 578230);
                contentValuesProperty1.put("rooms", 5);
                contentValuesProperty1.put("bedrooms", 3);
                contentValuesProperty1.put("bathroom", 2);
                contentValuesProperty1.put("description", "Quelle surprise derrière cette façade de découvrir une maison si moderne et entièrement équipée");
                contentValuesProperty1.put("upForSaleDate", 20190305);
                contentValuesProperty1.put("soldOnDate", 99999999);
                contentValuesProperty1.put("surface", 253);
                contentValuesProperty1.put("shop", true);
                contentValuesProperty1.put("school", false);
                contentValuesProperty1.put("museum", true);
                contentValuesProperty1.put("park", false);
                contentValuesProperty1.put("typeId", 2);
                contentValuesProperty1.put("agentId", 1);
                contentValuesProperty1.put("statusId", 2);
                contentValuesProperty1.put("mainPhoto", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQeMzGKCkWMBHvCyz3C7fqYUYuGYUjjhJRwqM3XavLWSAvUiUz9?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesProperty1.put("nbrePhotos", "6");
                contentValuesProperty1.put("numberInStreet", "10");
                contentValuesProperty1.put("street", "rue de Soissons");
                contentValuesProperty1.put("zipcode", "60800");
                contentValuesProperty1.put("town", "Crépy-en-Valois");
                contentValuesProperty1.put("country", "France");
                db.insert("property", OnConflictStrategy.REPLACE, contentValuesProperty1);


                ContentValues contentValuesProperty2 = new ContentValues();
                contentValuesProperty2.put("price", 125480);
                contentValuesProperty2.put("rooms", 1);
                contentValuesProperty2.put("bedrooms", 1);
                contentValuesProperty2.put("bathroom", 1);
                contentValuesProperty2.put("description", "studio de charme");
                contentValuesProperty2.put("upForSaleDate", 20190505);
                contentValuesProperty2.put("soldOnDate", 99999999);
                contentValuesProperty2.put("surface", 75);
                contentValuesProperty2.put("shop", true);
                contentValuesProperty2.put("school", false);
                contentValuesProperty2.put("museum", true);
                contentValuesProperty2.put("park", true);
                contentValuesProperty2.put("typeId", 1);
                contentValuesProperty2.put("agentId", 1);
                contentValuesProperty2.put("statusId", 2);
                contentValuesProperty2.put("mainPhoto", "https://upload.wikimedia.org/wikipedia/commons/3/3c/Fa%C3%A7ade_de_l%27immeuble_sis_au_57_rue_de_Bruxelles.JPG?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesProperty2.put("nbrePhotos", "3");
                contentValuesProperty2.put("numberInStreet", "12");
                contentValuesProperty2.put("street", "rue Charles de Gaulle");
                contentValuesProperty2.put("zipcode", "60800");
                contentValuesProperty2.put("town", "Crépy-en-Valois");
                contentValuesProperty2.put("country", "France");
                db.insert("property", OnConflictStrategy.REPLACE, contentValuesProperty2);

                ContentValues contentValuesProperty3 = new ContentValues();
                contentValuesProperty3.put("price", 56230500);
                contentValuesProperty3.put("rooms", 23);
                contentValuesProperty3.put("bedrooms", 12);
                contentValuesProperty3.put("bathroom", 12);
                contentValuesProperty3.put("description", "Un vrai palace! Pour revivre le faste du roi Soleil et dominer le monde");
                contentValuesProperty3.put("upForSaleDate", 20190505);
                contentValuesProperty3.put("soldOnDate", 99999999);
                contentValuesProperty3.put("surface", 75);
                contentValuesProperty3.put("shop", false);
                contentValuesProperty3.put("school", false);
                contentValuesProperty3.put("museum", false);
                contentValuesProperty3.put("park", true);
                contentValuesProperty3.put("typeId", 4);
                contentValuesProperty3.put("agentId", 1);
                contentValuesProperty3.put("statusId", 2);
                contentValuesProperty3.put("mainPhoto", "https://images.musement.com/cover/0001/19/palace-of-versailles-skip-the-line-tickets-guided-visit-with-fountain-show-and-musical-gardens-by-train_header-18993.jpeg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesProperty3.put("nbrePhotos", "5");
                contentValuesProperty3.put("numberInStreet", "5");
                contentValuesProperty3.put("street", "rue Saint Denis");
                contentValuesProperty3.put("zipcode", "60800");
                contentValuesProperty3.put("town", "Crépy-en-Valois");
                contentValuesProperty3.put("country", "France");
                db.insert("property", OnConflictStrategy.REPLACE, contentValuesProperty3);

                ContentValues contentValuesProperty4 = new ContentValues();
                contentValuesProperty4.put("price", 153450);
                contentValuesProperty4.put("rooms", 2);
                contentValuesProperty4.put("bedrooms", 1);
                contentValuesProperty4.put("bathroom", 1);
                contentValuesProperty4.put("description", "Un petit cocon");
                contentValuesProperty4.put("upForSaleDate", 20190501);
                contentValuesProperty4.put("soldOnDate", 99999999);
                contentValuesProperty4.put("surface", 25);
                contentValuesProperty4.put("shop", true);
                contentValuesProperty4.put("school", true);
                contentValuesProperty4.put("museum", false);
                contentValuesProperty4.put("park", false);
                contentValuesProperty4.put("typeId", 1);
                contentValuesProperty4.put("agentId", 1);
                contentValuesProperty4.put("statusId", 2);
                contentValuesProperty4.put("mainPhoto", "https://www.expert-peinture.fr/wp-content/uploads/2015/09/5-conseils-pour-peindre-sa-fa%c3%a7ade-%c3%a0-la-perfection-.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesProperty4.put("nbrePhotos", "3");
                contentValuesProperty4.put("numberInStreet", "10");
                contentValuesProperty4.put("street", "rue Saint Denis");
                contentValuesProperty4.put("zipcode", "60800");
                contentValuesProperty4.put("town", "Crépy-en-Valois");
                contentValuesProperty4.put("country", "France");
                db.insert("property", OnConflictStrategy.REPLACE, contentValuesProperty4);

                ContentValues contentValuesProperty5 = new ContentValues();
                contentValuesProperty5.put("price", 275490);
                contentValuesProperty5.put("rooms", 3);
                contentValuesProperty5.put("bedrooms", 2);
                contentValuesProperty5.put("bathroom", 1);
                contentValuesProperty5.put("description", "Une opportunité à ne pas manquer");
                contentValuesProperty5.put("upForSaleDate", 20190423);
                contentValuesProperty5.put("soldOnDate", 99999999);
                contentValuesProperty5.put("surface", 70);
                contentValuesProperty5.put("shop", true);
                contentValuesProperty5.put("school", true);
                contentValuesProperty5.put("museum", false);
                contentValuesProperty5.put("park", false);
                contentValuesProperty5.put("typeId", 1);
                contentValuesProperty5.put("agentId", 1);
                contentValuesProperty5.put("statusId", 2);
                contentValuesProperty5.put("mainPhoto", "https://cdn.pixabay.com/photo/2018/04/05/19/51/facade-3293950_960_720.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesProperty5.put("nbrePhotos", "3");
                contentValuesProperty5.put("numberInStreet", "5");
                contentValuesProperty5.put("street", "rue de Vez");
                contentValuesProperty5.put("zipcode", "60800");
                contentValuesProperty5.put("town", "Crépy-en-Valois");
                contentValuesProperty5.put("country", "France");
                db.insert("property", OnConflictStrategy.REPLACE, contentValuesProperty5);

                ContentValues contentValuesProperty6 = new ContentValues();
                contentValuesProperty6.put("price", 562300);
                contentValuesProperty6.put("rooms", 2);
                contentValuesProperty6.put("bedrooms", 1);
                contentValuesProperty6.put("bathroom", 1);
                contentValuesProperty6.put("description", "Pour les amoureux de design... et de confort!");
                contentValuesProperty6.put("upForSaleDate", 20190517);
                contentValuesProperty6.put("soldOnDate", 99999999);
                contentValuesProperty6.put("surface", 120);
                contentValuesProperty6.put("shop", true);
                contentValuesProperty6.put("school", false);
                contentValuesProperty6.put("museum", false);
                contentValuesProperty6.put("park", false);
                contentValuesProperty6.put("typeId", 2);
                contentValuesProperty6.put("agentId", 1);
                contentValuesProperty6.put("statusId", 2);
                contentValuesProperty6.put("mainPhoto", "https://inhabitat.com/wp-content/blogs.dir/1/files/2016/01/Bergeron-Centre-for-Engineering-Excellence-by-ZAS-Architects-3.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesProperty6.put("nbrePhotos", "5");
                contentValuesProperty6.put("numberInStreet", "5b");
                contentValuesProperty6.put("street", "rue de Vez");
                contentValuesProperty6.put("zipcode", "60800");
                contentValuesProperty6.put("town", "Crépy-en-Valois");
                contentValuesProperty6.put("country", "France");
                db.insert("property", OnConflictStrategy.REPLACE, contentValuesProperty6);

                ContentValues contentValuesPhoto1 = new ContentValues();
                contentValuesPhoto1.put("photoUri","https://s3.amazonaws.com/furniture.retailcatalog.us/products/208530/large/b219-31-36-46-77-74-71-96-q238.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto1.put("photoText", "bedroom");
                contentValuesPhoto1.put("propertyId",1 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto1);

                ContentValues contentValuesPhoto2 = new ContentValues();
                contentValuesPhoto2.put("photoUri","http://static.cotemaison.fr/medias_11749/w_2048,h_1146,c_crop,x_0,y_164/w_640,h_360,c_fill,g_north/v1518095166/chambre-parentale-avec-mur-bleu_6015724.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto2.put("photoText", "bedroom");
                contentValuesPhoto2.put("propertyId",1 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto2);

                ContentValues contentValuesPhoto3 = new ContentValues();
                contentValuesPhoto3.put("photoUri","https://www.ma-petite-cuisine.fr/wp-content/uploads/2017/04/elancon-chene-cuisine-equipee.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto3.put("photoText", "kitchen");
                contentValuesPhoto3.put("propertyId",1 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto3);

                ContentValues contentValuesPhoto5 = new ContentValues();
                contentValuesPhoto5.put("photoUri","https://cache.marieclaire.fr/data/photo/w1000_ci/4y/salon-design-new-york.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto5.put("photoText", "living room");
                contentValuesPhoto5.put("propertyId",2 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto5);

                ContentValues contentValuesPhoto6 = new ContentValues();
                contentValuesPhoto6.put("photoUri","https://www.turbulences-deco.fr/wp-content/uploads/2015/05/Chambre-blanche-et-rose-via-VTwonen.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto6.put("photoText", "bedroom");
                contentValuesPhoto6.put("propertyId",2 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto6);

                ContentValues contentValuesPhoto7 = new ContentValues();
                contentValuesPhoto7.put("photoUri","https://www.ateliersjacob.com/imports/images/realisations/fr/cuisine-contemporaine/cuisine-contemporaine-1-1.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto7.put("photoText", "kitchen");
                contentValuesPhoto7.put("propertyId",2 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto7);

                ContentValues contentValuesPhoto8 = new ContentValues();
                contentValuesPhoto8.put("photoUri","https://www.petiteamelie.fr/media/wysiwyg/homepage-images/chambre-bebe-petite-amelie.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto8.put("photoText", "bedroom");
                contentValuesPhoto8.put("propertyId",2 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto8);

                ContentValues contentValuesPhoto10 = new ContentValues();
                contentValuesPhoto10.put("photoUri","https://s-ec.bstatic.com/images/hotel/max1024x768/962/96268673.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto10.put("photoText", "main room");
                contentValuesPhoto10.put("propertyId",3 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto10);

                ContentValues contentValuesPhoto12 = new ContentValues();
                contentValuesPhoto12.put("photoUri","https://img.aws.la-croix.com/2019/04/09/1301014531/chambre-renovee-Marie-Antoinette-1755-1793-Chateau-Versailles-9-avril-2019_0_729_492.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto12.put("photoText", "chambre");
                contentValuesPhoto12.put("propertyId",4 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto12);

                ContentValues contentValuesPhoto13 = new ContentValues();
                contentValuesPhoto13.put("photoUri","https://img.grouponcdn.com/pwa_test/MSZ7NZCUsSLdpas2DmMMhKLBGA7/MS-669x446/v1/c700x420.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto13.put("photoText", "garden");
                contentValuesPhoto13.put("propertyId",4 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto13);

                ContentValues contentValuesPhoto14 = new ContentValues();
                contentValuesPhoto14.put("photoUri","https://images.musement.com/cover/0002/15/venuehero-versailles1-jpg_header-114728.jpeg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto14.put("photoText", "dance room");
                contentValuesPhoto14.put("propertyId",4 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto14);

                ContentValues contentValuesPhoto15 = new ContentValues();
                contentValuesPhoto15.put("photoUri","https://img-3.journaldesfemmes.fr/txQP63kltdw6VHa0PBklOLCpz-c=/910x607/smart/3ff469b4dd554cb185c1ac5ad0d045f5/ccmcms-jdf/10476574.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto15.put("photoText", "main room");
                contentValuesPhoto15.put("propertyId",5 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto15);

                ContentValues contentValuesPhoto18 = new ContentValues();
                contentValuesPhoto18.put("photoUri","https://www.entre-hotes.com/images/chambres/insolite/chambre-7701.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto18.put("photoText", "bedroom");
                contentValuesPhoto18.put("propertyId",6 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto18);

                ContentValues contentValuesPhoto19 = new ContentValues();
                contentValuesPhoto19.put("photoUri","https://www.keria.com/media/wysiwyg/CMS/Keria/218/K_erreurs-eclairage-salon_GUIDE.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto19.put("photoText", "salon");
                contentValuesPhoto19.put("propertyId",6 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto19);

                ContentValues contentValuesPhoto21 = new ContentValues();
                contentValuesPhoto21.put("photoUri","https://www.ikea.com/images/photo-dune-chambre-a-coucher-avec-lit-double-lit-bebe-et-jou-53a202d1682582a3f1e13520c2fba648.jpg?f=s?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto21.put("photoText", "bedroom");
                contentValuesPhoto21.put("propertyId",7 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto21);

                ContentValues contentValuesPhoto22 = new ContentValues();
                contentValuesPhoto22.put("photoUri","http://static.cotemaison.fr/medias_11747/w_1365,h_1365,c_crop,x_444,y_0/w_600,h_600,c_fill,g_north/v1517915099/grand-salon-avec-miroir-xxl_6014646.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto22.put("photoText", "salon");
                contentValuesPhoto22.put("propertyId",7 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto22);

                ContentValues contentValuesPhoto23 = new ContentValues();
                contentValuesPhoto23.put("photoUri","https://www.maisonapart.com/images/auto/640-480-c/20190128_150738_01-mobalpa-mo18ambiance-scenariovp11.jpg?auto=compress,format&q=80&h=100&dpr=2");
                contentValuesPhoto23.put("photoText", "kitchen");
                contentValuesPhoto23.put("propertyId",7 );
                db.insert("photo", OnConflictStrategy.REPLACE, contentValuesPhoto23);

            }
        };
    }
}
