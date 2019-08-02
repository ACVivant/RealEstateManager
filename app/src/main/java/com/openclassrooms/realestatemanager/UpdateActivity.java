package com.openclassrooms.realestatemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.openclassrooms.realestatemanager.adapters.PhotoRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.models.Photo;
import com.openclassrooms.realestatemanager.viewmodels.PropertyViewModel;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity implements PhotoRecyclerViewAdapter.DeletePhotoListener, UpdateFragment.OnValidateClickedListener  {
  private static final String TAG = "UpdateActivity";
    public static final String  FROM_UPDATE_REQUEST = "fromUpadateActivity";
    private PropertyViewModel propertyViewModel;

    private int propertyId;
    private List<Long> photoToDeleteList = new ArrayList<>();
    private List<String> photoToCreateList = new ArrayList<>();
    private List<String> legendToCreateList = new ArrayList<>();

    final FragmentManager fm = getSupportFragmentManager();
    UpdateFragment fragmentUpdate = new UpdateFragment();
    private AppBarLayout toolbar;
    private Photo myPhotoToDelete;
    private int deleteId=0;

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

        configureViewModel();
    }

    @Override
    public void photoToDelete(long photoId) {
        Log.d(TAG, "photoToDelete: photoId " +photoId);
        photoToDeleteList.add(photoId);
        getPhotosToDelete(photoId);
    }

    @Override
    public void onValidateClicked(int propertyId) {

/*        for (int i=0; i<photoToCreateList.size(); i++) {
            Photo photoToAdd = new Photo(photoToCreateList.get(i), legendToCreateList.get(i), propertyId);
            propertyViewModel.insertPhoto(photoToAdd);
            Log.d(TAG, "onValidateClicked: photoToCreate " + legendToCreateList.get(i));
        }*/


/*        for (int i=0; i<photoToDeleteList.size(); i++) {
            getPhotosToDelete(photoToDeleteList.get(i));
        }*/

        Log.d(TAG, "onValidateClicked: on passe par ici");


    }

    private void getPhotosToDelete(long id){
        this.propertyViewModel.getPhotoFromId(id).observe(this, this::updatePhotoDeleteList);
    }

    private void updatePhotoDeleteList(Photo photos){
        Log.d(TAG, "updatePhotoDeleteList: on passe par ici");
        if (deleteId<photoToDeleteList.size()) {
            myPhotoToDelete = photos;
            propertyViewModel.deletePhoto(myPhotoToDelete);
            deleteId+=1;
        }
    }

/*    @Override
    public void applyOthersPhoto(String photoUri, String photoLegend) {
    *//*photoToCreateList.add(photoUri);
    legendToCreateList.add(photoLegend);
        Log.d(TAG, "applyOthersPhoto");*//*
    }

    @Override
    public void applyMainPhoto(String photoUri, String photoLegend, boolean main) {

    }*/


    //--------------------------------------------------------------------------------------------------
    // Database
    //--------------------------------------------------------------------------------------------------

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }
}
