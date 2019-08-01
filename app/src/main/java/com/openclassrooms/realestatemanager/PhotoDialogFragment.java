package com.openclassrooms.realestatemanager;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.content.FileProvider;
import androidx.room.OnConflictStrategy;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Anne-Charlotte Vivant on 03/06/2019.
 */
public class PhotoDialogFragment extends AppCompatDialogFragment {

    private static final String TAG = "PhotoDialogFragment";

    private ImageButton search;
    private ImageButton take;
    private EditText legend;
    private ImageView photoPreviewAdd;
    private ImageView photoPreviewTake;

    private static final String PERMS = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int RC_IMAGE_PERMS1 = 101;
    private static final int RC_CHOOSE_PHOTO1 = 201;
    private static final int RC_TAKE_PHOTO1 = 301;
    private Uri uriImageSelected;
    private String newPhotoUrl;
    private Uri pictureUri;
    private String currentPhotoPath;
    private Uri photoURI;
    private String whichRequest;
    private String photoLegend;

    //private DialogListener listener;
    private Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.photo_picker_dialog, null);

        Bundle bundle = getArguments();
        whichRequest = bundle.getString(CreateHomeActivity.WHICH_REQUEST);

        context = this.getContext();

        search = view.findViewById(R.id.search_photo);
        take = view.findViewById(R.id.take_photo);
        legend = view.findViewById(R.id.create_photo_description);
        photoPreviewAdd = view.findViewById(R.id.dialog_add_photo_preview);
        photoPreviewTake = view.findViewById(R.id.dialog_take_photo_preview);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPhoto();
            }
        });

        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });

        builder.setView(view)
                .setTitle("Photo")
                .setNegativeButton(getResources().getString(R.string.reset), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (legend.getText()==null ||photoURI.toString().isEmpty()) {
                            Toast.makeText(getContext(), getResources().getString(R.string.photo_and_legend_needed), Toast.LENGTH_LONG).show();
                        } else {
                            photoLegend = legend.getText().toString();
                            if (whichRequest.equals(CreateHomeActivity.OTHERS_PHOTO_REQUEST)) {
                                if( getTargetFragment() == null ) {
                                    return;
                                }
                                Log.d(TAG, "onClick OK");
                                Intent intent = UpdateFragment.newIntent(photoURI.toString(), photoLegend, false);
                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                            }
                            if (whichRequest.equals(CreateHomeActivity.MAIN_PHOTO_REQUEST)) {
                                //listener.applyMainPhoto(photoURI.toString(), photoLegend, true);
                                Log.d(TAG, "onClick OK");
                                Intent intent = UpdateFragment.newIntent(photoURI.toString(), photoLegend, true);
                                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                            }
                            dismiss();
                        }
                    }
                });
        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Calling the appropriate method after activity result
        this.handleResponse(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void takePhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        context.getPackageManager();
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "takePhoto: ",ex );

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d(TAG, "takePhoto: photoFile non null");
                photoURI = FileProvider.getUriForFile(context,
                        "com.openclassrooms.realestatemanager",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, RC_TAKE_PHOTO1);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "REM_" + timeStamp;
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS1)
    private void addPhoto() {
        this.chooseImageFromPhone();
    }

    private void chooseImageFromPhone() {
        if (!EasyPermissions.hasPermissions(this.getContext(), PERMS)) {
            EasyPermissions.requestPermissions(this, getString(R.string.photo_permission_denied), RC_IMAGE_PERMS1, PERMS);
            return;
        }
        // Launch an "Selection Image" Activity
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RC_CHOOSE_PHOTO1);
    }

    private void handleResponse(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_CHOOSE_PHOTO1) {
            if (resultCode == RESULT_OK) { //SUCCESS
                this.uriImageSelected = data.getData();
                photoURI = uriImageSelected;
                newPhotoUrl = uriImageSelected.toString();
                Log.d(TAG, "handleResponse: uri " + newPhotoUrl);

                Glide.with(this) //SHOWING PREVIEW OF IMAGE
                        .load(this.uriImageSelected)
                        .apply(RequestOptions.circleCropTransform())
                        .into(this.photoPreviewAdd);
            }
        }

        if (requestCode == RC_TAKE_PHOTO1) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                   photoPreviewTake.setImageURI(photoURI);
                } else {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");

                    photoPreviewTake.setImageBitmap(imageBitmap);
                }

                galleryAddPic();
            } else {
                Toast.makeText(context, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        pictureUri = Uri.fromFile(f);
        mediaScanIntent.setData(pictureUri);
        context.sendBroadcast(mediaScanIntent);
    }
}
