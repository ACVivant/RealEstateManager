package com.openclassrooms.realestatemanager.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.openclassrooms.realestatemanager.database.dao.AddressDao;
import com.openclassrooms.realestatemanager.database.dao.AgentDao;
import com.openclassrooms.realestatemanager.database.dao.AttractingPointDao;
import com.openclassrooms.realestatemanager.database.dao.AttractingPropertyJoinDao;
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

    private static final String TAG = "RealEstateDatabase";
    // --- SINGLETON ---
    private static RealEstateDatabase INSTANCE;

    // --- DAO ---
    public abstract AddressDao addressDao();
    public abstract AgentDao agentDao();
    public abstract AttractingPointDao attractingPointDao();
    public abstract PhotoDao photoDao();
    public abstract PropertyDao propertyDao();
    public abstract StatusDao statusDao();
    public abstract TypeOfPropertyDao typeOfPropertyDao();
    public abstract AttractingPropertyJoinDao attractingPropertyJoinDao();

    // --- INSTANCE ---
    public static RealEstateDatabase getInstance(Context context) {
        Log.d(TAG, "getInstance: RealEstateDatabase 1");
        if (INSTANCE == null) {
            synchronized (RealEstateDatabase.class) {
                Log.d(TAG, "getInstance: RealEstateDatabase 2");
                if (INSTANCE == null) {
                    Log.d(TAG, "getInstance: RealEstateDatabase 3");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RealEstateDatabase.class, "real_estate_database.db")
                            //.fallbackToDestructiveMigration()
                            .addCallback(new Callback(){
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    Log.d(TAG, "onCreate: roomCallback");
                                    new PopulateDbAsyncTask(INSTANCE).execute();
                                }

                                @Override
                                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                    super.onOpen(db);
                                    Log.d(TAG, "onOpen: roomCallBack");
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // ---

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Log.d(TAG, "onCreate: roomCallback");
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AddressDao addressDao;
        private AgentDao agentDao;
        private AttractingPointDao attractingPointDao;
        private PhotoDao photoDao;
        private PropertyDao propertyDao;
        private  StatusDao statusDao;
        private TypeOfPropertyDao typeOfPropertyDao;
        private AttractingPropertyJoinDao attractingPropertyJoinDao;

        private PopulateDbAsyncTask(RealEstateDatabase db) {
        addressDao = db.addressDao();
        agentDao = db.agentDao();
        attractingPointDao = db.attractingPointDao();
        photoDao = db.photoDao();
        propertyDao = db.propertyDao();
        statusDao = db.statusDao();
        typeOfPropertyDao = db.typeOfPropertyDao();
        attractingPropertyJoinDao = db.attractingPropertyJoinDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: prepopulate");
            agentDao.insertAgent(new Agent("Pierre Robes"));
            agentDao.insertAgent(new Agent("Inès Pairé"));

            attractingPointDao.insertAttractingPoint(new AttractingPoint("school"));
            attractingPointDao.insertAttractingPoint(new AttractingPoint("shop"));
            attractingPointDao.insertAttractingPoint(new AttractingPoint("park"));
            attractingPointDao.insertAttractingPoint(new AttractingPoint("museum"));

            statusDao.insertStatus(new com.openclassrooms.realestatemanager.models.Status("en vente"));
            statusDao.insertStatus(new com.openclassrooms.realestatemanager.models.Status("compromis de vente signé"));
            statusDao.insertStatus(new com.openclassrooms.realestatemanager.models.Status("vendu"));

            typeOfPropertyDao.insertType(new TypeOfProperty("appartement"));
            typeOfPropertyDao.insertType(new TypeOfProperty("loft"));
            typeOfPropertyDao.insertType(new TypeOfProperty("maison"));
            typeOfPropertyDao.insertType(new TypeOfProperty("manoir"));
            typeOfPropertyDao.insertType(new TypeOfProperty("château"));

            addressDao.insertAddress(new Address("6", "rue Alexandre Dumas", "Appt 5", "60800", "Crépy-en-Valois", "France"));
            addressDao.insertAddress(new Address("10", "rue de Soissons", "", "60800", "Crépy-en-Valois", "France"));

            propertyDao.insertProperty(new Property(450000, 3, 1, 1,
                    "design, charme et localisation extraordinaire",
                    20181002, 20181005, 75,
                    typeOfPropertyDao.getTypeFromId(0),
                    addressDao.getAddressFromId(0),
                    agentDao.getAgentFromId(0),
                    statusDao.getStatusFromId(2),
                    "https://www.parklex.com/wp-content/uploads/2015/11/WoodviewMews-GeraghtyTaylorArchitects-London-UK-2015-Parklex-Facade-Gold-02.jpg?auto=compress,format&q=80&h=100&dpr=2"));
            propertyDao.insertProperty(new Property(578230, 5, 3, 2,
                    "Quelle surprise derrière cette façade de découvrir une maison si moderne et entièrement équipée",
                    20190305, ' ',253,
                    typeOfPropertyDao.getTypeFromId(2),
                    addressDao.getAddressFromId(1),
                    agentDao.getAgentFromId(0),
                    statusDao.getStatusFromId(0),
                    "http://espace-facades.fr/wp-content/uploads/sites/8/2018/09/ravalement-facade-maison-blagnac-avant.jpg?auto=compress,format&q=80&h=100&dpr=2"));

            photoDao.insertPhoto(new Photo("https://www.parklex.com/wp-content/uploads/2015/11/WoodviewMews-GeraghtyTaylorArchitects-London-UK-2015-Parklex-Facade-Gold-02.jpg?auto=compress,format&q=80&h=100&dpr=2","façade", 1));
            photoDao.insertPhoto(new Photo("https://img-3.journaldesfemmes.fr/qLiTvl6g4A4iw_56ntaA4-cOa1w=/819x546/smart/54dc4627fbce4251b7ffbc0702a26dc4/ccmcms-jdf/11014669.jpg?auto=compress,format&q=80&h=100&dpr=2", "bedroom",1));
            photoDao.insertPhoto(new Photo("http://static.cotemaison.fr/medias_11749/w_2048,h_1146,c_crop,x_0,y_164/w_640,h_360,c_fill,g_north/v1518095166/chambre-parentale-avec-mur-bleu_6015724.jpg?auto=compress,format&q=80&h=100&dpr=2", "bedroom",1));
            photoDao.insertPhoto(new Photo("https://www.ma-petite-cuisine.fr/wp-content/uploads/2017/04/elancon-chene-cuisine-equipee.jpg?auto=compress,format&q=80&h=100&dpr=2", "kitchen", 1));
            photoDao.insertPhoto(new Photo("http://espace-facades.fr/wp-content/uploads/sites/8/2018/09/ravalement-facade-maison-blagnac-avant.jpg?auto=compress,format&q=80&h=100&dpr=2", "façade", 2));
            photoDao.insertPhoto(new Photo( "https://cache.marieclaire.fr/data/photo/w1000_ci/4y/salon-design-new-york.jpg?auto=compress,format&q=80&h=100&dpr=2", "living room", 2));
            photoDao.insertPhoto(new Photo("https://www.turbulences-deco.fr/wp-content/uploads/2015/05/Chambre-blanche-et-rose-via-VTwonen.jpg?auto=compress,format&q=80&h=100&dpr=2", "bedroom", 2));
            photoDao.insertPhoto(new Photo( "https://www.ateliersjacob.com/imports/images/realisations/fr/cuisine-contemporaine/cuisine-contemporaine-1-1.jpg?auto=compress,format&q=80&h=100&dpr=2", "kitchen", 2));
            photoDao.insertPhoto(new Photo("https://www.petiteamelie.fr/media/wysiwyg/homepage-images/chambre-bebe-petite-amelie.jpg?auto=compress,format&q=80&h=100&dpr=2", "bedroom", 2));

            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(1,0));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(2,0));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(1,1));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(3,1));
            return null;
        }
    }
}
