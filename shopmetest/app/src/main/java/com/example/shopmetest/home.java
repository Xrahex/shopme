package com.example.shopmetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
/**
 *Klasa menu głównego
 * */
public class home extends AppCompatActivity {

    CardView listy;
    CardView zmiana_motywu;
    CardView mapy;
    CardView szablony;
    CardView historia;
    CardView statystyki;
    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private AdView mAdView;

    /**
     *Funkcja odpowiadająca za nawigację po menu
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        szablony = findViewById(R.id.cardszablony);
        listy = findViewById(R.id.cardlisty);
        zmiana_motywu = findViewById(R.id.cardzmianamotywu);
        mapy = findViewById(R.id.cardwyznacztrase);
        historia = findViewById(R.id.cardarchiwum);
        statystyki= findViewById(R.id.cardstatystyki);

        DailyNotificationReceiver.setDailyNotification(this);

        listy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), lista.class);
                startActivity(intent);
            }
        });


        zmiana_motywu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
                nightMODE = sharedPreferences.getBoolean("night", false);
                if (nightMODE) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                if (nightMODE) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", false);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor = sharedPreferences.edit();
                    editor.putBoolean("night", true);
                }
                editor.apply();
            }
        });

        mapy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), naszemapy.class);
                startActivity(intent);
            }
        });

        szablony.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), szablony.class);
                startActivity(intent);
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        historia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), historia.class);
                startActivity(intent);
            }
        });

        statystyki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Statystyki.class);
                startActivity(intent);
            }
        });

    }
}
