package com.eliftekin.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.eliftekin.foodorderapp.databinding.ActivityLogInScreenBinding;

public class LogInScreen extends AppCompatActivity {

    private ActivityLogInScreenBinding binding; //viewbinging nesnesi

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot(); //görünüme dönüştürür
        setContentView(view);

        SQLiteDatabase usersDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null); //veritabanını oluşturur

        usersDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name VARCHAR, email VARCHAR, password VARCHAR)"); //tabloyu oluşturur
        usersDatabase.execSQL("CREATE TABLE IF NOT EXISTS items (item_name VARCHAR, item_price INT, updated_item_price INT, item_amount INT, item_image BLOB)"); //sepete eklenen ürünler için tablo oluşturur

        binding.loginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        binding.loginGirisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    //eposta ve şifre doğruysa uygulamaya giriş yapan fonksiyon
    private void signIn() {
        String email, password;

        //girilen verileri değişkenlere aktarır
        email = binding.loginEmail.getText().toString();
        password = binding.loginPassword.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        }

        else {
            //veritabanını açar
            SQLiteDatabase usersDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

            //tablodan email ve şifre verilerini çeker
            Cursor cursor = usersDatabase.rawQuery("SELECT * FROM users WHERE email =? AND password = ?", new String[]{email, password});

            //eğer eposta ve şifre kombinasyonu doğruysa ana sayfa açılır
            if (cursor.getCount() > 0){
                Toast.makeText(this, "Logged in successfully", Toast.LENGTH_SHORT).show();

                cursor.moveToFirst();
                int nameIndex = cursor.getColumnIndex("name");
                String name = cursor.getString(nameIndex); //isim verisini alır

                Intent intent = new Intent(LogInScreen.this, MainScreen.class);
                intent.putExtra("name", name); //başlatılan sınıfa veriyi gönderir
                startActivity(intent);
            }

            else {
                Toast.makeText(this, "E-mail or password is incorrect", Toast.LENGTH_SHORT).show();
            }
        }

    }

    //kayıt olma sayfasını açan fonksiyon
    public void register(){
        Intent intent = new Intent(LogInScreen.this, RegisterScreen.class);
        startActivity(intent);
    }
}