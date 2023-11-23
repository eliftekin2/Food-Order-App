package com.eliftekin.foodorderapp.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliftekin.foodorderapp.Adapters.RecyclerViewAdapter;
import com.eliftekin.foodorderapp.ItemData;
import com.eliftekin.foodorderapp.MainScreen;
import com.eliftekin.foodorderapp.R;

import java.util.ArrayList;

public class BurgerFragment extends Fragment {

    ArrayList<ItemData> item_dataArrayList_burger; //adapter'a gönderilecek olan arraylist
    private RecyclerView recyclerView; //rv nesnesi
    private RecyclerViewAdapter recyclerViewAdapter; //rv adapter nesnesi

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_burger, container, false);

        item_dataArrayList_burger = new ArrayList<>(); //arraylist'i başlatır

        //ItemData sınıfı nesneleri oluşturulup gerekli veriler aktarılır.
        ItemData burgerbeef = new ItemData(R.drawable.burgerbeef, "Beef Burger", 9);
        ItemData burgercheese = new ItemData(R.drawable.burgercheese, "Cheeseburger", 3);
        ItemData burgerchicken = new ItemData(R.drawable.burgerchicken, "Chicken Burger", 4);
        ItemData burgerkimchi = new ItemData(R.drawable.burgerkimchi, "Kimchi Burger", 4);
        ItemData burgersalmon = new ItemData(R.drawable.burgersalmon, "Salmon Burger", 5);
        ItemData burgerturkey = new ItemData(R.drawable.burgerturkey, "Turkey Burger", 6);

        //listeye ItemData sınıfı nesneleri eklenir
        item_dataArrayList_burger.add(burgerbeef);
        item_dataArrayList_burger.add(burgercheese);
        item_dataArrayList_burger.add(burgerchicken);
        item_dataArrayList_burger.add(burgerkimchi);
        item_dataArrayList_burger.add(burgersalmon);
        item_dataArrayList_burger.add(burgerturkey);

        recyclerView = viewGroup.findViewById(R.id.burger_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); //recyclerview'un hangi layouta sahip olacağı belirlenir.

        recyclerViewAdapter = new RecyclerViewAdapter(item_dataArrayList_burger, requireContext()); //rv adapter nesnesi başlatılır ve liste parametre olarak gönderilir.
        recyclerView.setAdapter(recyclerViewAdapter); //recyclerview adapter ile bağlanır

        return viewGroup;
    }
}