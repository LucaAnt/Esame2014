package com.trieste.mobile.esame2014alberghi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.trieste.mobile.esame2014alberghi.database.tablehelpers.TableHelperAlberghi;

public class DatabaseHelperAlberghi extends SQLiteOpenHelper {


    public static final String DB_NAME = "alberghi.db";
    public static final int VERSION = 1;

    public DatabaseHelperAlberghi(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableHelperAlberghi.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
