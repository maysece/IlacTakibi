package com.example.a71donemproje.Activitys;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.a71donemproje.R;
import com.example.a71donemproje.databinding.ActivityAlarmBinding;
import com.example.a71donemproje.databinding.ActivityAlarmZilBinding;

public class AlarmActivity extends AppCompatActivity {
    ActivityAlarmZilBinding binding;
    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmZilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
        ringtone.play();
        binding.stopAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ringtone.isPlaying())
                    ringtone.stop();
            }
        });
    }
}