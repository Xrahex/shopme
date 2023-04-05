package com.example.shopmetest;

public class Produkt {

    private String nazwa;
    private String liczba_produktow;
    private String strona_gazetkowicza;


    public Produkt() {

    }

    public String getNazwa() {
        return nazwa;
    }

    public String getLiczba_produktow() {
        return liczba_produktow;
    }

    public String getStrona_gazetkowicza() {
        return strona_gazetkowicza;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setLiczba_produktow(String liczba_produktow) {
        this.liczba_produktow = liczba_produktow;
    }

    public void setStrona_gazetkowicza(String strona_gazetkowicza) {
        this.strona_gazetkowicza = strona_gazetkowicza;
    }


}
