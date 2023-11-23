package com.eliftekin.foodorderapp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class ItemData {

    public int item_picture;
    public String item_title;
    public int item_price;
    public int item_amount;
    public Bitmap item_picture_bitmap;

    public ItemData(int item_picture, String item_title, int item_price) {
        this.item_picture = item_picture;
        this.item_title = item_title;
        this.item_price = item_price;
    }

    public ItemData(String item_title, int item_price, int item_amount, Bitmap item_picture) {
        this.item_title = item_title;
        this.item_price = item_price;
        this.item_amount = item_amount;
        this.item_picture_bitmap = item_picture;
    }
}
