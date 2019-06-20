package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.database.dao.PropertyDao;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CreateHomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, PhotoDialogFragment.DialogListener {

    private PropertyViewModel propertyViewModel;

    private NotificationManagerCompat notificationManager;

    private static final String TAG = "CreateHomeActivity";
    public static final String MAIN_PHOTO_REQUEST = "Create_main_photo";
    public static final String OTHERS_PHOTO_REQUEST = "Create_others_photos";
    public static final String WHICH_REQUEST = "Create_main_orothers_photos";


    @BindView(R.id.create_spinner_status)
    Spinner spinnerStatus;
    @BindView(R.id.create_spinner_type)
    Spinner spinnerType;
    @BindView(R.id.create_description_text)
    EditText descriptionText;
    @BindView(R.id.create_insert_price_text)
    EditText price;
    @BindView(R.id.create_insert_surface)
    EditText surface;
    @BindView(R.id.update_photo_recyclerview_container)
    RecyclerView photosRecyclerView;
    @BindView(R.id.add_main_photo)
    ImageButton addMainPhoto;
    @BindView(R.id.create_main_photo_preview)
    ImageView mainPhotoPreview;
    @BindView(R.id.create_photo_more_preview1)
    ImageView photo1;
    @BindView(R.id.create_photo_more_preview2)
    ImageView photo2;
    @BindView(R.id.create_photo_more_preview3)
    ImageView photo3;
    @BindView(R.id.create_photo_more_preview4)
    ImageView photo4;
    @BindView(R.id.add_more_photo)
    ImageButton addPhotos;
    @BindView(R.id.number_photo_more)
    TextView numberPhotos;
    @BindView(R.id.delete_photo_layout)
    LinearLayout deletePhotoLayout;

    @BindView(R.id.create_insert_rooms)
    EditText rooms;
    @BindView(R.id.create_insert_bedrooms)
    EditText bedrooms;
    @BindView(R.id.create_insert_bathrooms)
    EditText bathrooms;

    @BindView(R.id.create_insert_address_number)
    EditText addressNumber;
    @BindView(R.id.create_insert_address_street)
    EditText addressStreet;
    @BindView(R.id.create_insert_address_street2)
    EditText addressStreet2;
    @BindView(R.id.create_insert_address_zipcode)
    EditText addressZipcode;
    @BindView(R.id.create_insert_address_town)
    EditText addressTown;
    @BindView(R.id.create_insert_address_country)
    EditText addressCountry;

    @BindView(R.id.create_checkbox_school)
    CheckBox checkboxSchool;
    @BindView(R.id.create_checkbox_shop)
    CheckBox checkboxShop;
    @BindView(R.id.create_checkbox_park)
    CheckBox checkboxPark;
    @BindView(R.id.create_checkbox_museum)
    CheckBox checkboxMuseum;

    @BindView(R.id.create_insert_upforsale)
    EditText dateUpForSale;
    @BindView(R.id.create_insert_soldon)
    EditText dateSoldOn;
    @BindView(R.id.create_spinner_agent)
    Spinner spinnerAgent;

    @BindView(R.id.save_new_property)
    Button saveProperty;
    @BindView(R.id.reset_new_property)
    Button resetProperty;

    private String newStatus;
    private String newDescription;
    private int newPrice;
    private String newType;
    private int newSurface;
    private int newRooms;
    private int newBedrooms;
    private int newBathrooms;
    private String newLegend;

    private String newAddressNumber;
    private String newAddressStreet;
    private String newAddressStreet2;
    private String newZipcode;
    private String newTown;
    private String newCountry;

    private Boolean nearSchool;
    private Boolean nearShop;
    private Boolean nearPark;
    private Boolean nearMuseum;

    private String newUpForSale;
    private String newSoldOn;
    private String newAgent;

    private int intUpForSale;
    private int intSoldOn;

    private int agentId;
    private int statusId;
    private int typeId;
    private long propertyId;

    private Address myAddress;
    private boolean  mainPhotoOk;
    private int nbrePhotos=0;

    // 1 - STATIC DATA FOR PICTURE
    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS = 100;
    private Uri uriImageSelected;
    private String newPhotoUrl;
    private static final int RC_CHOOSE_PHOTO = 200;
    private ArrayList<String> photosList = new ArrayList<>();
    private ArrayList<String> legendList = new ArrayList<>();
    private String mainPhotoUri;
    private String mainPhotoLegend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_home);
        ButterKnife.bind(this);

        notificationManager = NotificationManagerCompat.from(this);

        configureView();
    }

    private void configureView() {

        photosRecyclerView.setVisibility(View.GONE);
        deletePhotoLayout.setVisibility(View.GONE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.create_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.search_type_answer, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.create_agent_name, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgent.setAdapter(adapter2);
        spinnerAgent.setOnItemSelectedListener(this);

        saveProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureViewModel();
                savePropertyData();
                createProperty();
                launchMainActivityDetail();
            }
        });

        addMainPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(MAIN_PHOTO_REQUEST);
            }
        });

        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(OTHERS_PHOTO_REQUEST);
            }
        });

        resetProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateHomeActivity.this.finish();
            }
        });
    }

    private void savePropertyData() {
        newStatus = spinnerStatus.getSelectedItem().toString();
        statusId = spinnerStatus.getSelectedItemPosition() + 1;

        if (addressNumber.getText().toString().trim().isEmpty() ||
                addressStreet.getText().toString().trim().isEmpty() ||
                addressZipcode.getText().toString().trim().isEmpty() ||
                addressTown.getText().toString().trim().isEmpty() ||
                addressCountry.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Il faut entrer l'adrese du bien", Toast.LENGTH_LONG).show();
        } else {
            if (!mainPhotoOk) {
                Toast.makeText(this, "Il faut définir une photo principale", Toast.LENGTH_LONG).show();
            } else {
                if (!descriptionText.getText().toString().isEmpty()) {
                    newDescription = descriptionText.getText().toString();
                } else {
                    newDescription = "N/A";
                }

                if (!price.getText().toString().isEmpty()) {
                    newPrice = Integer.parseInt(price.getText().toString());
                } else {
                    newPrice = 0;
                }

                newType = spinnerType.getSelectedItem().toString();
                typeId = spinnerType.getSelectedItemPosition() + 1;

                if (!surface.getText().toString().isEmpty()) {
                    newSurface = Integer.parseInt(surface.getText().toString());
                } else {
                    newSurface = 0;
                }

                if (!rooms.getText().toString().isEmpty()) {
                    newRooms = Integer.parseInt(rooms.getText().toString());
                } else {
                    newRooms = 999;
                }

                if (!bedrooms.getText().toString().isEmpty()) {
                    newBedrooms = Integer.parseInt(bedrooms.getText().toString());
                } else {
                    newBedrooms = 999;
                }

                if (!bathrooms.getText().toString().isEmpty()) {
                    newBathrooms = Integer.parseInt(bathrooms.getText().toString());
                } else {
                    newBathrooms = 999;
                }

                newAddressNumber = addressNumber.getText().toString();
                if (!addressStreet.getText().toString().isEmpty()) {
                    newAddressStreet = addressStreet.getText().toString();
                } else {
                    newAddressStreet = " ";
                }
                newAddressStreet2 = addressStreet2.getText().toString();
                newZipcode = addressZipcode.getText().toString();
                newTown = addressTown.getText().toString();
                newCountry = addressCountry.getText().toString();

                nearSchool = checkboxSchool.isChecked();
                nearShop = checkboxShop.isChecked();
                nearPark = checkboxPark.isChecked();
                nearMuseum = checkboxMuseum.isChecked();

                if (dateUpForSale.getText().toString().length() == 10) {
                    newUpForSale = dateUpForSale.getText().toString();
                    intUpForSale = Utils.convertStringDateToIntDate(newUpForSale);
                    Log.d(TAG, "savePropertyData: upForSale enteredDate " + newUpForSale);
                    Log.d(TAG, "savePropertyData: upForSale formattedDate " + intUpForSale);
                } else {
                    newUpForSale = "99/99/9999";
                    intUpForSale = Utils.convertStringDateToIntDate(newUpForSale);
                    Log.d(TAG, "savePropertyData: enteredDate " + newUpForSale);
                    Log.d(TAG, "savePropertyData: formattedDate " + intUpForSale);
                }

                if (dateSoldOn.getText().toString().length() == 10) {
                    newSoldOn = dateSoldOn.getText().toString();
                    intSoldOn = Utils.convertStringDateToIntDate(newSoldOn);
                    Log.d(TAG, "savePropertyData: soldOn enteredDate " + newUpForSale);
                    Log.d(TAG, "savePropertyData: soldOn formattedDate " + intUpForSale);
                } else {
                    newSoldOn = "99/99/9999";
                    intSoldOn = Utils.convertStringDateToIntDate(newSoldOn);
                }

                newAgent = spinnerAgent.getSelectedItem().toString();
                agentId = spinnerAgent.getSelectedItemPosition() + 1;
            }
        }
    }

    private void createProperty() {
        Log.d(TAG, "createProperty: called");

        if(newStatus.equals(R.string.status_sold)&& newSoldOn.equals("99/99/9999")) {
            Toast.makeText(this, "Il faut entrer une date de vente", Toast.LENGTH_LONG).show();
        } else {

            Property myProperty = new Property(newPrice, newRooms, newBedrooms, newBathrooms, newDescription, intUpForSale, intSoldOn, newSurface, nearShop, nearSchool, nearMuseum, nearPark, typeId, agentId, statusId, mainPhotoUri, nbrePhotos, newAddressNumber, newAddressStreet, newAddressStreet2, newZipcode, newTown, newCountry);

            Observable<Property> propertyObservable = Observable
                    .fromCallable(new Callable<Property>() {
                        @Override
                        public Property call() throws Exception {
                            propertyViewModel.createProperty(myProperty, photosList, legendList, mainPhotoUri, mainPhotoLegend);
                            return myProperty;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

            propertyObservable.subscribe(new Observer<Property>() {
                @Override
                public void onSubscribe(Disposable d) {
                    Log.d(TAG, "onSubscribe: called");
                }

                @Override
                public void onNext(Property property) {
                    sendNotification();
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, "onError: " + e.getMessage());
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete: called");

                }
            });
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void configureViewModel() {
        Log.d(TAG, "configureViewModel");
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    private void launchMainActivityDetail() {
        Boolean displayDetail = false;
        Intent intent = new Intent(CreateHomeActivity.this, MainActivity.class);
        intent.putExtra(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
        startActivity(intent);
    }

    private void launchMainActivity() {
        Boolean displayDetail = false;
        Intent intent = new Intent(CreateHomeActivity.this, MainActivity.class);
        intent.putExtra(ListHouseFragment.DISPLAY_DETAIL, displayDetail);
        startActivity(intent);
    }

    //--------------------------------------------------------------------------------------------------
    // Photos
    //--------------------------------------------------------------------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponse(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    private void addMainPhoto() {
        this.chooseImageFromPhone();
    }

    private void chooseImageFromPhone() {
        if (!EasyPermissions.hasPermissions(this, PERMS)) {
            EasyPermissions.requestPermissions(this, getString(R.string.photo_permission_denied), RC_IMAGE_PERMS, PERMS);
            return;
        }
        // Launch an "Selection Image" Activity
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_CHOOSE_PHOTO);
    }

    private void handleResponse(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_CHOOSE_PHOTO) {
            if (resultCode == RESULT_OK) { //SUCCESS
                this.uriImageSelected = data.getData();
                newPhotoUrl = uriImageSelected.toString();
                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.uriImageSelected)
                        .apply(RequestOptions.circleCropTransform())
                        .into(this.mainPhotoPreview);

            } else {
                Toast.makeText(this, getString(R.string.no_photo), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openDialog(String which) {
        PhotoDialogFragment dialog = new PhotoDialogFragment();
        Bundle args = new Bundle();
        args.putString(WHICH_REQUEST, which);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "dialog");
    }

    private void setMainPhoto() {
        Glide.with(this) //SHOWING PREVIEW OF IMAGE
                .load(this.mainPhotoUri)
                .apply(RequestOptions.circleCropTransform())
                .into(this.mainPhotoPreview);
    }

    private void setPreviewPhotos() {
        numberPhotos.setText(String.valueOf(photosList.size()));

        if (photosList.size() > 0) {
            Glide.with(this) //SHOWING PREVIEW OF IMAGE
                    .load(this.photosList.get(0))
                    .apply(RequestOptions.circleCropTransform())
                    .into(this.photo1);

            if (photosList.size() > 1) {
                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.photosList.get(1))
                        .apply(RequestOptions.circleCropTransform())
                        .into(this.photo2);

                if (photosList.size() > 2) {
                    Glide.with(this) //SHOWING PREVIEW OF IMAGE
                            .load(this.photosList.get(2))
                            .apply(RequestOptions.circleCropTransform())
                            .into(this.photo3);

                    if (photosList.size() > 3) {
                        Glide.with(this) //SHOWING PREVIEW OF IMAGE
                                .load(this.photosList.get(3))
                                .apply(RequestOptions.circleCropTransform())
                                .into(this.photo4);
                    }
                }
            }
        }
    }

    @Override
    public void applyOthersPhoto(String photoUri, String photoLegend) {
        photosList.add(photoUri);
        legendList.add(photoLegend);
        nbrePhotos+=1;
        setPreviewPhotos();
    }

    @Override
    public void applyMainPhoto(String photoUri, String photoLegend, boolean main) {
        mainPhotoUri = photoUri;
        mainPhotoLegend = photoLegend;
        mainPhotoOk = main;
        nbrePhotos+=1;
        setMainPhoto();
    }

    private void createPhotos() {
        propertyViewModel.insertPhoto(new Photo(mainPhotoUri, mainPhotoLegend, propertyId));

        for (int i=0; i<photosList.size(); i++) {
            propertyViewModel.insertPhoto(new Photo(photosList.get(i), legendList.get(i), propertyId));
        }
        sendNotification();
    }

    private void sendNotification() {
        Notification notification = new NotificationCompat.Builder(this, RemApp.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }
}
