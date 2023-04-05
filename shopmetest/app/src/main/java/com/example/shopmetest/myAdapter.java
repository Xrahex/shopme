package com.example.shopmetest;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

    public class myAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Produkt> arrayList;
        private TextView nazwa, ilosc, gazetka;
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
            nazwa.setText(" " + arrayList.get(position).getNazwa());
            ilosc.setText(arrayList.get(position).getLiczba_produktow());
            gazetka.setText(arrayList.get(position).getStrona_gazetkowicza());
           // nazwa.setPaintFlags(nazwa.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            return convertView;
        }
    }

