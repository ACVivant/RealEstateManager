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
            addressDao.insertAddress(new Address("12", "rue Charles de Gaulle", "", "60800", "Crépy-en-Valois", "France"));
            addressDao.insertAddress(new Address("5", "rue Saint Denis", "", "60800", "Crépy-en-Valois", "France"));
            addressDao.insertAddress(new Address("13", "rue Saint Denis", "", "60800", "Crépy-en-Valois", "France"));
            addressDao.insertAddress(new Address("13", "rue de Vez", "", "60800", "Crépy-en-Valois", "France"));
            addressDao.insertAddress(new Address("19", "rue de Vez", "", "60800", "Crépy-en-Valois", "France"));

            propertyDao.insertProperty(new Property(450000, 3, 1, 1,
                    "design, charme et localisation extraordinaire",
                    20181002, 20181005, 75,
                    false, false, true, true,
                    typeOfPropertyDao.getTypeFromId(1),
                    addressDao.getAddressFromId(1),
                    agentDao.getAgentFromId(1),
                    statusDao.getStatusFromId(3),
                    "https://www.parklex.com/wp-content/uploads/2015/11/WoodviewMews-GeraghtyTaylorArchitects-London-UK-2015-Parklex-Facade-Gold-02.jpg?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(578230, 5, 3, 2,
                    "Quelle surprise derrière cette façade de découvrir une maison si moderne et entièrement équipée",
                    20190305, ' ',253,
                    true, false, true, false,
                    typeOfPropertyDao.getTypeFromId(3),
                    addressDao.getAddressFromId(2),
                    agentDao.getAgentFromId(1),
                    statusDao.getStatusFromId(1),
                    "http://espace-facades.fr/wp-content/uploads/sites/8/2018/09/ravalement-facade-maison-blagnac-avant.jpg?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(125480, 1, 1, 1,
                    "studio de charme",
                    20190505, ' ',27,
                    false, false, false, true,
                    typeOfPropertyDao.getTypeFromId(1),
                    addressDao.getAddressFromId(3),
                    agentDao.getAgentFromId(1),
                    statusDao.getStatusFromId(1),
                    "https://upload.wikimedia.org/wikipedia/commons/3/3c/Fa%C3%A7ade_de_l%27immeuble_sis_au_57_rue_de_Bruxelles.JPG?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(56230500, 32, 12, 12,
                    "palace, qu'en dire de plus?",
                    20190517, ' ',980,
                    true, false, false, false,
                    typeOfPropertyDao.getTypeFromId(5),
                    addressDao.getAddressFromId(4),
                    agentDao.getAgentFromId(1),
                    statusDao.getStatusFromId(1),
                    "https://images.musement.com/cover/0001/19/palace-of-versailles-skip-the-line-tickets-guided-visit-with-fountain-show-and-musical-gardens-by-train_header-18993.jpeg?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(153450, 2, 1, 1,
                    "un petit cocon",
                    20190501, ' ',25,
                    true, true, true, true,
                    typeOfPropertyDao.getTypeFromId(1),
                    addressDao.getAddressFromId(5),
                    agentDao.getAgentFromId(1),
                    statusDao.getStatusFromId(1),
                    "https://www.expert-peinture.fr/wp-content/uploads/2015/09/5-conseils-pour-peindre-sa-fa%c3%a7ade-%c3%a0-la-perfection-.jpg?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(275490, 3, 2, 1,
                    "à saisir",
                    20190423, ' ',70,
                    false, false, true, false,
                    typeOfPropertyDao.getTypeFromId(2),
                    addressDao.getAddressFromId(6),
                    agentDao.getAgentFromId(1),
                    statusDao.getStatusFromId(1),
                    "http://www.constructions-dantan.fr/docs/Entreprise/restauration/charpente_normandie.jpg?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(562300, 2, 1, 1,
                    "un petit cocon",
                    201900517, ' ',120,
                    true, true, false, false,
                    typeOfPropertyDao.getTypeFromId(3),
                    addressDao.getAddressFromId(7),
                    agentDao.getAgentFromId(1),
                    statusDao.getStatusFromId(1),
                    "https://inhabitat.com/wp-content/blogs.dir/1/files/2016/01/Bergeron-Centre-for-Engineering-Excellence-by-ZAS-Architects-3.jpg?auto=compress,format&q=80&h=100&dpr=2"));

          /*  propertyDao.insertProperty(new Property(450000, 3, 1, 1,
                    "design, charme et localisation extraordinaire",
                    20181002, 20181005, 75,
                    1,addressDao.getAddressFromId(1),1,1,
                    "https://www.parklex.com/wp-content/uploads/2015/11/WoodviewMews-GeraghtyTaylorArchitects-London-UK-2015-Parklex-Facade-Gold-02.jpg?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(578230, 5, 3, 2,
                    "Quelle surprise derrière cette façade de découvrir une maison si moderne et entièrement équipée",
                    20190305, ' ',253,
                    3,addressDao.getAddressFromId(2),1,1,
                    "http://espace-facades.fr/wp-content/uploads/sites/8/2018/09/ravalement-facade-maison-blagnac-avant.jpg?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(125480, 1, 1, 1,
                    "studio de charme",
                    20190505, ' ',27,
                     1,addressDao.getAddressFromId(3),1,1,
                    "https://upload.wikimedia.org/wikipedia/commons/3/3c/Fa%C3%A7ade_de_l%27immeuble_sis_au_57_rue_de_Bruxelles.JPG?auto=compress,format&q=80&h=100&dpr=2"));

            propertyDao.insertProperty(new Property(56230500, 32, 12, 12,
                    "palace, qu'en dire de plus?",
                    20190517, ' ',980,
                    5,addressDao.getAddressFromId(4),1,1,
                    "https://images.musement.com/cover/0001/19/palace-of-versailles-skip-the-line-tickets-guided-visit-with-fountain-show-and-musical-gardens-by-train_header-18993.jpeg?auto=compress,format&q=80&h=100&dpr=2"));
*/
            photoDao.insertPhoto(new Photo("https://www.parklex.com/wp-content/uploads/2015/11/WoodviewMews-GeraghtyTaylorArchitects-London-UK-2015-Parklex-Facade-Gold-02.jpg?auto=compress,format&q=80&h=100&dpr=2","façade", 1));
            photoDao.insertPhoto(new Photo("https://img-3.journaldesfemmes.fr/qLiTvl6g4A4iw_56ntaA4-cOa1w=/819x546/smart/54dc4627fbce4251b7ffbc0702a26dc4/ccmcms-jdf/11014669.jpg?auto=compress,format&q=80&h=100&dpr=2", "bedroom",1));
            photoDao.insertPhoto(new Photo("http://static.cotemaison.fr/medias_11749/w_2048,h_1146,c_crop,x_0,y_164/w_640,h_360,c_fill,g_north/v1518095166/chambre-parentale-avec-mur-bleu_6015724.jpg?auto=compress,format&q=80&h=100&dpr=2", "bedroom",1));
            photoDao.insertPhoto(new Photo("https://www.ma-petite-cuisine.fr/wp-content/uploads/2017/04/elancon-chene-cuisine-equipee.jpg?auto=compress,format&q=80&h=100&dpr=2", "kitchen", 1));

            photoDao.insertPhoto(new Photo("http://espace-facades.fr/wp-content/uploads/sites/8/2018/09/ravalement-facade-maison-blagnac-avant.jpg?auto=compress,format&q=80&h=100&dpr=2", "façade", 2));
            photoDao.insertPhoto(new Photo( "https://cache.marieclaire.fr/data/photo/w1000_ci/4y/salon-design-new-york.jpg?auto=compress,format&q=80&h=100&dpr=2", "living room", 2));
            photoDao.insertPhoto(new Photo("https://www.turbulences-deco.fr/wp-content/uploads/2015/05/Chambre-blanche-et-rose-via-VTwonen.jpg?auto=compress,format&q=80&h=100&dpr=2", "bedroom", 2));
            photoDao.insertPhoto(new Photo( "https://www.ateliersjacob.com/imports/images/realisations/fr/cuisine-contemporaine/cuisine-contemporaine-1-1.jpg?auto=compress,format&q=80&h=100&dpr=2", "kitchen", 2));
            photoDao.insertPhoto(new Photo("https://www.petiteamelie.fr/media/wysiwyg/homepage-images/chambre-bebe-petite-amelie.jpg?auto=compress,format&q=80&h=100&dpr=2", "bedroom", 2));

            photoDao.insertPhoto(new Photo("https://upload.wikimedia.org/wikipedia/commons/3/3c/Fa%C3%A7ade_de_l%27immeuble_sis_au_57_rue_de_Bruxelles.JPG?auto=compress,format&q=80&h=100&dpr=2", "façade", 3));
            photoDao.insertPhoto(new Photo("https://s-ec.bstatic.com/images/hotel/max1024x768/962/96268673.jpg?auto=compress,format&q=80&h=100&dpr=2", "pièce principale", 3));

            photoDao.insertPhoto(new Photo("https://www.nerienlouper.paris/wp-content/uploads/2018/05/chateau-de-versailles-photo.jpg?auto=compress,format&q=80&h=100&dpr=2", "façade", 4));
            photoDao.insertPhoto(new Photo("http://www.culture.gouv.fr/var/culture/storage/images/media/www.culture.gouv.fr/images/actualites/2011/octobre/exposition-versailles-raconte-le-mobilier-national.-quatre-siecles-de-creation-3/116755-1-fre-FR/Exposition-Versailles-raconte-le-Mobilier-national.-Quatre-siecles-de-creation-3.jpg?auto=compress,format&q=80&h=100&dpr=2", "bureau", 4));
            photoDao.insertPhoto(new Photo("http://www.iwishparis.com/wp-content/uploads/2016/03/orangerie-chateau-versailles-andre-le-notre-jcl.jpg?auto=compress,format&q=80&h=100&dpr=2", "jardin", 4));
            photoDao.insertPhoto(new Photo("http://www.nouveautourismeculturel.com/blog/wp-content/contenus/2012/08/Galerie-des-Glaces-.jpg?auto=compress,format&q=80&h=100&dpr=2", "salle à manger", 4));

            photoDao.insertPhoto(new Photo("https://img-3.journaldesfemmes.fr/txQP63kltdw6VHa0PBklOLCpz-c=/910x607/smart/3ff469b4dd554cb185c1ac5ad0d045f5/ccmcms-jdf/10476574.jpg?auto=compress,format&q=80&h=100&dpr=2", "pièce principale", 5));
            photoDao.insertPhoto(new Photo("https://www.expert-peinture.fr/wp-content/uploads/2015/09/5-conseils-pour-peindre-sa-fa%c3%a7ade-%c3%a0-la-perfection-.jpg?auto=compress,format&q=80&h=100&dpr=2", "façade", 5));

            photoDao.insertPhoto(new Photo("http://www.constructions-dantan.fr/docs/Entreprise/restauration/charpente_normandie.jpg?auto=compress,format&q=80&h=100&dpr=2", "façade", 6));
            photoDao.insertPhoto(new Photo("https://www.entre-hotes.com/images/chambres/insolite/chambre-7701.jpg?auto=compress,format&q=80&h=100&dpr=2", "chambre", 6));
            photoDao.insertPhoto(new Photo("https://www.keria.com/media/wysiwyg/CMS/Keria/218/K_erreurs-eclairage-salon_GUIDE.jpg?auto=compress,format&q=80&h=100&dpr=2", "salon", 6));

            photoDao.insertPhoto(new Photo("https://inhabitat.com/wp-content/blogs.dir/1/files/2016/01/Bergeron-Centre-for-Engineering-Excellence-by-ZAS-Architects-3.jpg?auto=compress,format&q=80&h=100&dpr=2", "façade", 7));
            photoDao.insertPhoto(new Photo("https://www.ikea.com/images/photo-dune-chambre-a-coucher-avec-lit-double-lit-bebe-et-jou-53a202d1682582a3f1e13520c2fba648.jpg?f=s?auto=compress,format&q=80&h=100&dpr=2", "chambre", 7));
            photoDao.insertPhoto(new Photo("http://static.cotemaison.fr/medias_11747/w_1362,h_1362,c_crop,x_205,y_0/w_600,h_600,c_fill,g_north/v1517914643/grand-salon-mobilier-rose_6014620.jpg?auto=compress,format&q=80&h=100&dpr=2", "salon", 7));
            photoDao.insertPhoto(new Photo("https://www.maisonapart.com/images/auto/640-480-c/20190128_150738_01-mobalpa-mo18ambiance-scenariovp11.jpg?auto=compress,format&q=80&h=100&dpr=2", "cuisine", 7));

            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(1,1));   // attractingId commence à 1, propertyId commence à 1
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(2,1));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(1,2));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(3,2));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(3,3));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(1,4));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(2,4));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(3,4));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(4,4));
            attractingPropertyJoinDao.insertAttractingPropertyJoin(new AttractingPropertyJoin(2,5));
            return null;
        }
    }
}
