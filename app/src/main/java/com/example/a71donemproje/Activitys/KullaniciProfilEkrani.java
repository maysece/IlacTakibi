package com.example.a71donemproje.Activitys;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.a71donemproje.MainActivity;
import com.example.a71donemproje.R;
import com.example.a71donemproje.recycleview.Secimler;
import com.example.a71donemproje.recycleview.SecimlerAdapter;
import com.example.a71donemproje.databinding.ActivityKullaniciProfilEkraniBinding;

import java.util.ArrayList;

public class KullaniciProfilEkrani extends AppCompatActivity {
    ActivityKullaniciProfilEkraniBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityKullaniciProfilEkraniBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main),
               (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
             v.setPadding(systemBars.left,systemBars.top,systemBars.right,systemBars.bottom);
             return insets;
               });
        binding.islemlerRv.setHasFixedSize(true);
        binding.islemlerRv.setLayoutManager(new LinearLayoutManager(this));

        SecimlerAdapter adapter = getAdapter();
        binding.islemlerRv.setAdapter(adapter);

        binding.islemTxt.setOnClickListener(v -> {
            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    @NonNull
    private SecimlerAdapter getAdapter() {
        Secimler Ekle = new Secimler("Ekle","asiekle");
        Secimler Sil = new Secimler("Sil","asisil");
        Secimler Goster = new Secimler("Göster","asigoster");
        Secimler Guncelle = new Secimler("Güncelle","asiguncelle");

        ArrayList<Secimler> secimlerArrayList = new ArrayList<>();
        secimlerArrayList.add(Ekle);
        secimlerArrayList.add(Sil);
        secimlerArrayList.add(Goster);
        secimlerArrayList.add(Guncelle);



        return new SecimlerAdapter(this, secimlerArrayList);
    }


}