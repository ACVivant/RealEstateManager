package com.openclassrooms.realestatemanager;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.utils.MapUrl;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    //public class DetailFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "DetailFragment";
    private static final float DEFAULT_ZOOM = 13f;

    // Demo data
    private ArrayList<String> photoUrlList = new ArrayList<>();
    private ArrayList<String> textList = new ArrayList<>();
/*    String number = "6";
    String street = "Alexandre Dumas";
    String zipcode = "60800";
    String town = "Crépy-en-Valois";
    String country = "France";*/

    // Real data
    private int propertyId = 0;

    private View v;
    //private GoogleMap mMap;
    private Marker myHomeMarker;

    TextView upForSaleDate;
    TextView soldOnDate;
    TextView description;
    TextView surface;
    TextView rooms;
    TextView bedrooms;
    TextView bathrooms;
    CheckBox shops;
    CheckBox schools;
    CheckBox museum;
    CheckBox park;
    TextView numberInStreet;
    TextView street;
    TextView street2;
    TextView zipcode;
    TextView town;
    TextView country;
    TextView price;

    /* @BindView(R.id.map-detail)
ImageView mMapView;;*/
    private ImageView mMap;

    private List<Property> properties = new ArrayList<>();
    private PropertyViewModel propertyViewModel;
    private Property currentProperty;
    private Address currentAddress;

    private List<Photo> photos = new ArrayList<>();

    private String key;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_detail, container, false);
        key = getContext().getResources().getString(R.string.Google_Maps_API_Key);
        mMap = (ImageView) v.findViewById(R.id.map_detail);
        //ButterKnife.bind(v); // Ca ne marche pas...

        upForSaleDate = (TextView) v.findViewById(R.id.textView27);
        soldOnDate = (TextView) v.findViewById(R.id.textView28);
        description = (TextView) v.findViewById(R.id.editText);
        surface = (TextView) v.findViewById(R.id.textView4);
        rooms = (TextView) v.findViewById(R.id.textView6);
        bedrooms = (TextView) v.findViewById(R.id.textView8);
        bathrooms = (TextView) v.findViewById(R.id.textView10);
        shops = (CheckBox) v.findViewById(R.id.checkBox);
        schools = (CheckBox) v. findViewById(R.id.checkBox2);
        museum = (CheckBox) v.findViewById(R.id.checkBox3);
        park = (CheckBox) v.findViewById(R.id.checkBox4);
        numberInStreet = (TextView) v.findViewById(R.id.textView12);
        street = (TextView) v.findViewById(R.id.textView13);
        street2 = (TextView) v.findViewById(R.id.textView14);
        zipcode = (TextView) v.findViewById(R.id.textView15);
        town = (TextView) v.findViewById(R.id.textView16);
        country = (TextView) v.findViewById(R.id.textView17);
        price = (TextView) v.findViewById(R.id.textView21);

        Bundle bundle = getArguments();
        propertyId = bundle.getInt(ListHouseFragment.ID_PROPERTY);
        //propertyId = 2; // pour les tests

        Log.d(TAG, "onCreateView: bundle " + propertyId);
        propertyViewModel = ViewModelProviders.of(this).get(PropertyViewModel.class);
        propertyViewModel.getAllProperty().observe(this, new Observer<List<Property>>() {
            @Override
            public void onChanged(List<Property> properties) {
                Log.d(TAG, "onChanged");

                currentProperty = properties.get(propertyId-1); // ici propertyId correspond à la position dans la liste récupérée suite au getAllProperty
                currentAddress = currentProperty.getAddress();

                initStaticMap(currentAddress.getNumberInStreet(), currentAddress.getStreet(), currentAddress.getZipcode(), currentAddress.getTown(), currentAddress.getCountry(), key );
                initTextData();

            }
        });

        propertyViewModel.getAllPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                for (int i=0; i<photos.size(); i++) {
                    if(photos.get(i).getProperty()== propertyId) {
                        photoUrlList.add(photos.get(i).getPhotoUri());
                        textList.add(photos.get(i).getPhotoText());
                    }
                }
                initPhotosRecyclerView();
            }
        });


        // Pourquoi ça ne fonctionne pas?
/*        Log.d(TAG, "onCreateView: propertyId pour les photos " + propertyId);
        propertyViewModel.getPhotoFromProperty(propertyId).observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                Log.d(TAG, "onChanged: photos.size " + photos.size());
                for (int i=0; i<photos.size(); i++) {
                    photoUrlList.add(photos.get(i).getPhotoUri());
                    textList.add(photos.get(i).getPhotoText());
                }
                initPhotosRecyclerView();
            }
        });*/


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initDemoData() {
        photoUrlList.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        textList.add("Façade");

        photoUrlList.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        textList.add("Cuisine");

        photoUrlList.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        textList.add("Chambre 1");

        photoUrlList.add("https://i.redd.it/j6myfqglup501.jpg");
        textList.add("Chambre 2");

        photoUrlList.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        textList.add("Salon");

        photoUrlList.add("https://i.redd.it/k98uzl68eh501.jpg");
        textList.add("Salle de bain");
    }

    private void initTextData() {
        upForSaleDate.setText(Utils.convertStringToDate(String.valueOf(currentProperty.getUpForSaleDate())));
        soldOnDate.setText(Utils.convertStringToDate(String.valueOf(currentProperty.getSoldOnDate())));
        description.setText(String.valueOf(currentProperty.getDescription()));
        surface.setText(String.valueOf(currentProperty.getSurface()));
        rooms.setText(String.valueOf(currentProperty.getRooms()));
        bedrooms.setText(String.valueOf(currentProperty.getBedrooms()));
        bathrooms.setText(String.valueOf(currentProperty.getBathroom()));
        shops.setChecked(currentProperty.getShop());
        schools.setChecked(currentProperty.getSchool());
        museum.setChecked(currentProperty.getMuseum());
        park.setChecked(currentProperty.getPark());
        numberInStreet.setText(String.valueOf(currentAddress.getNumberInStreet()));
        street.setText(String.valueOf(currentAddress.getStreet()));
        street2.setText(String.valueOf(currentAddress.getStreet2()));
        zipcode.setText(String.valueOf(currentAddress.getZipcode()));
        town.setText(String.valueOf(currentAddress.getTown()));
        country.setText(String.valueOf(currentAddress.getCountry()));
        price.setText(String.valueOf(currentProperty.getPrice()));
}

    private void initPhotosRecyclerView() {
        Log.d(TAG, "initPhotosRecyclerView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.photo_recyclerview_container);
        PhotoRecyclerViewAdapter adapter = new PhotoRecyclerViewAdapter(getContext(), photoUrlList, textList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    //------------------------------------------------------------------
    // Map
    //------------------------------------------------------------------

    public void initStaticMap(String number, String street, String zipcode, String town, String country, String key) {
        MapUrl mapUrl = new MapUrl();
        String srcMap = mapUrl.createUrl(number, street, zipcode, town, country, key);
        Log.d(TAG, "initStaticMap: " + srcMap);
        Picasso.get().load(srcMap).into(mMap);
    }

    private void updatePropertyDetail(List<Property> allProperties){

    }
}
