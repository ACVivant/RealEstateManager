package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;

public class DetailActivity extends AppCompatActivity implements PhotoRecyclerViewAdapter.DeletePhotoListener {

    //Design
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    final FragmentManager fm = getSupportFragmentManager();

    DetailFragment fragmentDetail = new DetailFragment();

    private int propertyId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        propertyId = getIntent().getIntExtra(ListHouseFragment.ID_PROPERTY,1);

        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);
        fragmentDetail.setArguments(args);

        fm.beginTransaction().add(R.id.detail_container, fragmentDetail, "2").commit();
    }

    @Override
    public void photoToDelete(long photoId) {

    }
}
