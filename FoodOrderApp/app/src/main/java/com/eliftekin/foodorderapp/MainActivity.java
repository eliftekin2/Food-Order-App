package com.eliftekin.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //tam ekran yapar
        getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.maincolor)); //navigasyon çubuğunun rengini değiştirir

        setContentView(R.layout.activity_main);

        handler = new Handler();

        //3 saniye gecikme süresi olan runabble nesnesi planlar
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, LogInScreen.class); //aktiviteler arası geçiş
                startActivity(intent);
                finish();
            }
        },3000);
    }

}