package com.example.a71donemproje;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a71donemproje.Activitys.KayitEkrani;
import com.example.a71donemproje.Activitys.KullaniciProfilEkrani;
import com.example.a71donemproje.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("com.example.a71donemproje", MODE_PRIVATE);

        binding.kayitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), KayitEkrani.class);
            startActivity(intent);
        });


        binding.girisYapBtn.setOnClickListener(v -> {
            String savedUsername = sharedPreferences.getString("username", "");
            String savedPassword = sharedPreferences.getString("password", "");

            String enteredUsername = Objects.requireNonNull(binding.usernameTxt.getText()).toString().trim();
            String enteredPassword = Objects.requireNonNull(binding.passwordTxt.getText()).toString().trim();

            if (TextUtils.isEmpty(enteredUsername) || TextUtils.isEmpty(enteredPassword)) {
                Toast.makeText(MainActivity.this, "Kullanıcı adı ve şifre boş bırakılamaz", Toast.LENGTH_SHORT).show();
                return;
            }


            if (enteredUsername.equals(savedUsername) && enteredPassword.equals(savedPassword)) {

                Toast.makeText(MainActivity.this, "Giriş başarılıyla Gerçekleştirildi.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), KullaniciProfilEkrani.class);
                startActivity(intent);
            } else {

                Toast.makeText(MainActivity.this, "Kullanıcı adı veya şifre yanlış", Toast.LENGTH_SHORT).show();
            }
        });


    }
}

