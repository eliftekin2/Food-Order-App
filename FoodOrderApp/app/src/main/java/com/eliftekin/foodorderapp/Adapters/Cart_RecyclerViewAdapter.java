package com.eliftekin.foodorderapp.Adapters;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eliftekin.foodorderapp.Cart;
import com.eliftekin.foodorderapp.ItemData;
import com.eliftekin.foodorderapp.databinding.ItemsInCartBinding;

import java.util.ArrayList;

public class Cart_RecyclerViewAdapter extends RecyclerView.Adapter<Cart_RecyclerViewAdapter.MyViewHolder>{

    ArrayList<ItemData> itemDataArrayList;
    Context context;
    TextView orderTotal;

    public Cart_RecyclerViewAdapter(ArrayList<ItemData> itemDataArrayList, Context context, TextView orderTotal) {
        this.itemDataArrayList = itemDataArrayList;
        this.context = context;
        this.orderTotal = orderTotal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsInCartBinding binding = ItemsInCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyViewHolder holder = new MyViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.cartItemTitle.setText(itemDataArrayList.get(position).item_title);
        holder.binding.cartItemPrice.setText(itemDataArrayList.get(position).item_price +"");
        holder.binding.cartItemAmount.setText(itemDataArrayList.get(position).item_amount +"");
        holder.binding.cartItemImage.setImageBitmap(itemDataArrayList.get(position).item_picture_bitmap);

        int price = Integer.parseInt(holder.binding.cartItemPrice.getText().toString());

        //eksi butonuna basıldığında çalışacak metodu çağırır
        holder.binding.cartItemMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               decreaseAndUpdatePrice(holder, position);
            }
        });

        //artı butonuna basıldığında çalışacak metodu çağırır
        holder.binding.cartItemPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increaseAndUpdatePrice(holder);
            }
        });
    }

    private void increaseAndUpdatePrice(MyViewHolder holder) {
        String itemName = holder.binding.cartItemTitle.getText().toString();

        SQLiteDatabase database = context.openOrCreateDatabase("Users", Context.MODE_PRIVATE, null); //veritabanını açar

        int amount=1;
        int price=0;

        Cursor cursor = database.rawQuery("SELECT item_price, item_amount FROM items WHERE item_name='"+itemName+"'", null); //seçilen ürünün adedini ve tutarını alır

        int priceIndex = cursor.getColumnIndex("item_price");
        int amountIndex = cursor.getColumnIndex("item_amount");

        while (cursor.moveToNext()){
            amount = cursor.getInt(amountIndex);
            price = cursor.getInt(priceIndex);
        }

        amount++; //adedi bir arttırır
        price*=amount; //adetle tutarı çarpar

        //yeni verileri yerlerine yazdırır
        holder.binding.cartItemPrice.setText(price+"");
        holder.binding.cartItemAmount.setText(amount+"");

        //veritabanını günceller
        database.execSQL("UPDATE items SET item_amount= "+amount+", updated_item_price= "+price+" WHERE item_name= '"+itemName+"' "); //tabloyu günceller

        //toplam tutarı güncelleyen metodu çağırır
        updateTotalPrice();
    }

    private void decreaseAndUpdatePrice(MyViewHolder holder, int position) {
        SQLiteDatabase database = context.openOrCreateDatabase("Users", Context.MODE_PRIVATE, null); //veritabanını açar

        String item_name = holder.binding.cartItemTitle.getText().toString();

        //eğer ürün adedi 1'e eşitse
        if(holder.binding.cartItemAmount.getText().equals("1")){

            database.execSQL("DELETE FROM items WHERE item_name= '"+ item_name +"'"); //tablodan o ürünü siler

            //recyclerview'u günceller
            itemDataArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }

        //eğer ürün adedi 1'den fazlaysa
        else {
            int amount=1;
            int price=0;

            Cursor cursor = database.rawQuery("SELECT item_price, item_amount FROM items WHERE item_name= '"+item_name+"'", null);

            int priceIndex = cursor.getColumnIndex("item_price");
            int amountIndex = cursor.getColumnIndex("item_amount");

            while (cursor.moveToNext()){
                amount = cursor.getInt(amountIndex);
                price = cursor.getInt(priceIndex);
            }

            amount--; //ürün adedini bir azaltır
            price*=amount; //ürünün toplam fiyatını günceller

            holder.binding.cartItemAmount.setText(amount+"");
            holder.binding.cartItemPrice.setText(price+"");

            //veritabanını günceller
            database.execSQL("UPDATE items SET item_amount="+amount+", updated_item_price= "+price+" WHERE item_name= '"+holder.binding.cartItemTitle.getText().toString()+"'"); //tabloyu günceller
        }

        //toplam tutarı güncelleyen metodu çağırır
        updateTotalPrice();
    }

    public void clearItems() {
        //recyclerview içindeki tüm itemleri siler
        itemDataArrayList.clear();
        notifyDataSetChanged();
    }

    private void updateTotalPrice(){
        SQLiteDatabase database = context.openOrCreateDatabase("Users", Context.MODE_PRIVATE,null);
        Cursor cursor = database.rawQuery("SELECT updated_item_price FROM items", null); //veritabanındaki tüm güncellenmiş tutarları alır

        int total=0;

        int priceIndex = cursor.getColumnIndex("updated_item_price");

        while (cursor.moveToNext()){
            int price = cursor.getInt(priceIndex);
            total+=price;
        }

        orderTotal.setText("$"+total); //yeni toplam tutarı yazdırır
    }

    @Override
    public int getItemCount() {
        return itemDataArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private ItemsInCartBinding binding;

        public MyViewHolder(ItemsInCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
