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

public class myAdapterhistoria extends BaseAdapter {
    private Context context;
    private ArrayList<Produkt> arrayList;
    private TextView nazwa, ilosc, gazetka;
    private ImageView kosz;
    View.OnClickListener listener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public myAdapterhistoria(Context context, ArrayList<Produkt> arrayList) {
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
        Produkt produkt = new Produkt();
        produkt.setNazwa(arrayList.get(position).getNazwa());
        return convertView;
    }





}

