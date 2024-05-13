package com.example.a71donemproje.Activitys;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a71donemproje.receivers.AlarmKontrol;

import java.util.Calendar;
import java.util.Objects;

public class Alarm extends AppCompatActivity {
    private TimePickerDialog timePickerDialog;
    final static int REQUEST_CODE = 1;
    com.example.a71donemproje.databinding.ActivityAlarmBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.a71donemproje.databinding.ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.alarmKurBtn.setOnClickListener(v -> {
           openPickerDialog(true);
        });
    }


    private void openPickerDialog(boolean is24hour) {

        Calendar calendar = Calendar.getInstance();

        timePickerDialog = new TimePickerDialog(
                Alarm.this,
                onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                is24hour);
        timePickerDialog.setTitle("Alarm Ayarla");

        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListener
            = new TimePickerDialog.OnTimeSetListener(){

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if(calSet.compareTo(calNow) <= 0){

                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet);
        }};

    private void setAlarm(Calendar alarmCalender){


        Toast.makeText(getApplicationContext(),"Alarm Ayarlandı!",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), AlarmKontrol.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), REQUEST_CODE, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmCalender.getTimeInMillis(), pendingIntent);

    }
    /*
    private void kurAlarm(String alarmZamani) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (!alarmZamani.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, Integer.parseInt(alarmZamani));
            long alarmZamaniMillis = cal.getTimeInMillis();

            Intent intent = new Intent(this, AlarmKontrol.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmZamaniMillis, pendingIntent);

            Toast.makeText(this, "Alarm " + alarmZamani + " dakika sonra kuruldu", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Alarm.this, "Lütfen Bir Zaman Giriniz!!", Toast.LENGTH_SHORT).show();
        }
    }*/
}