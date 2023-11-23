package com.eliftekin.foodorderapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliftekin.foodorderapp.Adapters.RecyclerViewAdapter;
import com.eliftekin.foodorderapp.ItemData;
import com.eliftekin.foodorderapp.R;

import java.util.ArrayList;

public class SandwichFragment extends Fragment {

    ArrayList<ItemData> itemDataArrayList;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_sandwich, container, false);

        itemDataArrayList = new ArrayList<>();

        ItemData sandwichchicken = new ItemData(R.drawable.sandwichchicken, "Chicken Sandwich", 4);
        ItemData sandwichclub = new ItemData(R.drawable.sandwichclub, "Club Sandwich", 4);
        ItemData sandwichgrilledcheese = new ItemData(R.drawable.sandwichgrilledcheese, "Grilled Cheese Sandwich", 6);
        ItemData sandwichopen = new ItemData(R.drawable.sandwichopen, "Open Sandwich", 5);
        ItemData sandwichpanini = new ItemData(R.drawable.sandwichpanini, "Panini Sandwich", 5);
        ItemData sandwichtunafish = new ItemData(R.drawable.sandwichtunafish, "Tuna Fish Sandwich", 7);

        itemDataArrayList.add(sandwichchicken);
        itemDataArrayList.add(sandwichclub);
        itemDataArrayList.add(sandwichgrilledcheese);
        itemDataArrayList.add(sandwichopen);
        itemDataArrayList.add(sandwichpanini);
        itemDataArrayList.add(sandwichtunafish);

        recyclerView = viewGroup.findViewById(R.id.sandwich_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(),2));

        recyclerViewAdapter = new RecyclerViewAdapter(itemDataArrayList, requireContext());
        recyclerView.setAdapter(recyclerViewAdapter);

        return viewGroup;
    }
}