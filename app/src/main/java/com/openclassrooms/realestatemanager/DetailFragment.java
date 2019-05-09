package com.openclassrooms.realestatemanager;


import android.annotation.TargetApi;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;


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
    String number = "6";
    String street = "Alexandre Dumas";
    String zipcode = "60800";
    String town = "Crépy-en-Valois";
    String country = "France";


    private View v;
    private GoogleMap mMap;
    private Marker myHomeMarker;

    /* @BindView(R.id.map-detail)
ImageView mMapView;;*/
 private ImageView mMapView;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_detail, container, false);

        mMapView = (ImageView) v.findViewById(R.id.map_detail);
        //ButterKnife.bind(v); // Ca ne marche pas...


        initDemoData();
        initPhotosRecyclerView();

        String key = getContext().getResources().getString(R.string.Google_Maps_API_Key);
        initStaticMap(number, street,zipcode, town, country, key);
        //initMap();

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

    private void initPhotosRecyclerView() {
        Log.d(TAG, "initRecyclerView");

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
        Picasso.get().load(srcMap).into(mMapView);
    }

}
