package com.openclassrooms.realestatemanager;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.models.Photo;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anne-Charlotte Vivant on 03/05/2019.
 */
public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder> {

    private static final String TAG = "PhotoRecyclerViewAdapte";
    public static final String PHOTO_URI = "photoUri";
    public static final String PHOTO_ID = "photoId";

    //Variables
    private Context mContext;
    private List<Photo> photos;
    private String which;
    private DeletePhotoListener listener;

    private View view;


public PhotoRecyclerViewAdapter(Context context, String which) {
    mContext = context;
    this.photos = new ArrayList<>();
    this.which = which;

}

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_picture, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(view);
        mContext = parent.getContext();
        try {
            listener = (DeletePhotoListener) mContext;
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString() + "must implement DeletePhotoListener");
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        Photo currentPhoto = photos.get(position);

        Glide.with(mContext)
                .load(currentPhoto.getPhotoUri())
                .into(holder.picture);

        holder.type.setText(currentPhoto.getPhotoText());

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (which.equals(DetailFragment.FROM_DETAIL_REQUEST)) {
                    openBigPhoto(Uri.parse(currentPhoto.getPhotoUri()), currentPhoto.getPhotoText());
                } else {
                    if (which.equals(UpdateActivity.FROM_UPDATE_REQUEST)) {
                        openDeleteDialog(holder, currentPhoto, currentPhoto.getPhotoUri(), currentPhoto.getPhotoId());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
    return photos.size();
        //return photoList.size();
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    private void openDeleteDialog(PhotoViewHolder holder, Photo currentPhoto, String Uri, long photoId) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Warning");
        alertDialog.setMessage("Vous allez supprimer cette photo");
        alertDialog.setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                holder.picture.setImageResource(R.drawable.ic_delete);
                holder.picture.setBackgroundColor(mContext.getResources().getColor(R.color.colorAccent));

                listener.photoToDelete(photoId);

            }
        });
        alertDialog.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alertDialog.create();
        dialog.show();
    }

    public void openBigPhoto(Uri photoUri, String legend) {
        View alertLayout =LayoutInflater.from(mContext).inflate(R.layout.display_photo_dialog, null);
        final ImageView photoView = alertLayout.findViewById(R.id.bigPhotoImg);

        Glide.with(mContext)
                .load(photoUri)
                .into(photoView);

        final TextView photoLegend = alertLayout.findViewById(R.id.bigPhotoLegend);
        photoLegend.setText(legend);


        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = alert.create();
        dialog.show();

    }

    public interface DeletePhotoListener{
        void photoToDelete(long photoId);
    }

    //---------------------------------------------------------------------------------------------------

    public class PhotoViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.photo_recyclerview_picture)
        ImageView picture;
        @BindView(R.id.photo_recyclerview_text)
        TextView type;
        @BindView(R.id.photo_recyclerview_item)
        RelativeLayout itemContainer;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }




}
