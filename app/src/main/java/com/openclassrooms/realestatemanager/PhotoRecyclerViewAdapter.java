package com.openclassrooms.realestatemanager;

import android.content.Context;
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

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anne-Charlotte Vivant on 03/05/2019.
 */
public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder> {

    private static final String TAG = "PhotoRecyclerViewAdapte";

    //Variables
    private Context mContext;
    private List<Photo> photos;


public PhotoRecyclerViewAdapter(Context context) {
    mContext = context;
    this.photos = new ArrayList<>();

}

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_picture, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(view);
        mContext = parent.getContext();
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
                Toast.makeText(mContext, currentPhoto.getPhotoText(), Toast.LENGTH_LONG).show();
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
