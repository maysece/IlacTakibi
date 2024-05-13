package com.example.a71donemproje.Activitys;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a71donemproje.databinding.ActivityKayitGuncelleBinding;
import com.example.a71donemproje.models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class GuncellemeEkrani extends AppCompatActivity {
    ActivityKayitGuncelleBinding binding;
    SQLiteDatabase veritabani;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> kullaniciListesi;

    UserModel seciliKullanici;

    ArrayList<UserModel> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKayitGuncelleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        users = new ArrayList<>();
        veritabani = openOrCreateDatabase("kayitlar.db", MODE_PRIVATE, null);
        listView = binding.kullaniciList;
        kullaniciListesi = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kullaniciListesi);
        listView.setAdapter(adapter);

        loadUsers();


        listView.setOnItemClickListener((parent, view, position, id) -> {
            seciliKullanici = users.get(position);
            binding.usernameTxt.setText(seciliKullanici.kullaniciAdi);
            binding.adTxt.setText(seciliKullanici.isim);
            binding.soyadTxt.setText(seciliKullanici.soyisim);
            binding.passwordTxt.setText(seciliKullanici.sifre);
            Toast.makeText(getApplicationContext(), String.format("Tiklandi: %d, %s", position, seciliKullanici.kullaniciAdi), Toast.LENGTH_SHORT).show();
        });

        binding.guncelleBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(seciliKullanici != null) {
                    ContentValues values = new ContentValues();
                    values.put("KULLANICI_ADI", binding.usernameTxt.getText().toString());
                    values.put("AD", binding.adTxt.getText().toString());
                    values.put("SOYAD", binding.soyadTxt.getText().toString());
                    values.put("SIFRE", binding.passwordTxt.getText().toString());
                    veritabani.update("kullanici", values, "ID=?", new String[]{String.valueOf(seciliKullanici.id)});
                loadUsers();
                Toast.makeText(getApplicationContext(), "Kullanıcı güncellendi", Toast.LENGTH_SHORT).show();
                seciliKullanici = null;
                binding.usernameTxt.setText("");
                binding.adTxt.setText("");
                binding.soyadTxt.setText("");
                binding.passwordTxt.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Lütfen bir kullanıcı seçiniz", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadUsers() {
        Cursor cursor = veritabani.rawQuery("SELECT * FROM kullanici", null);
        if (cursor != null && cursor.moveToFirst()) {
            users.clear();
            kullaniciListesi.clear();
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
            Toast.makeText(this, "Veri bulunamadı", Toast.LENGTH_SHORT).show();
        }
    }

}
