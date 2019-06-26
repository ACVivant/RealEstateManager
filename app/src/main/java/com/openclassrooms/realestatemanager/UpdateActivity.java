package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.appbar.AppBarLayout;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.Status;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.openclassrooms.realestatemanager.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements PhotoRecyclerViewAdapter.DeletePhotoListener  {

    private static final String TAG = "UpdateActivity";
    public static final String  FROM_UPDATE_REQUEST = "fromUpadateActivity";

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

    @BindView(R.id.create_all_others_photos)
    LinearLayout photoLayout;
    @BindView(R.id.add_main_photo)
    ImageButton updateMainPhoto;
    @BindView(R.id.create_add_main_photo_text)
    TextView addMainPhotoText;
    @BindView(R.id.create_add_others_photo_text)
    TextView addOthersPhotosText;
    @BindView(R.id.add_more_photo)
    ImageButton addPhotos;
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
    @BindView(R.id.number_photo_more)
    TextView numberPhotos;

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


    private int propertyId;
    private List<Long> photoToDeleteList = new ArrayList<>();

    final FragmentManager fm = getSupportFragmentManager();
    UpdateFragment fragmentUpdate = new UpdateFragment();
    private AppBarLayout toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        propertyId = getIntent().getIntExtra(ListHouseFragment.ID_PROPERTY,1);

        toolbar = findViewById(R.id.appBarLayout);
        toolbar.setVisibility(View.GONE);

        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);
        fragmentUpdate.setArguments(args);

        fm.beginTransaction().add(R.id.detail_container, fragmentUpdate, "2").commit();

    }
    @Override
    public void photoToDelete(long photoId) {
        photoToDeleteList.add(photoId);
    }
}
