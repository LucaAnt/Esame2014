package com.trieste.mobile.esame2014alberghi.database.tablehelpers;

import android.provider.BaseColumns;

public class TableHelperAlberghi implements BaseColumns {

    public static final String TABLE_NAME = "alberghi";
    public static final String NOME = "nome";
    public static final String CITTA = "citta";
    public static final String STELLE = "stelle";
    public static final String COSTO = "costo";


    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            NOME + " TEXT , " +
            CITTA + " TEXT ," +
            STELLE + " INTEGER , " +
            COSTO + " INTEGER " +
            ") ;";
}
