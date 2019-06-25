package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Anne-Charlotte Vivant on 02/05/2019.
 */
public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ListViewHolder> {
    private static final String TAG = "ListRecyclerViewAdapter";

    private Context mContext;
    private OnItemClickedListener mListener;

    private List<Property> properties ;
    private List<TypeOfProperty> types;
    private  boolean useTablet;
    private int propertySelected;
    private int rowIndex;

    public ListRecyclerViewAdapter(int propertySelected, boolean useTablet) {
        this.properties = new ArrayList<>();
        this.types = new ArrayList<>();
        this.propertySelected = propertySelected;
        this.useTablet = useTablet;
        Log.d(TAG, "ListRecyclerViewAdapter: constructor");
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ListRecyclerViewAdapter");
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_house, parent, false);
        ListViewHolder holder = new ListViewHolder(itemView, mListener);
        mContext = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: ListRecyclerViewAdapter Called.");
        Log.d(TAG, "onBindViewHolder: position " + position);

        Property currentProperty = properties.get(position);
        TypeOfProperty currentType = types.get(currentProperty.getTypeId()-1);

        Log.d(TAG, "onBindViewHolder: currentProperty " + currentProperty.getPropertyId());
        Log.d(TAG, "onBindViewHolder: propertySelected " + propertySelected);

        holder.type.setText(String.valueOf(currentType.getTypeText()));
        holder.town.setText(String.valueOf(currentProperty.getTown()));
        holder.price.setText(String.valueOf(currentProperty.getPrice()));


        String uriPhoto = currentProperty.getMainPhoto();
        Glide.with(mContext)
                .load(uriPhoto)
                .into(holder.picture);

/*        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowIndex=position;
                //notifyDataSetChanged();
            }
        });*/

        for (int i=0; i<properties.size(); i++) {
            Log.d(TAG, "setRVBackgroudColor: properties.get(i).getPropertyId " + properties.get(i).getPropertyId());
            Log.d(TAG, "onBindViewHolder:setRVBackgroudColor  propertySelected " + propertySelected);
            if (properties.get(i).getPropertyId()== propertySelected) {
                rowIndex =i;
            }
        }
        Log.d(TAG, "onBindViewHolder setRVBackgroudColor: rowIndex " + rowIndex);

        if(rowIndex==position){
            holder.itemBackground.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
            holder.type.setTextColor(mContext.getResources().getColor(R.color.colorMyWhite));
            holder.town.setTextColor(mContext.getResources().getColor(R.color.colorMyWhite));
        }
        else
        {
            holder.itemBackground.setBackgroundColor(mContext.getResources().getColor(R.color.colorMyWhite));
            holder.type.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
            holder.town.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
        }

    }

    //public void setRVBackgroudColor(int position) {
    public void setRVBackgroudColor(int propertyId) {
        Log.d(TAG, "setRVBackgroudColor: propertyId " + propertyId);
        Log.d(TAG, "setRVBackgroudColor: properties.size " + properties.size());
/*        for (int i=0; i<properties.size(); i++) {
            Log.d(TAG, "setRVBackgroudColor: properties.get(i).getPropertyId " + properties.get(i).getPropertyId());
            if (properties.get(i).getPropertyId()== propertyId) {
                rowIndex =i;
            }
        }
        Log.d(TAG, "setBackgroundColor: je passe par ici " + rowIndex);*/
        propertySelected = propertyId;
        //rowIndex = position;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ListRecyclerViewAdapter " + properties.size());
        return properties.size();
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
        notifyDataSetChanged();
        Log.d(TAG, "setProperties");
        Log.d(TAG, "setProperties: properties.size " + properties.size());
    }

    public void setTypes(List<TypeOfProperty> types) {
        this.types = types;
        notifyDataSetChanged();
        Log.d(TAG, "setTypes");
    }

    public interface OnItemClickedListener{
        //void OnItemClicked(int position);
        void OnItemClicked(int propertyId, int position);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        mListener = listener;
    }

    //-------------------------------------------------------------------------------------------------------

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
        @BindView(R.id.list_recyclerview_background)
        LinearLayout itemBackground;

        RecyclerView rv;

        public ListViewHolder(View itemView, final ListRecyclerViewAdapter.OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            rv=(RecyclerView)itemView.findViewById(R.id.list_recyclerview_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        int position = getAdapterPosition();
                        int propertyId = properties.get(position).getPropertyId();
                        Log.d(TAG, "onClick: position " + position);
                        Log.d(TAG, "onClick: id " + propertyId);

                        if (position!= RecyclerView.NO_POSITION) {
                           // listener.OnItemClicked(position);
                            listener.OnItemClicked(propertyId, position);
                        }
                    }
                }
            });
        }

    }
}
