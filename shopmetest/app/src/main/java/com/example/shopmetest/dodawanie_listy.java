package com.example.shopmetest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 *Klasa dodawania list
 * */
public class dodawanie_listy extends AppCompatActivity {

    EditText dodanie_listy;
    Button btn_dodanie_listy;
    Switch aSwitch;
    Spinner spinner;

    /**
     *Funkcja odpowiadająca za dodawanie listy lub szablonu
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_listy);
        String [] listaelementow = new String []{};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(listaelementow));
        btn_dodanie_listy= findViewById(R.id.btn_dodanie_listy);
        aSwitch = findViewById(R.id.zszablonu);
        spinner = findViewById(R.id.spinnerlistyszablonow);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ListElementsArrayList);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        wyszukanieszablonow(db,ListElementsArrayList,ad);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()==true) {
                    spinner.setVisibility(View.VISIBLE);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                }
                else
                {
                    spinner.setVisibility(View.INVISIBLE);
                }
            }
        });

        Intent intent= getIntent();
        String mode =intent.getStringExtra("trybik");
        if(mode.equals("New schema")) {
            aSwitch.setVisibility(View.INVISIBLE);
        }

        btn_dodanie_listy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodanie_listy = findViewById(R.id.dodanie_listy2);
                String tytul = dodanie_listy.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                klasalisty lista = new klasalisty();
                lista.title = tytul;
                lista.archiwizowany= false;
                if(mode.equals("New list")) {
                    aSwitch.setVisibility(View.VISIBLE);
                    String szablon = spinner.getSelectedItem().toString();
                    db.collection("listy").document(tytul).
                            set(lista).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(dodawanie_listy.this, "Utworzono nową liste!", Toast.LENGTH_LONG).show();
                                    if(aSwitch.isChecked()==true) {
                                        funkcja_spinner(db, szablon, tytul);
                                    }
                                }
                            });
                }
                if(mode.equals("New schema")) {
                    db.collection("szablony").document(tytul)
                            .set(lista).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(dodawanie_listy.this, "Utworzono nowy szablon!", Toast.LENGTH_LONG).show();
                                }
                            });
                }
                finish();
            }
        });
    }

    /**
     *Funkcja odpowiadająca za pobranie produktów z danego szablonu. Dodaje produkty do nowo powstałej listy
     * */
    public void funkcja_spinner(FirebaseFirestore db,String test, String test2 ) {
        db.collection("szablony").document(test).collection("produkty")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("wynik80", "Listen failed.", e);
                            return;
                        }
                        ArrayList<Produkt> produkt = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : value) {
                            if(doc != null) {
                                Produkt p = new Produkt();
                                p = doc.toObject(Produkt.class);
                                p.setSchemat(false);
                                p.setLista(test2);
                                produkt.add(p);
                            }
                        }
                        for(int i=0; i<produkt.size(); i++) {
                            db.collection("listy").document(test2).collection("produkty")
                                    .document(produkt.get(i).getNazwa()).set(produkt.get(i));
                        }
                    }
                });
    }
    /**
     *Funkcja odpowiadająca za wyszukanie szablonów w bazie danych
     * */
    public void wyszukanieszablonow(FirebaseFirestore db, List<String> ListElementsArrayList, ArrayAdapter<String> adapter ) {
        db.collection("szablony")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        ListElementsArrayList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                ListElementsArrayList.add(doc.getId());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}