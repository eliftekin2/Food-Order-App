package com.eliftekin.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.eliftekin.foodorderapp.Adapters.ViewPagerAdapter;
import com.eliftekin.foodorderapp.databinding.ActivityMainScreenBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    private ActivityMainScreenBinding binding; //viewbinding nesnesi

    ViewPagerAdapter viewPagerAdapter; //viewpageradapter nesnesi
    private String[] tabTitles = new String[] {"Pizzas", "Sandwiches", "Burgers"}; //tab başlıkları

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Giriş yapılan epostaya kayıtlı olan ismi diğer aktivitiden alır ve textview'da gösterir.
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        binding.customerName.setText(name);

        viewPagerAdapter = new ViewPagerAdapter(this); //adapter nesnesi
        binding.viewpager.setAdapter(viewPagerAdapter); //viewpager ve adapter bağlanır

        new TabLayoutMediator(binding.tablayout, binding.viewpager,(tab, position) -> tab.setText(tabTitles[position])).attach(); //tablayout ve viewpager birbirine bağlanır ve tablerin başlıkları tanımlanır.

        binding.tablayout.selectTab(binding.tablayout.getTabAt(1)); //başlangıçta hangi sekmenin açılacağı belirlenir

        //alışveriş sepeti sayfasına götürür
        binding.shoppingBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainScreen.this, Cart.class);
                startActivity(intent);
            }
        });
    }

}