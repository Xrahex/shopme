package com.example.shopmetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

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
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends AppCompatActivity {

    Button listy;
    Button zmiana_motywu;
    Button mapy;
    Button szablony;
    Button historia;
    Button statystyki;
    boolean nightMODE;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private AdView mAdView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     szablony = findViewById(R.id.btn_szablony);
     listy = findViewById(R.id.listy);
     zmiana_motywu = findViewById(R.id.motyw);
     mapy = findViewById(R.id.mapa);
     historia = findViewById(R.id.btn_historia);
     statystyki= findViewById(R.id.btn_statystyki);

     listy.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(getApplicationContext(), lista.class);
             startActivity(intent);
         }
     });


        createNotificationChannel();

        Button button = findViewById(R.id.btn_powiadomienie);

        button.setOnClickListener(v ->{
            Toast.makeText(this, "Ustawiono przypomnienie.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, Przypomnienie.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            long czaswcisnieciaprzycisku = System.currentTimeMillis();
            long czaswms = 1000 * 5;

            alarmManager.set(AlarmManager.RTC_WAKEUP, czaswcisnieciaprzycisku + czaswms, pendingIntent);

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
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Powiadamiacz";
            String description = "Na potrzeby projektu";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("przypomnienie",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }




}
