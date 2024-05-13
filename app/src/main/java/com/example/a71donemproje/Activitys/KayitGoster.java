package com.example.a71donemproje.Activitys;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.a71donemproje.databinding.ActivityKayitGosterBinding;
import java.util.ArrayList;
import java.util.List;

public class KayitGoster extends AppCompatActivity {
    ActivityKayitGosterBinding binding;
    SQLiteDatabase veritabani;
    ListView listView, ilacListView;
    ArrayAdapter<String> adapter, ilacAdapter;
    List<String> kullaniciListesi, ilacListesi;

    int seciliKullanici = -1;

    ArrayList<Integer> userIDS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKayitGosterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        veritabani = openOrCreateDatabase("kayitlar.db", MODE_PRIVATE, null);
        listView = binding.kullanicilisteLV;
        ilacListView = binding.ilacListView;
        listView.setVisibility(View.GONE);
        ilacListView.setVisibility(View.GONE);
        kullaniciListesi = new ArrayList<>();
        ilacListesi = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kullaniciListesi);
        ilacAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ilacListesi);
        listView.setAdapter(adapter);
        ilacListView.setAdapter(ilacAdapter);

        // Kullanıcıları sorgula ve listeye ekle
        Cursor cursor = veritabani.rawQuery("SELECT * FROM kullanici", null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") String kullaniciAdi = cursor.getString(cursor.getColumnIndex("KULLANICI_ADI"));
                @SuppressLint("Range") String ad = cursor.getString(cursor.getColumnIndex("AD"));
                @SuppressLint("Range") String soyad = cursor.getString(cursor.getColumnIndex("SOYAD"));
                @SuppressLint("Range") int kullaniciID = cursor.getInt(cursor.getColumnIndex("ID"));
                userIDS.add(kullaniciID);
                kullaniciListesi.add("Kullanıcı Adı: " + kullaniciAdi + "\nAd: " + ad + "\nSoyad: " + soyad);
                cursor.moveToNext();
            }
            adapter.notifyDataSetChanged();
            cursor.close();
        } else {
            Toast.makeText(this, "Veri bulunamadı", Toast.LENGTH_SHORT).show();
        }

        binding.listeleBtn.setOnClickListener(v -> {
            listView.setVisibility(View.VISIBLE);

        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            seciliKullanici = userIDS.get(position);
            Toast.makeText(getApplicationContext(), String.format("Tiklandi: %d, %d", position, seciliKullanici), Toast.LENGTH_SHORT).show();
        });

        binding.ilacListeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seciliKullanici == -1) {
                    Toast.makeText(KayitGoster.this, "Lütfen bir kullanıcı seçin", Toast.LENGTH_SHORT).show();
                }
                else {
                    Cursor cursor = veritabani.rawQuery("SELECT * FROM ilaclar WHERE KULLANICI_ID = ?", new String[]{String.valueOf(seciliKullanici)});

                    if (cursor != null && cursor.moveToFirst()) {
                        ilacListesi.clear();
                        while (!cursor.isAfterLast()) {
                            @SuppressLint("Range") String ilacAdi = cursor.getString(cursor.getColumnIndex("ILAC_ADI"));
                            @SuppressLint("Range") String ilacMiktar = cursor.getString(cursor.getColumnIndex("GUNLUK_ALINACAK_MIKTAR"));
                            @SuppressLint("Range") String ilacZamanSabah = cursor.getString(cursor.getColumnIndex("SABAH"));
                            @SuppressLint("Range") String ilacZamanOglen =  cursor.getString(cursor.getColumnIndex("OGLEN")) ;
                            @SuppressLint("Range") String ilacZamanAksam = cursor.getString(cursor.getColumnIndex("AKSAM"));
                            ilacListesi.add(String.format("Ilac Adi: %s\nIlac Miktar: %s \nSabah: %s\nOglen: %s\nAksam: %s",
                                    ilacAdi, ilacMiktar,
                                    ilacZamanSabah,
                                    ilacZamanOglen,
                                    ilacZamanAksam ));

                            cursor.moveToNext();
                        }
                        ilacAdapter.notifyDataSetChanged();
                        cursor.close();
                        ilacListView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(getApplicationContext(), "Veri bulunamadı", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}










