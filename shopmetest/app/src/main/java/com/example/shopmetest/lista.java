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
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class lista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        Button dodanie_listy;
        Button usuniecie_listy;
        dodanie_listy = findViewById(R.id.dodaj);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ListView lista = findViewById(R.id.wyswitlanielist);

        String [] listaelementow = new String []{};
      final List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(listaelementow));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>
                (lista.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        lista.setAdapter(adapter);


        funkcja2(db,ListElementsArrayList,adapter);


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("adam","wciśnieto liste"+ListElementsArrayList.get(position));
                Intent intent = new Intent(getApplicationContext(),szczegolylisty.class);
                intent.putExtra("nazwa_listy",ListElementsArrayList.get(position));
                intent.putExtra("trybito","lista");
                startActivity(intent);

            }
        });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                usuwanie_listy(ListElementsArrayList.get(position),db);
                return true;
            }
        });
        dodanie_listy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), dodawanie_listy.class);
                intent.putExtra("trybik","New list");
                startActivity(intent);

            }
        });
    }

    public void funkcja2(FirebaseFirestore db, List<String> ListElementsArrayList, ArrayAdapter<String> adapter ) {
        db.collection("listy").whereEqualTo("archiwizowany",false)
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
                                ListElementsArrayList.add(doc.getId());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }

    public void usuwanie_listy(String title, FirebaseFirestore db) {
        new AlertDialog.Builder(this)
                .setTitle("Usunięcie listy")
                .setMessage("Czy na pewno chcesz usunąć liste?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.collection("listy").document(title)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(lista.this,"Usunięto!",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(lista.this,"Wystąpił bląd!",Toast.LENGTH_LONG).show();
                                    }
                                });
                    }})
                .setNeutralButton("Archiwizuj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.collection("listy").document(title)
                                .update("archiwizowany",true)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(lista.this,"Archiwizowano!",Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(lista.this,"Wystąpił bląd!",Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                })
                .setNegativeButton(android.R.string.cancel, null).show();

    }
}