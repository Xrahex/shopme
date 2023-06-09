package com.example.shopmetest;

import static com.google.android.gms.tasks.Tasks.await;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
/**
 *Klasa odpowiedzialna za statystyki
 * */
public class Statystyki extends AppCompatActivity {

    TextView text,text2,text3,text4,text5,text6,text7;
    long liczba_produktow=0;
    long liczba_list=0;
    long liczba_zakupionych_produktow=0;

    /**
     *Funkcja odpowiadająca za wczytanie aktywności Statystyki
     * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        text = findViewById(R.id.dane_statystyki);
        text2 = findViewById(R.id.dane_statystyki2);
        text3 = findViewById(R.id.dane_statystyki3);
        text4 = findViewById(R.id.dane_statystyki4);
        text5 = findViewById(R.id.dane_statystyki5);
        text6 = findViewById(R.id.dane_statystyki6);
        text7 = findViewById(R.id.dane_statystyki7);


        statystyki_aktywnych(db);
        statystyki_archwizowane(db);
        liczba_szablonow(db);
        wszystkie_listy(db, new callbackstatystyk() {
            @Override
            public void onCallback(long liczba) {

            }

            @Override
            public void dodaj(long liczba1) {

            }

            @Override
            public void dodaj2(long liczba1) {
                liczba_list=liczba1;
            }
        });
        funkcja2(db, new callbackstatystyk() {
            @Override
            public void onCallback(long liczba) {

            }

            @Override
            public void dodaj(long liczba1) {
                liczba_produktow=liczba1;
                double zmienna=0;
                text4.setText("Średnia liczba produktów na liste: "+zmienna);
                if(liczba_list>0) {
                    double x=liczba_produktow,y=liczba_list;
                    zmienna=x/y;
                    text4.setText("Średnia liczba produktów na liste: "+zmienna);
                }

            }

            @Override
            public void dodaj2(long liczba1) {
            }
        });

        zakupione_srednia(db, new callbackstatystyk() {
            @Override
            public void onCallback(long liczba) {

            }

            @Override
            public void dodaj(long liczba1) {
                liczba_zakupionych_produktow=liczba1;
                double zmienna=0;
                text5.setText("Średnia liczba wykupionych produktów na liste: "+zmienna);
                text6.setText("Liczba utworzonych produktów: "+liczba_produktow);
                text7.setText("Liczba wykupionych produktów: "+liczba_zakupionych_produktow);
                if(liczba_list>0) {
                    double x = liczba_zakupionych_produktow, y = liczba_list;
                    zmienna = x / y;
                    text5.setText("Średnia liczba wykupionych produktów na liste: " + zmienna);
                }
            }

            @Override
            public void dodaj2(long liczba1) {

            }
        });
    }
    /**
     *Funkcja odpowiadająca za wyświetlenie liczby aktywnych list
     * */
    public void statystyki_aktywnych(FirebaseFirestore db) {
        Query query = db.collection("listy").whereEqualTo("archiwizowany",false);
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    text.setText("Ilość aktywnych list: "+ snapshot.getCount());
                } else {
                }

            }
        });
    }
    /**
     *Funkcja odpowiadająca za liczbe zarchiwizowanych list
     * */
    public void statystyki_archwizowane(FirebaseFirestore db) {
        Query query = db.collection("listy").whereEqualTo("archiwizowany",true);
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    text2.setText("Ilość zarchiwizowanych list: "+ snapshot.getCount());

                } else {
                }
            }
        });
    }
    /**
     *Funkcja odpowiadająca za wyświetlenie liczby szablonów
     * */
    public void liczba_szablonow(FirebaseFirestore db) {
        Query query = db.collection("szablony");
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    text3.setText("Ilość szablonów "+ snapshot.getCount());
                } else {
                }
            }
        });
    }

    /**
     *Funkcja odpowiadająca za wyświetlenie liczby wszystkich list
     * */
    public void wszystkie_listy(FirebaseFirestore db, callbackstatystyk s) {
        Query query = db.collection("listy");
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    s.dodaj2(snapshot.getCount());
                } else {
                }
            }
        });
    }

    /**
     *Funkcja odpowiadająca za wyświetlenie średniej liczby produktów na liste
     * */
    public void funkcja2(FirebaseFirestore db,callbackstatystyk s) {
        db.collection("listy")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        final long[] licznik = {0};
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Query query =db.collection("listy").document(doc.getId()).collection("produkty");
                                AggregateQuery countQuery =query.count();
                                countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            AggregateQuerySnapshot snapshot = task.getResult();
                                            licznik[0] = licznik[0] + snapshot.getCount();
                                            s.dodaj(licznik[0]);
                                        } else {
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }

    /**
     *Funkcja odpowiadająca za wyświetlenie średniej ilości zakupionych produktów
     * */
    public void zakupione_srednia(FirebaseFirestore db,callbackstatystyk s) {
        db.collection("listy")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            return;
                        }
                        final long[] licznik = {0};
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Query query =db.collection("listy").document(doc.getId()).collection("produkty").whereEqualTo("status",true);
                                AggregateQuery countQuery =query.count();
                                countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            AggregateQuerySnapshot snapshot = task.getResult();
                                            licznik[0] = licznik[0] + snapshot.getCount();
                                            s.dodaj(licznik[0]);
                                        } else {
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
    }

    private interface callbackstatystyk {
        void onCallback(long liczba);
        void dodaj(long liczba1);
        void dodaj2(long liczba1);

    }

    public void read(callbackstatystyk s) {
        final long[] liczba_list = {0};
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Query query = db.collection("listy");
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    liczba_list[0] =snapshot.getCount();
                    s.onCallback(liczba_list[0]);
                } else {
                }
            }
        });
    }

}