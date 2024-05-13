package com.example.a71donemproje.recycleview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Secimler {
    private  String secim_adi;
    private  String resim_adi;

    public Secimler(String secim_adi, String resim_adi){
        this.secim_adi = secim_adi;
        this.resim_adi = resim_adi;
    }
    public String getSecim_adi(){return secim_adi;}
    public void setSecim_adi(String secim_adi){this.secim_adi = secim_adi;}
    public String getResim_adi(){return resim_adi;}
    public void setResim_adi(String resim_adi){this.resim_adi = resim_adi;}


    }
