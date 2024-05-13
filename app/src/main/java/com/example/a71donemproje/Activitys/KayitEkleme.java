package com.example.a71donemproje.Activitys;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a71donemproje.databinding.ActivityKayitEklemeBinding;

import java.util.Objects;

public class KayitEkleme extends AppCompatActivity {

    ActivityKayitEklemeBinding binding;
    SharedPreferences sharedPreferences;
    SQLiteDatabase veritabani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKayitEklemeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("com.example.a71donemproje", MODE_PRIVATE);
        veritabani = openOrCreateDatabase("kayitlar.db", Context.MODE_PRIVATE, null);
        veritabani.execSQL("CREATE TABLE IF NOT EXISTS kullanici (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " AD TEXT, SOYAD TEXT, KULLANICI_ADI TEXT, SIFRE TEXT)");

        binding.kullaniciEkleBtn.setOnClickListener(v -> {
            String usernameTxt = Objects.requireNonNull(binding.adTxt.getText()).toString();
            String soyadiTxt = Objects.requireNonNull(binding.soyadTxt.getText()).toString();
            String kullaniciAdiTxt = Objects.requireNonNull(binding.usernameTxt.getText()).toString();
            String sifreTxt = Objects.requireNonNull(binding.passwordTxt.getText()).toString();

            if (usernameTxt.trim().isEmpty() || soyadiTxt.trim().isEmpty() || kullaniciAdiTxt.trim().isEmpty()
                    || sifreTxt.trim().isEmpty()) {
                Toast.makeText(KayitEkleme.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put("AD", usernameTxt);
                contentValues.put("SOYAD", soyadiTxt);
                contentValues.put("KULLANICI_ADI", kullaniciAdiTxt);
                contentValues.put("SIFRE", sifreTxt);

                long result = veritabani.insert("kullanici", null, contentValues);

                if (result != -1) {
                    Toast.makeText(KayitEkleme.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), KayitEdilecekIlac.class);
                    intent.putExtra("userID", result);
                    startActivity(intent);
                } else {
                    Toast.makeText(KayitEkleme.this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                }

            }

        });
    }
}
