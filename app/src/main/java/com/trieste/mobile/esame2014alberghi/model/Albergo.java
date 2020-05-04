package com.trieste.mobile.esame2014alberghi.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.trieste.mobile.esame2014alberghi.database.tablehelpers.TableHelperAlberghi;

public class Albergo {
    private int _id;
    private String nome;
    private String citta;
    private int stelle;
    private int costo;

    public Albergo(String nome, String citta, int stelle, int costo) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.costo = costo;
    }

    public Albergo(Cursor c)
    {
        this._id = c.getInt(c.getColumnIndex(TableHelperAlberghi._ID));
        this.nome = c.getString(c.getColumnIndex(TableHelperAlberghi.NOME));
        this.citta = c.getString(c.getColumnIndex(TableHelperAlberghi.CITTA));
        this.stelle = c.getInt(c.getColumnIndex(TableHelperAlberghi.STELLE));
        this.costo = c.getInt(c.getColumnIndex(TableHelperAlberghi.COSTO));
    }

    public ContentValues toContentValues()
    {
        ContentValues cValues = new ContentValues();
        cValues.put(TableHelperAlberghi.NOME,nome);
        cValues.put(TableHelperAlberghi.CITTA,citta);
        cValues.put(TableHelperAlberghi.STELLE,stelle);
        cValues.put(TableHelperAlberghi.COSTO,costo);
        return cValues;
    }

    public int get_id() {
        return _id;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public int getStelle() {
        return stelle;
    }

    public int getCosto() {
        return costo;
    }

    @Override
    public String toString() {
        return "Albergo{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", citta='" + citta + '\'' +
                ", stelle=" + stelle +
                ", costo=" + costo +
                '}';
    }
}
