package com.example.shopmetest;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class dodanieproduktu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodanieproduktu);

        Locale locale = new Locale("pl");
        Locale.setDefault(locale);

        Button dodaj_produkt = findViewById(R.id.btn_dodanie_produktu);
        EditText nazwa_produktu = findViewById(R.id.nazwa_produktu);
        EditText ilosc_produktow = findViewById(R.id.liczba_produktow);
        EditText strona_w_gazetce = findViewById(R.id.strona_w_gazetce);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Intent intent= getIntent();
        String test =intent.getStringExtra("nazwa");
        String tryb =intent.getStringExtra("tryb");


        dodaj_produkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nazwa = nazwa_produktu.getText().toString();
                String ilosc = ilosc_produktow.getText().toString();
                String strona = strona_w_gazetce.getText().toString();


                Produkt produkt = new Produkt();
                if(nazwa !=null) {
                    produkt.setNazwa(nazwa);
                }
                if(ilosc !=null) {
                    produkt.setLiczba_produktow(ilosc);
                }
                if(strona !=null) {
                    produkt.setStrona_gazetkowicza(strona);
                }
                produkt.setStatus(false);
                produkt.setLista(test);
                if(tryb.equals("szablon")) {
                    produkt.setSchemat(true);
                }
                else {
                    produkt.setSchemat(false);
                }
               if(tryb.equals("lista")) {

                   db.collection("listy").document(test).collection("produkty").document(nazwa)
                           .set(produkt).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   finish();
                               }
                           });
               }
                if(tryb.equals("szablon")) {

                    db.collection("szablony").document(test).collection("produkty").document(nazwa)
                            .set(produkt).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    finish();
                                }
                            });
                }
            }

        });


    }


}