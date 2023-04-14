package com.example.shopmetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class dodawanie_listy extends AppCompatActivity {

    EditText dodanie_listy;
    Button btn_dodanie_listy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_listy);

        btn_dodanie_listy= findViewById(R.id.btn_dodanie_listy);
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
}