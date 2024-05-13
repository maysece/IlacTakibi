package com.example.a71donemproje.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a71donemproje.MainActivity;
import com.example.a71donemproje.databinding.ActivityKayitEkraniBinding;

import java.util.Objects;

public class KayitEkrani extends AppCompatActivity {
    ActivityKayitEkraniBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKayitEkraniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("com.example.a71donemproje",MODE_PRIVATE);

        binding.kaydetBtn.setOnClickListener(v -> {
            String ad = Objects.requireNonNull(binding.adTxt.getText()).toString().trim();
            String soyad = Objects.requireNonNull(binding.soyadTxt.getText()).toString().trim();
            String username = Objects.requireNonNull(binding.usernameTxt.getText()).toString().trim();
            String password = Objects.requireNonNull(binding.passwordTxt.getText()).toString().trim();
            String telefon = Objects.requireNonNull(binding.telefonEdt.getText()).toString().trim();
            String mail = Objects.requireNonNull(binding.mailTxt.getText()).toString().trim();

            if (TextUtils.isEmpty(ad) || TextUtils.isEmpty(soyad) || TextUtils.isEmpty(username) ||
                    TextUtils.isEmpty(password) || TextUtils.isEmpty(telefon) || TextUtils.isEmpty(mail)) {
                Toast.makeText(KayitEkrani.this, "Alanlar Boş Bırakılamaz!", Toast.LENGTH_SHORT).show();
            } else {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ad", ad).apply();
                editor.putString("soyad", soyad).apply();
                editor.putString("username", username).apply();
                editor.putString("password", password).apply();
                editor.putString("telefon", telefon).apply();
                editor.putString("mail", mail).apply();

                Toast.makeText(KayitEkrani.this, "Kayıt Tamamlandı.Giriş Ekranına Yönlendiriliyorsunuz...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        }
    }
