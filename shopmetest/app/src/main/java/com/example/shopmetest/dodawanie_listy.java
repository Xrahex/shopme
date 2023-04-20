package com.example.shopmetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class dodawanie_listy extends AppCompatActivity {

    EditText dodanie_listy;
    Button btn_dodanie_listy;
    Switch aSwitch;
    Spinner spinner;

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
        String tablica[]= {"hej","test","test2"};
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, ListElementsArrayList);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        Log.d("listahejhej", ListElementsArrayList.toString());
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
        Log.d("mode8", mode);

        btn_dodanie_listy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dodanie_listy = findViewById(R.id.dodanie_listy2);
                String tytul = dodanie_listy.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Map<String, Object> lista = new HashMap<>();
                lista.put("title", tytul);
                if(mode.equals("New list")) {

                    db.collection("listy").document(tytul)
                            .set(lista).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(dodawanie_listy.this, "Utworzono nową liste!", Toast.LENGTH_LONG).show();
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
    public void wyszukanieszablonow(FirebaseFirestore db, List<String> ListElementsArrayList, ArrayAdapter<String> adapter ) {
        db.collection("szablony")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("wynik80", "Listen failed.", e);
                            return;
                        }
                        ListElementsArrayList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Log.d("testerinio",ListElementsArrayList.toString());
                                ListElementsArrayList.add(doc.getId());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}