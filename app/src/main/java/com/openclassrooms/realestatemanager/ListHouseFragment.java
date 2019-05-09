package com.openclassrooms.realestatemanager;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListHouseFragment extends Fragment {

    private static final String TAG = "ListHouseFragment";
    private static final String PLACE_ID = "id_of_place";
    private static final String ID_FRAGMENT = "fragment_to_expose";

    private ArrayList<String> picturesUrlList = new ArrayList<>();
    private ArrayList<String> typeList = new ArrayList<>();
    private ArrayList<String> townList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();



    View v;
    public ListHouseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v= inflater.inflate(R.layout.fragment_list_house, container, false);

       initDemoData();

        return v;
    }

    private void initDemoData() {
        picturesUrlList.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        typeList.add("Appartement");
        townList.add("Crépy-en-Valois");
        priceList.add("542365");
        idList.add(100);

        picturesUrlList.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        typeList.add("Appartement");
        townList.add("Betz");
        priceList.add("123455");
        idList.add(101);

        picturesUrlList.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        typeList.add("Maison");
        townList.add("Crépy-en-Valois");
        priceList.add("75235");
        idList.add(102);

        picturesUrlList.add("https://i.redd.it/j6myfqglup501.jpg");
        typeList.add("Château");
        townList.add("Crépy-en-Valois");
        priceList.add("45445");
        idList.add(103);

        picturesUrlList.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        typeList.add("Manoir");
        townList.add("Compiègne");
        priceList.add("1001");
        idList.add(104);

        picturesUrlList.add("https://i.redd.it/k98uzl68eh501.jpg");
        typeList.add("Maison");
        townList.add("Senlis");
        priceList.add("789425");
        idList.add(105);

        picturesUrlList.add("https://i.redd.it/glin0nwndo501.jpg");
        typeList.add("Maison");
        townList.add("Compiègne");
        priceList.add("542365456");
        idList.add(106);

        picturesUrlList.add("https://i.redd.it/obx4zydshg601.jpg");
        typeList.add("Manoir");
        townList.add("Vauciennes");
        priceList.add("542365456498");
        idList.add(107);

        picturesUrlList.add("https://i.imgur.com/ZcLLrkY.jpg");
        typeList.add("Appartement");
        townList.add("Crépy-en-Valois");
        priceList.add("5423");
        idList.add(108);

        initRecyclerView();
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView");
        RecyclerView recyclerView = v.findViewById(R.id.list_recyclerview_container);
        ListRecyclerViewAdapter adapter = new ListRecyclerViewAdapter(getContext(), idList, picturesUrlList, typeList, townList, priceList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Launch DetailActiviy when user clicks on home item
        adapter.setOnItemClickedListener(new ListRecyclerViewAdapter.OnItemClickedListener() {
            @Override
            public void OnItemClicked(int position) {
                Intent WVIntent = new Intent(getContext(), MainActivity.class);
                WVIntent.putExtra(PLACE_ID, idList.get(position));
                WVIntent.putExtra(ID_FRAGMENT, "2");
                startActivity(WVIntent);
            }
        });
    }

}
