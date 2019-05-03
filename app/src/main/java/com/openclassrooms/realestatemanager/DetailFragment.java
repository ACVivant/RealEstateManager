package com.openclassrooms.realestatemanager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private static final String TAG = "DetailFragment";

    private ArrayList<String> photoUrlList = new ArrayList<>();
    private ArrayList<String> textList = new ArrayList<>();

    View v;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_detail, container, false);

        initDemoData();

        return v;
    }

    private void initDemoData() {
        photoUrlList.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        textList.add("Façade");

        photoUrlList.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        textList.add("Cuisine");

        photoUrlList.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        textList.add("Chambre 1");

        photoUrlList.add("https://i.redd.it/j6myfqglup501.jpg");
        textList.add("Chambre 2");

        photoUrlList.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        textList.add("Salon");

        photoUrlList.add("https://i.redd.it/k98uzl68eh501.jpg");
        textList.add("Salle de bain");

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = v.findViewById(R.id.photo_recyclerview_container);
        PhotoRecyclerViewAdapter adapter = new PhotoRecyclerViewAdapter(getContext(), photoUrlList, textList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
