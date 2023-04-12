package com.example.shopmetest;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

    public class myAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Produkt> arrayList;
        private TextView nazwa, ilosc, gazetka;
        private ImageView kosz;
        View.OnClickListener listener;
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        public myAdapter(Context context, ArrayList<Produkt> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                convertView = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
                nazwa = convertView.findViewById(R.id.serailNumber);
                ilosc = convertView.findViewById(R.id.studentName);
                gazetka = convertView.findViewById(R.id.mobileNum);
                kosz = convertView.findViewById(R.id.kosz);
                nazwa.setText(" " + arrayList.get(position).getNazwa());
                ilosc.setText(arrayList.get(position).getLiczba_produktow());
                gazetka.setText(arrayList.get(position).getStrona_gazetkowicza());
                kosz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("essunia","elo");
                        usuwanie_produktu(arrayList.get(position).getLista(),db,arrayList.get(position).getNazwa());
                    }
                });
                Produkt produkt = new Produkt();
                produkt.setNazwa(arrayList.get(position).getNazwa());
                if(arrayList.get(position).getStatus()== true) {
                convertView.setBackgroundColor(Color.GREEN);}
                // nazwa.setPaintFlags(nazwa.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            return convertView;
            }

        public void usuwanie_produktu(String title, FirebaseFirestore db,String name) {
            new AlertDialog.Builder(context)
                    .setTitle("Usunięcie produktu")
                    .setMessage("Czy na pewno chcesz ususnąć produkt?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            db.collection("listy").document(title).collection("produkty").document(name)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(context,"Usunięto!",Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(context,"Wystąpił bląd!",Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }})
                    .setNegativeButton(android.R.string.cancel, null).show();
        }
        }

