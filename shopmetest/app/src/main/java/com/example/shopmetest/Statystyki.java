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

public class Statystyki extends AppCompatActivity {

    TextView text,text2,text3,text4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statystyki);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        text = findViewById(R.id.dane_statystyki);
        text2 = findViewById(R.id.dane_statystyki2);
        text3 = findViewById(R.id.dane_statystyki3);
        text4 = findViewById(R.id.dane_statystyki4);


        statystyki_aktywnych(db);
        statystyki_archwizowane(db);
        liczba_szablonow(db);
        srednia_ilosc_produktow(db);

    }

    public void statystyki_aktywnych(FirebaseFirestore db) {
        Query query = db.collection("listy").whereEqualTo("archiwizowany",false);
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    Log.d("XD1", "Count: " + snapshot.getCount());
                    text.setText("Ilość aktywnych list: "+ snapshot.getCount());
                } else {
                    Log.d("XD2", "Count failed: ", task.getException());
                }

            }
        });
    }
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
                    Log.d("XD2", "Count failed: ", task.getException());
                }
            }
        });
    }
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
                    Log.d("XD2", "Count failed: ", task.getException());
                }
            }
        });
    }

    public void srednia_ilosc_produktow(FirebaseFirestore db) {
        Query query = db.collection("listy");
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    Log.d("XD1", "Count: " + snapshot.getCount());
                } else {
                    Log.d("XD2", "Count failed: ", task.getException());
                }
            }
        });
    }

    public void funkcja2(FirebaseFirestore db) {
        final List<String>[] arrayList = new List[]{new ArrayList<>()};
        db.collection("listy")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("wynik80", "Listen failed.", e);
                            return;
                        }
                        final long[] licznik = {0};
                        long liczba_list = 0;
                        final long[] srednia = {0};
                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.getId() != null) {
                                Query query =db.collection("listy").document(doc.getId()).collection("produkty");
                                AggregateQuery countQuery =query.count();
                                countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            // Count fetched successfully
                                            AggregateQuerySnapshot snapshot = task.getResult();
                                            //  Log.d("XD1", "Count: " + snapshot.getCount());
                                            licznik[0] = licznik[0] + snapshot.getCount();
                                            Log.d("test9", "" + licznik[0]);
                                        } else {
                                            Log.d("XD2", "Count failed: ", task.getException());
                                        }
                                        try {
                                            await(task);
                                        } catch (ExecutionException ex) {
                                            ex.printStackTrace();
                                        } catch (InterruptedException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });
                            }

                            liczba_list++;
                            srednia[0] = licznik[0]/liczba_list;
                            Log.d("srednia", "" + srednia[0]);

                            Log.d("liczba list", "" + liczba_list);
                        }
                            long liczba = licznik[0];
                            long wynik = liczba/liczba_list;
                            Log.d("test10", "" + wynik);
                    }

                });
    }

}