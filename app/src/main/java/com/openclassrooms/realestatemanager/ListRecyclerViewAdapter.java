package com.openclassrooms.realestatemanager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.models.Address;
import com.openclassrooms.realestatemanager.models.Agent;
import com.openclassrooms.realestatemanager.models.Property;
import com.openclassrooms.realestatemanager.models.TypeOfProperty;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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

    private Context mContext;
    private OnItemClickedListener mListener;

    private List<Property> properties ;
    //private List<Address> addresses;
    private List<TypeOfProperty> types;
    private PropertyViewModel propertyViewModel;
    private  boolean useTablet;
    private int propertySelected;

    String townText;

    public ListRecyclerViewAdapter(int propertySelected, boolean useTablet) {
        this.properties = new ArrayList<>();
        //this.addresses = new ArrayList<>();
        this.types = new ArrayList<>();
        this.propertySelected = propertySelected;
        this.useTablet = useTablet;
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

        Property currentProperty = properties.get(position);
        //Address currentAddress = addresses.get(currentProperty.getAddressId());
        TypeOfProperty currentType = types.get(currentProperty.getTypeId());
       // currentProperty.getTypeId();

        holder.type.setText(String.valueOf(currentType.getTypeText()));
        holder.town.setText(String.valueOf(currentProperty.getTown()));
        holder.price.setText(String.valueOf(currentProperty.getPrice()));

        String uriPhoto = currentProperty.getMainPhoto();
        Picasso.get().load(uriPhoto).into(holder.picture);

        if(useTablet) {
            if (position == propertySelected - 1) {
                holder.itemBackground.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
                holder.type.setTextColor(mContext.getResources().getColor(R.color.colorMyWhite));
                holder.town.setTextColor(mContext.getResources().getColor(R.color.colorMyWhite));
            }
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: ListRecyclerViewAdapter " + properties.size());
        return properties.size();
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
        notifyDataSetChanged();
    }

/*    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
        notifyDataSetChanged();
    }*/

    public void setTypes(List<TypeOfProperty> types) {
        this.types = types;
        notifyDataSetChanged();
    }

    public interface OnItemClickedListener{
        void OnItemClicked(int position);
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

        public int idHome;

        public ListViewHolder(View itemView, final ListRecyclerViewAdapter.OnItemClickedListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION) {
                            listener.OnItemClicked(position);
                        }
                    }
                }
            });
        }

    }
}
