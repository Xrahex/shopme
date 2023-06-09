package com.example.shopmetest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *Klasa historii zakupów
 * */
public class historia extends AppCompatActivity {
    /**
     *Funkcja odpowiadająca za uzupełnienie listy historii
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ListView lista = findViewById(R.id.listyzarchwizowane);
        String [] listaelementow = new String []{};
        final List<String> ListElementsArrayList = new ArrayList<>(Arrays.asList(listaelementow));
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(historia.this, android.R.layout.simple_list_item_1, ListElementsArrayList);
        lista.setAdapter(adapter);

        wyciagnieciehistorii(db,ListElementsArrayList,adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),szczegolylisty.class);
                intent.putExtra("nazwa_listy",ListElementsArrayList.get(position));
                intent.putExtra("trybito","historia");
                startActivity(intent);
            }
        });


    }

    /**
     *Funkcja odpowiadająca za pobranie historii z bazy danych
     * */
    public void wyciagnieciehistorii(FirebaseFirestore db, List<String> ListElementsArrayList, ArrayAdapter<String> adapter ) {
        db.collection("listy").whereEqualTo("archiwizowany",true)
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