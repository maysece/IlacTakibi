package com.example.a71donemproje.Activitys;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class KayitEdilecekIlac extends AppCompatActivity {
  com.example.a71donemproje.databinding.ActivityKayitEdilecekIlacBinding binding;
    SQLiteDatabase veritabani;
    long userID ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.a71donemproje.databinding.ActivityKayitEdilecekIlacBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent gelenIntent = getIntent();
        userID = gelenIntent.getLongExtra("userID", -1);
        if (userID == -1) {
            Toast.makeText(this, "Kullanıcı ID Hatası", Toast.LENGTH_SHORT).show();
            finish();
        }
        Toast.makeText(this, String.format("Gelen Kullanici ID: %d", userID), Toast.LENGTH_SHORT).show();
        veritabani = openOrCreateDatabase("kayitlar.db", MODE_PRIVATE, null);
        veritabani.execSQL("CREATE TABLE IF NOT EXISTS ilaclar (ID INTEGER PRIMARY KEY AUTOINCREMENT, ILAC_ADI TEXT," +
                " GUNLUK_ALINACAK_MIKTAR TEXT, SABAH TEXT, OGLEN TEXT, AKSAM TEXT, KULLANICI_ID INTEGER)");

        binding.kaydetBtn.setOnClickListener(v -> {
            String ilacAdi = Objects.requireNonNull(binding.ilacAdiEdt.getText()).toString();
            String gunlukAlinacakMiktar = Objects.requireNonNull(binding.gunlukTxt.getText()).toString();

            if (ilacAdi.trim().isEmpty() || gunlukAlinacakMiktar.trim().isEmpty()) {
                // Eğer ilaç adı veya günlük alınacak miktar boş bırakılmışsa kullanıcıya uyarı ver
                Toast.makeText(KayitEdilecekIlac.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
            } else {
                // Veriler boş bırakılmamışsa işlemi devam ettir
                ContentValues contentValues = getContentValues(ilacAdi, gunlukAlinacakMiktar);
                long result = veritabani.insert("ilaclar", null, contentValues);

                if (result != -1) {
                    Toast.makeText(KayitEdilecekIlac.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(KayitEdilecekIlac.this, "Kayıt Başarısız", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.hatirlaticiTxt.setOnClickListener(v -> {
            Context mContext = v.getContext(); //

            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("Hatırlatıcı Ekleme");
            builder.setPositiveButton("Evet", (dialog, which) -> {
                Intent intent = new Intent(mContext, Alarm.class);
                mContext.startActivity(intent);
            });
            builder.setNegativeButton("Hayır", (dialog, which) ->
            Toast.makeText(this, "Alarm Kurma İşlemi İptal Edildi", Toast.LENGTH_SHORT).show());
            builder.show();



        });
    }

    @NonNull
    private ContentValues getContentValues(String ilacAdi, String gunlukAlinacakMiktar) {
        boolean sabahSecim = binding.sabahSecimCB.isChecked();
        boolean ogleSecim = binding.ogleSecimCB.isChecked();
        boolean aksamSecim = binding.aksamSecimCB.isChecked();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ILAC_ADI", ilacAdi);
        contentValues.put("GUNLUK_ALINACAK_MIKTAR", gunlukAlinacakMiktar);
        contentValues.put("SABAH", sabahSecim ? "Evet" : "Hayır");
        contentValues.put("OGLEN", ogleSecim ? "Evet" : "Hayır");
        contentValues.put("AKSAM", aksamSecim ? "Evet" : "Hayır");
        contentValues.put("KULLANICI_ID", userID);
        return contentValues;
    }

}
