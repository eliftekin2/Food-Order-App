package com.eliftekin.foodorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.eliftekin.foodorderapp.databinding.ActivityRegisterScreenBinding;

public class RegisterScreen extends AppCompatActivity {

    private ActivityRegisterScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.registerKaydol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_newuser();
            }
        });
    }

    //yeni kullanıcı verilerini veritabanına ekleyen foksiyon
    private void register_newuser() {
        String name, email, password;

        //alanlar boşsa uyarı mesajı çıkarır
        if(binding.registerName.getText().toString().isEmpty() || binding.registerEmail.getText().toString().isEmpty() || binding.registerSifre.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
        else{
            //alınan verileri değişkenlere atar
            name = binding.registerName.getText().toString();
            email = binding.registerEmail.getText().toString();
            password = binding.registerSifre.getText().toString();

            try {
                //veritabanını açar
                SQLiteDatabase usersDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

                Cursor cursor = usersDatabase.rawQuery("SELECT * FROM users WHERE email = ?", new String[]{email});

                //aynı epostayla daha önce kayıt olunmuş mu diye kontrol eder
                if(cursor.getCount() > 0)
                {
                    Toast.makeText(this, "This email is already exists.", Toast.LENGTH_SHORT).show();
                }

                else {
                    usersDatabase.execSQL("INSERT INTO users (name, email, password) VALUES ( '" +name + "','" + email + "','" + password +"')"); //değişkenleri tablodaki verilere aktarır.

                    Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterScreen.this, LogInScreen.class);
                    startActivity(intent);

                }
                /*
                //kaydedilen verileri gösterme
                Cursor cursor1 = usersDatabase.rawQuery("SELECT * FROM users", null);

                int nameIndex = cursor1.getColumnIndex("name");
                int emailIndex = cursor1.getColumnIndex("email");
                int passwordIndex = cursor1.getColumnIndex("password");

                while (cursor1.moveToNext()) {
                    System.out.println("isim: " + cursor1.getString(nameIndex));
                    System.out.println("mail: " + cursor1.getString(emailIndex));
                    System.out.println("şifre: " + cursor1.getString(passwordIndex));
                }*/
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}