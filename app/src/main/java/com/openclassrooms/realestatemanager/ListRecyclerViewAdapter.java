package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anne-Charlotte Vivant on 02/05/2019.
 */
public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ListViewHolder> {
    private static final String TAG = "ListRecyclerViewAdapter";

    private ArrayList<String> picturesList = new ArrayList<>();
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayList<String> townList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private Context mContext;

    public ListRecyclerViewAdapter(Context context, ArrayList<String> picturesList, ArrayList<String> typeList, ArrayList<String> townList, ArrayList<String> priceList) {
        mContext = context;
        this.picturesList = picturesList;
        this.typeList = typeList;
        this.townList = townList;
        this.priceList = priceList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_house, parent, false);
        ListViewHolder holder = new ListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called.");

        Glide.with(mContext)
                .load(picturesList.get(position))
                .into(holder.picture);

        holder.type.setText(typeList.get(position));
        holder.town.setText(townList.get(position));
        holder.price.setText(priceList.get(position));

        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, townList.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.list_recyclerview_item_picture)
        ImageView picture;
        @BindView(R.id.list_recyclerview_item_type)
        TextView type;
        @BindView(R.id.list_recyclerview_item_town)
        TextView town;
        @BindView(R.id.list_recyclerview_item_devise)
        TextView devise;
        @BindView(R.id.list_recyclerview_item_price)
        TextView price;
        @BindView(R.id.list_recyclerview_item)
        LinearLayout itemContainer;

        public ListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
