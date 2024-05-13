package com.example.a71donemproje.Activitys;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.a71donemproje.databinding.ActivityKayitSilBinding;
import com.example.a71donemproje.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class KayitSil extends AppCompatActivity {
    ActivityKayitSilBinding binding;
    SQLiteDatabase veritabani;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> kullaniciListesi;

    UserModel seciliKullanici;

    ArrayList<UserModel> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKayitSilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        users = new ArrayList<>();
        veritabani = openOrCreateDatabase("kayitlar.db", MODE_PRIVATE, null);
        listView = binding.kullaniciGosterLv;
        kullaniciListesi = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kullaniciListesi);
        listView.setAdapter(adapter);

        loadUsers();


        listView.setOnItemClickListener((parent, view, position, id) -> {
            seciliKullanici = users.get(position);
            Toast.makeText(getApplicationContext(), String.format("Tiklandi: %d, %s", position, seciliKullanici.kullaniciAdi), Toast.LENGTH_SHORT).show();


        });

        binding.kayitSilBtn.setOnClickListener(v -> {
            if (seciliKullanici == null) {
                Toast.makeText(getApplicationContext(), "Lütfen bir kullanıcı seçiniz", Toast.LENGTH_SHORT).show();
                return;
            }
            veritabani.delete("kullanici", "ID=?", new String[]{String.valueOf(seciliKullanici.id)});
            seciliKullanici=null;
            Toast.makeText(getApplicationContext(), "Kayıt Silindi", Toast.LENGTH_SHORT).show();
            loadUsers();
        });

    };
    private void loadUsers() {
        Cursor cursor = veritabani.rawQuery("SELECT * FROM kullanici", null);
        users.clear();
        kullaniciListesi.clear();
        if (cursor != null && cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") String kullaniciAdi = cursor.getString(cursor.getColumnIndex("KULLANICI_ADI"));
                @SuppressLint("Range") String ad = cursor.getString(cursor.getColumnIndex("AD"));
                @SuppressLint("Range") String soyad = cursor.getString(cursor.getColumnIndex("SOYAD"));
                @SuppressLint("Range") String kullaniciSifre = cursor.getString(cursor.getColumnIndex("SIFRE"));
                @SuppressLint("Range") int kullaniciID = cursor.getInt(cursor.getColumnIndex("ID"));
                users.add(new UserModel(ad, soyad, kullaniciAdi, kullaniciSifre, kullaniciID));
                kullaniciListesi.add("Kullanıcı Adı: " + kullaniciAdi + "\nAd: " + ad + "\nSoyad: " + soyad);
                cursor.moveToNext();
            }
            adapter.notifyDataSetChanged();
            cursor.close();
        } else {
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Veri bulunamadı", Toast.LENGTH_SHORT).show();
        }
    }

}
