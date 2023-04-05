package com.example.shopmetest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class szczegolylisty extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szczegolylisty);

        ListView produkty = findViewById(R.id.lista_produktow);
        Button dodaj = findViewById(R.id.btn_dodaj_produkt);
        Intent intent= getIntent();
        String test =intent.getStringExtra("nazwa_listy");
        ArrayList<Produkt> p = new ArrayList<>();

        myAdapter adapter;
        adapter = new myAdapter(this, p);
        produkty.setAdapter(adapter);



        sluchaniejednegodokumentu(test);
        funkcja3(db,p,adapter,test);

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),dodanieproduktu.class);
                intent.putExtra("nazwa",test);
                startActivity(intent);
            }
        });


    }

    public void sluchaniejednegodokumentu(String test) {
        final DocumentReference docRef = db.collection("listy").document(test);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("test1", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("test2", "Current data: " + snapshot.getData());
                    TextView nazwa_listy = findViewById(R.id.nazwa_listy);
                    nazwa_listy.setText(snapshot.get("title").toString());
                } else {
                    Log.d("test3", "Current data: null");
                }
            }
        });
    }

    public void funkcja3(FirebaseFirestore db, List<Produkt> ListElementsArrayList, myAdapter adapter,String test ) {
        db.collection("listy").document(test).collection("produkty")
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
                            if(doc != null) {
                                ListElementsArrayList.add(doc.toObject(Produkt.class));
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }
}