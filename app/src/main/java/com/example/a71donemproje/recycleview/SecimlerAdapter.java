package com.example.a71donemproje.recycleview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a71donemproje.Activitys.GuncellemeEkrani;
import com.example.a71donemproje.Activitys.KayitEkleme;
import com.example.a71donemproje.Activitys.KayitGoster;
import com.example.a71donemproje.Activitys.KayitSil;
import com.example.a71donemproje.R;

import java.util.List;

public class SecimlerAdapter extends RecyclerView.Adapter<SecimlerAdapter.CardViewNesneTutucu> {
    private final Context mContext;
    private final List<Secimler> secimlerList;

    public SecimlerAdapter(Context mContext, List<Secimler> secimlerList) {
        this.mContext = mContext;
        this.secimlerList = secimlerList;
    }
    public static class CardViewNesneTutucu extends RecyclerView.ViewHolder{
        public ImageView resimImg;
        public TextView ilgiliTxt;
        public Button secBtn;
        public CardViewNesneTutucu(@NonNull View itemView){
            super(itemView);
            resimImg = itemView.findViewById(R.id.ilgiliImg);
            ilgiliTxt = itemView.findViewById(R.id.yaziTxt);
            secBtn = itemView.findViewById(R.id.secimBtn);
        }
    }

    @NonNull
    @Override
    public CardViewNesneTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(
          R.layout.cardview,parent,false);
        return new CardViewNesneTutucu(itemView);
    }

    @SuppressLint("DiscouragedApi")
    @Override
    public void onBindViewHolder(@NonNull CardViewNesneTutucu holder, int position) {
        Secimler secimler= secimlerList.get(position);
        holder.ilgiliTxt.setText(secimler.getSecim_adi());
        holder.resimImg.setImageResource(mContext.getResources().getIdentifier(
          secimler.getResim_adi(),"drawable" ,mContext.getPackageName()
        ));
        holder.secBtn.setOnClickListener(v -> {

            String secim = secimler.getSecim_adi();
            switch (secim) {
                case "Ekle":
                    Intent intent_ekle = new Intent(mContext, KayitEkleme.class);
                    ContextCompat.startActivity(mContext, intent_ekle, null);


                    break;
                case "Sil":
                   Intent intent_kaydiSil = new Intent(mContext, KayitSil.class);
                   ContextCompat.startActivity(mContext, intent_kaydiSil, null);


                    break;
                case "Güncelle":
                    Intent intent_guncelleme = new Intent(mContext, GuncellemeEkrani.class);
                    ContextCompat.startActivity(mContext, intent_guncelleme, null);

                    break;
                default:
                    Intent intent_goster = new Intent(mContext, KayitGoster.class);
                    ContextCompat.startActivity(mContext, intent_goster, null);
                    break;
            }
        });

    }

    @Override
    public int getItemCount() {
        return secimlerList.size();
    }





    }


