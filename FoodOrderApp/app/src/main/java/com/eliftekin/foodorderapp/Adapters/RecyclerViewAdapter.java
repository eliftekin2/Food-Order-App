package com.eliftekin.foodorderapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eliftekin.foodorderapp.Cart;
import com.eliftekin.foodorderapp.ItemData;
import com.eliftekin.foodorderapp.MainScreen;
import com.eliftekin.foodorderapp.R;
import com.eliftekin.foodorderapp.databinding.CardsBinding;
import com.eliftekin.foodorderapp.fragments.BurgerFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    ArrayList<ItemData> itemDataArrayList;
    Context context;

    SQLiteDatabase addedItemsDatabase;

    public ArrayList<ItemData> addedItemArrayList; //sepete eklenen ürünler için arraylist

    public RecyclerViewAdapter(ArrayList<ItemData> itemDataArrayList, Context context) {
        this.itemDataArrayList = itemDataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardsBinding binding = CardsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        MyViewHolder holder = new MyViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //arraylist içindeki verileri layouttaki yerlerine yerleştirir
        holder.binding.itemPicture.setImageResource(itemDataArrayList.get(position).item_picture);
        holder.binding.itemName.setText(itemDataArrayList.get(position).item_title);
        holder.binding.itemPrice.setText(itemDataArrayList.get(position).item_price + "");

        //sepete ürün eklendiğinde çalışacak metod
        holder.binding.itemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItems(holder);
            }
        });

    }

    //sepete eklenen ürünleri tabloya kaydeden metod
    private void addItems(MyViewHolder holder) {
        //ürünün ismini ve tutarını değişkene atar
        String item_name = holder.binding.itemName.getText().toString();
        int item_price = Integer.parseInt(holder.binding.itemPrice.getText().toString());

        Drawable item_image_drawable = holder.binding.itemPicture.getDrawable(); //ürünün resmini değişkene atar
        Bitmap bitmap_image = ((BitmapDrawable) item_image_drawable).getBitmap(); //resmi bitmap türüne dönüştürür
        Bitmap item_image_bitmap = resizeImage(bitmap_image, 300); //ürünün boyutunu küçültür.

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        item_image_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        byte[] byte_item_picture = outputStream.toByteArray(); //resmi byte dizisine dönüştürür

        int itemAmount=1;
        int itemPrice=0;

        try {
            addedItemsDatabase = context.openOrCreateDatabase("Users", Context.MODE_PRIVATE, null); //veritabanını açar

            Cursor cursor = addedItemsDatabase.rawQuery("SELECT * FROM items WHERE item_name=?", new String[]{item_name}); //sepette aynı üründen var mı diye kontrol eder
            //eğer aynı üründen sepette varsa
            if(cursor.getCount()>0){
                int itemAmountIndex = cursor.getColumnIndex("item_amount");
                int itemPriceIndex = cursor.getColumnIndex("item_price");

                while (cursor.moveToNext()){
                    itemAmount = cursor.getInt(itemAmountIndex);//ürünün adedini alır
                    itemPrice = cursor.getInt(itemPriceIndex); //ürünün fiyatını alır
                }

                itemAmount++;//bir arttırır
                itemPrice*=itemAmount;

                addedItemsDatabase.execSQL("UPDATE items SET item_amount="+itemAmount+", updated_item_price= "+itemPrice+" WHERE item_name= '"+item_name+"'"); //tabloyu günceller
            }

            //eğer aynı üründen yoksa
            else {
                //verileri tabloya aktarır
                String sqlString = "INSERT INTO items (item_name, item_price, updated_item_price, item_amount, item_image) VALUES(?,?,?,?,?)";
                SQLiteStatement sqLiteStatement = addedItemsDatabase.compileStatement(sqlString);
                sqLiteStatement.bindString(1, item_name);
                sqLiteStatement.bindDouble(2, item_price);
                sqLiteStatement.bindDouble(3, item_price);
                sqLiteStatement.bindDouble(4, 1);
                sqLiteStatement.bindBlob(5, byte_item_picture);
                sqLiteStatement.execute();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Bitmap resizeImage (Bitmap image, int maximumSize){
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width/ (float) height;

        if(bitmapRatio > 1){
            width = maximumSize;
            height = (int) (width/bitmapRatio);
        }
        else {
            height = maximumSize;
            width= (int) (height*bitmapRatio);
        }
        return image.createScaledBitmap(image,width, height, true );
    }

    @Override
    //xml'in kaç defa oluşturulacağını belirlediğimiz metot
    public int getItemCount() {
        return itemDataArrayList.size();
    }

    //görünümleri tutan yardımcı sınıf
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private CardsBinding binding;

        public MyViewHolder(CardsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
