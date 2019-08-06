package com.openclassrooms.realestatemanager;

import android.app.Notification;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.openclassrooms.realestatemanager.adapters.PhotoRecyclerViewAdapter;
import com.openclassrooms.realestatemanager.injections.Injection;
import com.openclassrooms.realestatemanager.injections.ViewModelFactory;
import com.openclassrooms.realestatemanager.utils.RemApp;
import com.openclassrooms.realestatemanager.viewmodels.PropertyViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

/**
 * Activity which organise creation of new property in linf with CreateHomeFragment
 */
public class CreateHomeActivity extends AppCompatActivity implements PhotoRecyclerViewAdapter.DeletePhotoListener, UpdateFragment.OnValidateClickedListener, CreateHomeFragment.OnCreateValidateClickedListener  {

    private static final String TAG = "CreateHomeActivity";
    public static final String MAIN_PHOTO_REQUEST = "Create_main_photo";
    public static final String OTHERS_PHOTO_REQUEST = "Create_others_photos";
    public static final String WHICH_REQUEST = "Create_main_others_photos";

    private PropertyViewModel propertyViewModel;
    private NotificationManagerCompat notificationManager;

    private int propertyId;

    final FragmentManager fm = getSupportFragmentManager();
    CreateHomeFragment fragmentCreate = new CreateHomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        notificationManager = NotificationManagerCompat.from(this);

        propertyId = getIntent().getIntExtra(ListHouseFragment.ID_PROPERTY,1);

        configureToolbar();

        Bundle args = new Bundle();
        args.putInt(ListHouseFragment.ID_PROPERTY, propertyId);
        fragmentCreate.setArguments(args);

        fm.beginTransaction().add(R.id.detail_container, fragmentCreate, "3").commit();

        configureViewModel();

    }

    // Configure toolbar
    private void configureToolbar(){
        Toolbar toolbar;
        // Get the toolbar view inside the activity layout
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }

    //--------------------------------------------------------------------------------------------------
    // Database
    //--------------------------------------------------------------------------------------------------

    private void configureViewModel() {
        ViewModelFactory mViewModelFactory = Injection.provideViewModelFactory(this);
        this.propertyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyViewModel.class);
    }

    //-----------------------------------------------------------------------------------------------
    // Notification
    //----------------------------------------------------------------------------------------------

    private void sendNotification() {
        Log.d(TAG, "sendNotification");
        Notification notification = new NotificationCompat.Builder(this, RemApp.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_message))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }

    @Override
    public void onValidateClicked(int propertyId) {

    }

    @Override
    public void photoToDelete(long photoId) {

    }

    @Override
    public void onCreateValidateClicked() {
    }
}
