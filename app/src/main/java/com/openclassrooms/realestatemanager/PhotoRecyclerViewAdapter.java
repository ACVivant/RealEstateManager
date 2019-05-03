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

import java.util.ArrayList;

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
    private ArrayList<String> photoList = new ArrayList<>();
    private ArrayList<String> textList = new ArrayList<>();
    private Context mContext;

    public PhotoRecyclerViewAdapter(Context context, ArrayList<String> photoList, ArrayList<String> textList ) {
        mContext = context;
        this.photoList = photoList;
        this.textList = textList;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_picture, parent, false);
        PhotoViewHolder holder = new PhotoViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        Glide.with(mContext)
                .load(photoList.get(position))
                .into(holder.picture);

        holder.type.setText(textList.get(position));

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, textList.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

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
