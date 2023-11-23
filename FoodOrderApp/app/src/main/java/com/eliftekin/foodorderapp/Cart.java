package com.eliftekin.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eliftekin.foodorderapp.Adapters.Cart_RecyclerViewAdapter;
import com.eliftekin.foodorderapp.Adapters.RecyclerViewAdapter;
import com.eliftekin.foodorderapp.databinding.ActivityCartBinding;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private ActivityCartBinding binding;

    ArrayList<ItemData> dataArrayList;
    RecyclerView recyclerView;
    Cart_RecyclerViewAdapter recyclerViewAdapter;

    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TextView orderTotal = findViewById(R.id.order_total);

        //veritabanını açar ve itemler tablosu yoksa oluşturur
        database = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);
        database.execSQL("CREATE TABLE IF NOT EXISTS items (item_name VARCHAR, item_price INT, updated_item_price INT, item_amount INT, item_image BLOB)");

        //toplam tutarı güncelleyen metodu çağırır.
        updatetotalprice();

        dataArrayList = new ArrayList<>(); //arraylist başlatılır

        getAddedItems(); //sepete eklenen ürünleri veritabanından alan metodu çağırır

        recyclerView = binding.orderRv;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //recyclerview'un layoutu ayarlanır.

        recyclerViewAdapter = new Cart_RecyclerViewAdapter(dataArrayList, this, orderTotal); //rv adapter başlatılır.
        recyclerView.setAdapter(recyclerViewAdapter); //adapter ve rv bağlanır.

        //sipariş ver butonuna basıldığında
        binding.orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //eğer tutar sıfırsa uyarı mesajı verir
                if(orderTotal.getText().toString().equals("$0"))
                {
                    Toast.makeText(Cart.this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                }

                //eğer tutar sıfırdan faklıysa dialog penceresini açan metodu çağırır
                else {
                    orderNow();
                }
            }
        });

    }

    private void orderNow() {
        Dialog dialog= new Dialog(this); //dialog nesnesini oluşturur
        dialog.setContentView(R.layout.order_success); //layoutu tanımlar
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); //boyutlarını belirler
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //arkaplanı saydam yapar
        dialog.getWindow().setGravity(Gravity.CENTER); //pencereyi ortada gösterir
        dialog.show(); //pencereyi görüntüler

        database.execSQL("CREATE TABLE IF NOT EXISTS items (item_name VARCHAR, item_price INT, updated_item_price INT, item_amount INT, item_image BLOB)");
        database.execSQL("DROP TABLE items"); //tablodaki verileri siler

        recyclerViewAdapter.clearItems(); //recyclerview'u güncelleyen metodu çağırır
        binding.orderTotal.setText("$0"); //toplam tutarı günceller

        //pencere kapatıldığında bir önceki aktiviteye geri döner
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                onBackPressed();
            }
        });
    }

    public void updatetotalprice() {
        Cursor cursor = database.rawQuery("SELECT updated_item_price FROM items", null); //tablodaki güncellenmiş tutarları alır

        int total=0;

        int priceIndex = cursor.getColumnIndex("updated_item_price");

        while (cursor.moveToNext()){
            int price = cursor.getInt(priceIndex);
            total+=price; //toplamı günceller

        }
        binding.orderTotal.setText("$"+total); //son toplamı textview'a yazdırır
    }

    private void getAddedItems() {
        Cursor cursor = database.rawQuery("SELECT * FROM items", null);

        int titleIndex = cursor.getColumnIndex("item_name");
        int priceIndex = cursor.getColumnIndex("updated_item_price");
        int amountIndex = cursor.getColumnIndex("item_amount");
        int imageIndex = cursor.getColumnIndex("item_image");

        while (cursor.moveToNext()) {
            //verileri tablodan alıp değişkenlere atar
            String item_title = cursor.getString(titleIndex);
            int item_price = cursor.getInt(priceIndex);
            int item_amount = cursor.getInt(amountIndex);
            byte[] item_image = cursor.getBlob(imageIndex);

            //byte dizisi türündeki resmi bitmap nesnesine dönüştürür
            Bitmap item_image_bitmap = BitmapFactory.decodeByteArray(item_image, 0, item_image.length);

            //alınan verileri data sınıfına aktarır
            ItemData data = new ItemData(item_title, item_price, item_amount, item_image_bitmap); //verileri model sınıfına atar
            dataArrayList.add(data);
        }
        cursor.close();
    }
}