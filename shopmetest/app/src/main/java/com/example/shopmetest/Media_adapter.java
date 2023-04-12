package com.example.shopmetest;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Media_adapter extends ArrayAdapter<Produkt>
{
    private ArrayList<Produkt> data ;
    private TextView nazwa, ilosc, gazetka;
    private ImageView kosz;
    Context context;
    int                     layoutResourceId;
    View.OnClickListener    listener;

    public Media_adapter(Context context, int layoutResourceId,ArrayList<Produkt> data, View.OnClickListener listener)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        Produkt holder;

        if (row == null)
        {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new Produkt();
            nazwa = convertView.findViewById(R.id.serailNumber);
            ilosc = convertView.findViewById(R.id.studentName);
            gazetka = convertView.findViewById(R.id.mobileNum);
            kosz = convertView.findViewById(R.id.kosz);
            ilosc.setText(data.get(position).getLiczba_produktow());
            gazetka.setText(data.get(position).getStrona_gazetkowicza());
            kosz.setOnClickListener(listener);

            holder.setNazwa(data.get(position).getNazwa());

            row.setTag(holder);

        }
        else
        {
            holder = (Produkt) row.getTag();
        }

        Produkt media = data.get(position);
        holder.setNazwa(media.getNazwa());

        return row;
    }

    class MediaHolder
    {
        TextView    song_title;
        ImageView   add_to_favorites, share;
    }
}