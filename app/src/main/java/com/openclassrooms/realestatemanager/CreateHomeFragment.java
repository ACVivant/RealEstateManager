package com.openclassrooms.realestatemanager;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.RemApp;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewmodels.PropertyViewModel;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

import static android.app.Activity.RESULT_OK;
import static com.openclassrooms.realestatemanager.UpdateFragment.IS_MAIN_PHOTO;

/**
 * Created by Anne-Charlotte Vivant on 02/08/2019.
 */
public class CreateHomeFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private PropertyViewModel propertyViewModel;
    private static final int CREATE_FRAGMENT_REQUEST_CODE = 133;
    public static final String FRAGMENT_REQUEST = "whichFragment";
    private static final String PHOTO_URI = "photoUri";
    private static final String PHOTO_LEGEND = "photoLegend";

    private NotificationManagerCompat notificationManager;

    public static final String TAG = "CreateHomeActivity";
    public static final String MAIN_PHOTO_REQUEST = "Create_main_photo";
    public static final String OTHERS_PHOTO_REQUEST = "Create_others_photos";
    public static final String WHICH_REQUEST = "Create_main_orothers_photos";

    private View v;

    Spinner spinnerStatus;
    Spinner spinnerType;
    EditText descriptionText;
    EditText price;
    EditText surface;
    RecyclerView photosRecyclerView;
    ImageButton addMainPhoto;
    ImageView mainPhotoPreview;
    ImageView photo1;
    ImageView photo2;
    ImageView photo3;
    ImageView photo4;
    ImageButton addPhotos;
    TextView numberPhotos;
    LinearLayout deletePhotoLayout;

    EditText rooms;
    EditText bedrooms;
    EditText bathrooms;

    EditText addressNumber;
    EditText addressStreet;
    EditText addressStreet2;
    EditText addressZipcode;
    EditText addressTown;
    EditText addressCountry;

    CheckBox checkboxSchool;
    CheckBox checkboxShop;
    CheckBox checkboxPark;
    CheckBox checkboxMuseum;

    EditText dateUpForSale;
    EditText dateSoldOn;
    Spinner spinnerAgent;

    Button saveProperty;
    Button resetProperty;

    private String newStatus;
    private String newDescription;
    private int newPrice;

    private int newSurface;
    private int newRooms;
    private int newBedrooms;
    private int newBathrooms;

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

    private String newSoldOn;
    private int intUpForSale;
    private int intSoldOn;

    private int agentId;
    private int statusId;
    private int typeId;
    private int propertyId;

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

    //private int initPropertyId;

    private Context mContext;
    private  boolean tabletSize;

    public CreateHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_create_home, container, false);
        mContext = getContext();
        tabletSize = getResources().getBoolean(R.bool.isTablet);
        mContext = getContext();
        notificationManager = NotificationManagerCompat.from(mContext);
        //initPropertyId = savedInstanceState.getInt(MainActivity.PROPERTY_ID_SAVED);

        photosRecyclerView = v.findViewById(R.id.update_photo_recyclerview_container);
        photosRecyclerView.setVisibility(View.GONE);
        deletePhotoLayout = v.findViewById(R.id.delete_photo_layout);
        deletePhotoLayout.setVisibility(View.GONE);

        spinnerStatus = v.findViewById(R.id.create_spinner_status);
        spinnerType= v.findViewById(R.id.create_spinner_type);
        descriptionText= v.findViewById(R.id.create_description_text);
        price= v.findViewById(R.id.create_insert_price_text);
        surface= v.findViewById(R.id.create_insert_surface);
        addMainPhoto= v.findViewById(R.id.add_main_photo);
         mainPhotoPreview= v.findViewById(R.id.create_main_photo_preview);
        photo1= v.findViewById(R.id.create_photo_more_preview1);
        photo2= v.findViewById(R.id.create_photo_more_preview2);
        photo3= v.findViewById(R.id.create_photo_more_preview3);
        photo4= v.findViewById(R.id.create_photo_more_preview4);
        addPhotos= v.findViewById(R.id.add_more_photo);
        numberPhotos= v.findViewById(R.id.number_photo_more);

        rooms= v.findViewById(R.id.create_insert_rooms);
        bedrooms= v.findViewById(R.id.create_insert_bedrooms);
        bathrooms= v.findViewById(R.id.create_insert_bathrooms);

        addressNumber= v.findViewById(R.id.create_insert_address_number);
        addressStreet= v.findViewById(R.id.create_insert_address_street);
        addressStreet2= v.findViewById(R.id.create_insert_address_street2);
        addressZipcode= v.findViewById(R.id.create_insert_address_zipcode);
        addressTown= v.findViewById(R.id.create_insert_address_town);
        addressCountry= v.findViewById(R.id.create_insert_address_country);

        checkboxSchool= v.findViewById(R.id.create_checkbox_school);
        checkboxShop= v.findViewById(R.id.create_checkbox_shop);
        checkboxPark= v.findViewById(R.id.create_checkbox_park);
        checkboxMuseum= v.findViewById(R.id.create_checkbox_museum);

        dateUpForSale= v.findViewById(R.id.create_insert_upforsale);
        dateSoldOn= v.findViewById(R.id.create_insert_soldon);
        spinnerAgent= v.findViewById(R.id.create_spinner_agent);

        saveProperty= v.findViewById(R.id.save_new_property);
        resetProperty= v.findViewById(R.id.reset_new_property);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.create_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mContext,
                R.array.search_type_answer, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter1);
        spinnerType.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext,
                R.array.create_agent_name, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAgent.setAdapter(adapter2);
        spinnerAgent.setOnItemSelectedListener(this);

        saveProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                configureViewModel();
                savePropertyData();
            }
        });

        addMainPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(CreateHomeActivity.MAIN_PHOTO_REQUEST, TAG);
            }
        });

        addPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(CreateHomeActivity.OTHERS_PHOTO_REQUEST, TAG);
            }
        });

        resetProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tabletSize) {
                    getActivity().finish();
                } else {
                    Bundle args = new Bundle();
                    //args.putInt(ListHouseFragment.ID_PROPERTY, initPropertyId);

                    DetailFragment detail =new DetailFragment();
                    //detail.setArguments(args);
                    final FragmentManager fm = getFragmentManager();

                    fm.beginTransaction().replace(R.id.frame_layout_detail, detail, "2").commit();
                }
            }
        });
       return v;
    }

    // Save data entered by user
    private void savePropertyData() {
        String newUpForSale;

        newStatus = spinnerStatus.getSelectedItem().toString();
        statusId = spinnerStatus.getSelectedItemPosition() + 1;

        if (addressNumber.getText().toString().trim().isEmpty() ||
                addressStreet.getText().toString().trim().isEmpty() ||
                addressZipcode.getText().toString().trim().isEmpty() ||
                addressTown.getText().toString().trim().isEmpty() ||
                addressCountry.getText().toString().trim().isEmpty()) {
            Toast.makeText(mContext, getResources().getString(R.string.address_missing), Toast.LENGTH_LONG).show();
        } else {
            Log.d(TAG, "savePropertyData: mainPhotoUri " + mainPhotoUri);
            if (mainPhotoUri.trim().isEmpty()) {
                Toast.makeText(mContext, getResources().getString(R.string.photo_missing), Toast.LENGTH_LONG).show();
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
                } else {
                    newUpForSale = "99/99/9999";
                    intUpForSale = Utils.convertStringDateToIntDate(newUpForSale);
                }

                if (dateSoldOn.getText().toString().length() == 10) {
                    newSoldOn = dateSoldOn.getText().toString();
                    intSoldOn = Utils.convertStringDateToIntDate(newSoldOn);
                } else {
                    newSoldOn = "99/99/9999";
                    intSoldOn = Utils.convertStringDateToIntDate(newSoldOn);
                }

                agentId = spinnerAgent.getSelectedItemPosition() + 1;

                if (statusId==4 && intSoldOn==99999999) {
                    Toast.makeText(mContext, getResources().getString(R.string.soldon_date_missing), Toast.LENGTH_LONG).show();
                } else {
                    createProperty();
                    if (!tabletSize) {
                        getActivity().finish();
                    } else {
                        Bundle args = new Bundle();
                        //args.putInt(ListHouseFragment.ID_PROPERTY, initPropertyId);

                        DetailFragment detail =new DetailFragment();
                       // detail.setArguments(args);
                        final FragmentManager fm = getFragmentManager();

                        fm.beginTransaction().replace(R.id.frame_layout_detail, detail, "2").commit();
                    }
                }
            }
        }
    }

    // Create the new property
    private void createProperty() {

        if(newStatus.equals(R.string.status_sold)&& newSoldOn.equals("99/99/9999")) {
            Toast.makeText(mContext, getResources().getString(R.string.soldon_date_missing), Toast.LENGTH_LONG).show();
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

    //--------------------------------------------------------------------------------------------------
    // Database
    //--------------------------------------------------------------------------------------------------

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(mContext);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    //--------------------------------------------------------------------------------------------------
    // Photos
    //--------------------------------------------------------------------------------------------------

    private void openDialog(String which, String tag) {
        PhotoDialogFragment dialog = new PhotoDialogFragment();
        Bundle args = new Bundle();
        args.putString(WHICH_REQUEST, which);
        args.putString(FRAGMENT_REQUEST, TAG);
        dialog.setArguments(args);
        dialog.setTargetFragment(CreateHomeFragment.this,CREATE_FRAGMENT_REQUEST_CODE);
        dialog.show(getFragmentManager(), "dialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if( resultCode != RESULT_OK ) {
            return;
        }
        if( requestCode == CREATE_FRAGMENT_REQUEST_CODE ) {
            Log.d(TAG, "onActivityResult: requestCode == CREATE_FRAGMENT_REQUEST_CODE");
            if(!data.getBooleanExtra(IS_MAIN_PHOTO, false)) {
                photosList.add(data.getStringExtra(PHOTO_URI));
                legendList.add(data.getStringExtra(PHOTO_LEGEND));
                nbrePhotos += 1;
                Log.d(TAG, "onActivityResult: on passe par là - !data.getBooleanExtra(IS_MAIN_PHOTO");
                setPreviewPhotos();
            } else {
                Log.d(TAG, "onActivityResult: on passe par là - data.getBooleanExtra(IS_MAIN_PHOTO");
                mainPhotoUri= data.getStringExtra(PHOTO_URI);
                mainPhotoLegend = data.getStringExtra(PHOTO_LEGEND);
                setMainPhoto();
            }
        }
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    private void addMainPhoto() {
        this.chooseImageFromPhone();
    }

    private void chooseImageFromPhone() {
        if (!EasyPermissions.hasPermissions(mContext, PERMS)) {
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
                Toast.makeText(mContext, getString(R.string.no_photo), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static Intent newIntent(String photoUri, String photoLegend, Boolean main) {
        Intent intent = new Intent();
        intent.putExtra(PHOTO_URI, photoUri);
        intent.putExtra(PHOTO_LEGEND, photoLegend);
        intent.putExtra(IS_MAIN_PHOTO, main);
        return intent;
    }

    //-----------------------------------------------------------------------------------------------
    // Notification
    //----------------------------------------------------------------------------------------------

    private void sendNotification() {
        Log.d(TAG, "sendNotification");
        Notification notification = new NotificationCompat.Builder(mContext, RemApp.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
