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

    private ArrayList<String> picturesList = new ArrayList<>();
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayList<String> townList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();

    private Context mContext;
    private OnItemClickedListener mListener;

    private List<Property> properties = new ArrayList<>();
    private List<Agent> agents = new ArrayList<>();
    private PropertyViewModel propertyViewModel;

    private int propertySelected;

    public ListRecyclerViewAdapter(int propertySelected) {
        this.propertySelected = propertySelected;
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

        holder.type.setText(currentProperty.getType().getTypeText());
        holder.town.setText(currentProperty.getAddress().getTown());
        holder.price.setText(String.valueOf(currentProperty.getPrice()));

        String uriPhoto = currentProperty.getMainPhoto();
        Picasso.get().load(uriPhoto).into(holder.picture);

        if (position == propertySelected-1) {
           holder.itemBackground.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryLight));
           holder.type.setTextColor(mContext.getResources().getColor(R.color.colorMyWhite));
           holder.town.setTextColor(mContext.getResources().getColor(R.color.colorMyWhite));
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

    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }

    public interface OnItemClickedListener{
        void OnItemClicked(int position);
    }

    public void setOnItemClickedListener(OnItemClickedListener listener) {
        mListener = listener;
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
