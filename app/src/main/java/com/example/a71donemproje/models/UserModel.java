package com.example.a71donemproje.models;

public class UserModel {
    public String isim;
    public String soyisim;
    public String kullaniciAdi;
    public String sifre;
    public int id;

    public UserModel(String isim, String soyisim, String kullaniciAdi, String sifre, int id) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.id = id;
    }

}
