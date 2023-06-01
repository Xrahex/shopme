package com.example.shopmetest;

import static org.junit.Assert.*;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Test;

public class StatystykiTest {

    @Test
    public void testStatystykiAktywnych() throws Exception {

                FirebaseFirestore firestore = FirebaseFirestore.getInstance();

                firestore.collection("users").document("user123").get()
                        .addOnSuccessListener(documentSnapshot -> {
                            Assert.assertTrue(documentSnapshot.exists());
                        })
                        .addOnFailureListener(e -> {
                            Assert.fail("Wystąpił błąd: " + e.getMessage());
                        });
            }
        }

