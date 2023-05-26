package com.example.shopmetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class naszemapy extends AppCompatActivity implements OnMapReadyCallback {

    EditText etDestination;
    Button trasa;
    private FusedLocationProviderClient mClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;

    /**
     *Funkcja odpowiadająca za wczytanie aktywności naszemapy
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naszemapy);


        etDestination = findViewById(R.id.et_destination);
        trasa = findViewById(R.id.btn_dodajtrase);


        trasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sDestination = etDestination.getText().toString().trim();
                String sTarget = "Your location";

                if(sDestination.equals("")) {
                    Toast.makeText(getApplicationContext(),"Wpisz nazwe sklepu",Toast.LENGTH_LONG).show();
                }
                else {
                    DisplayTrack(sTarget,sDestination);
                }

            }

        });
    }
    /**
     *Funkcja odpowiadająca za przekazanie celu do aplikacji map google
     * */
    private void DisplayTrack(String sDestination,String sTarget) {
        try {
            Uri uri = Uri.parse("https://www.google.co.in/maps/dir/" +sDestination+ "/" + sTarget);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        catch(ActivityNotFoundException e)
        {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}