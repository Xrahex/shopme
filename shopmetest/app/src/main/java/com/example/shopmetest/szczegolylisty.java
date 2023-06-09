package com.example.shopmetest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 *Klasa wyświetlająca szczegóły danej listy
 * */
public class szczegolylisty extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    /**
     *Funkcja odpowiadająca za wyświetlenie szczegolylisty (nazwa,ilosc,strona w gazetce)
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegolylisty);

        ListView produkty = findViewById(R.id.lista_produktow);
        Button dodaj = findViewById(R.id.btn_dodaj_produkt);
        Intent intent= getIntent();
        String test =intent.getStringExtra("nazwa_listy");
        String tryb =intent.getStringExtra("trybito");
        ArrayList<Produkt> p = new ArrayList<>();

        myAdapter adapter;
        myAdapterhistoria adapter2;


        adapter = new myAdapter(this, p);
        adapter2 = new myAdapterhistoria(this, p);

        if(tryb.equals("lista")) {
            produkty.setAdapter(adapter);
            sluchaniejednegodokumentu(test);
            funkcja3(db,p,adapter,test);

        }
        if(tryb.equals("szablon")) {
            produkty.setAdapter(adapter);
            sluchaniejednegodokumentuschemat(test);
            funkcja3szablon(db,p,adapter,test);
        }
        if(tryb.equals("historia")) {
            produkty.setAdapter(adapter2);
            sluchaniejednegodokumentu(test);
            funkcja3historia(db,p,adapter2,test);
            dodaj.setVisibility(View.INVISIBLE);
        }


        produkty.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            /**
             *Funkcja odpowiadająca za oznaczenie produktu jako zakupionego
             * */
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(tryb.equals("lista")) {
                    zakupiono(p.get(position).getLista(), db, p.get(position).getNazwa());
                }
                return true;
            }
        });

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),dodanieproduktu.class);
                intent.putExtra("nazwa",test);
                intent.putExtra("tryb",tryb);
                startActivity(intent);
            }
        });

    }
    /**
     *Funkcja odpowiadająca za zmiane nazwy listy w czasie rzeczywistym
     * */
    public void sluchaniejednegodokumentu(String test) {
        final DocumentReference docRef = db.collection("listy").document(test);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    TextView nazwa_listy = findViewById(R.id.nazwa_listy);
                    nazwa_listy.setText(snapshot.get("title").toString());
                } else {
                }
            }
        });
    }
    /**
     *Funkcja odpowiadająca za zmiane nazwy schematu w czasie rzeczywistym
     * */
    public void sluchaniejednegodokumentuschemat(String test) {
        final DocumentReference docRef = db.collection("szablony").document(test);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    TextView nazwa_listy = findViewById(R.id.nazwa_listy);
                    nazwa_listy.setText(snapshot.get("title").toString());
                } else {
                }
            }
        });
    }
    /**
     *Funkcja odpowiadająca za uzuzpełnianie listy produktów oraz nasłuchiwanie zmian w bazie danych
     * */
    public void funkcja3(FirebaseFirestore db, List<Produkt> ListElementsArrayList, myAdapter adapter,String test ) {
        db.collection("listy").document(test).collection("produkty")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        ListElementsArrayList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if(doc != null) {
                                ListElementsArrayList.add(doc.toObject(Produkt.class));
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }
    /**
     *Funkcja odpowiadająca za uzuzpełnianie schematów produktów oraz nasłuchiwanie zmian w bazie danych
     * */
    public void funkcja3szablon(FirebaseFirestore db, List<Produkt> ListElementsArrayList, myAdapter adapter,String test ) {
        db.collection("szablony").document(test).collection("produkty")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        ListElementsArrayList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if(doc != null) {
                                ListElementsArrayList.add(doc.toObject(Produkt.class));
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }
    /**
     *Funkcja odpowiadająca za uzuzpełnianie historii produktów listy oraz nasłuchiwanie zmian w bazie danych
     * */
    public void funkcja3historia(FirebaseFirestore db, List<Produkt> ListElementsArrayList, myAdapterhistoria adapter,String test ) {
        db.collection("listy").document(test).collection("produkty")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        ListElementsArrayList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            if(doc != null) {
                                ListElementsArrayList.add(doc.toObject(Produkt.class));
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }
    /**
     *Funkcja odpowiadająca za zmiane statusu produktu w bazie danych
     * */
    public void zakupiono (String title, FirebaseFirestore db,String name) {
                        db.collection("listy").document(title).collection("produkty").document(name)
                                .update("status",true)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(szczegolylisty.this,"Zakupiono!",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(szczegolylisty.this,"Wystąpił bląd!",Toast.LENGTH_LONG).show();
                                    }
                                });
                    }

}