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

public class PizzaFragment extends Fragment {

    ArrayList <ItemData> itemDataArrayList;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_pizza, container, false);

        itemDataArrayList = new ArrayList<>();

        ItemData pizzacheese = new ItemData(R.drawable.pizzacheese, "Classic Cheese Pizza", 15);
        ItemData pizzahawaiian = new ItemData(R.drawable.pizzahawaiian, "Hawaiian Pizza", 22);
        ItemData pizzamargherita = new ItemData(R.drawable.pizzamargherita, "Margherita Pizza", 20);
        ItemData pizzameat = new ItemData(R.drawable.pizzameat, "Meat Pizza", 25);
        ItemData pizzapepperoni = new ItemData(R.drawable.pizzapepperoni, "Pepperoni Pizza", 16);
        ItemData pizzasupreme = new ItemData(R.drawable.pizzasupereme, "Supreme Pizza", 23);

        itemDataArrayList.add(pizzacheese);
        itemDataArrayList.add(pizzahawaiian);
        itemDataArrayList.add(pizzamargherita);
        itemDataArrayList.add(pizzameat);
        itemDataArrayList.add(pizzapepperoni);
        itemDataArrayList.add(pizzasupreme);

        recyclerView = viewGroup.findViewById(R.id.pizza_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        recyclerViewAdapter = new RecyclerViewAdapter(itemDataArrayList, requireContext());
        recyclerView.setAdapter(recyclerViewAdapter);

        return viewGroup;
    }
}