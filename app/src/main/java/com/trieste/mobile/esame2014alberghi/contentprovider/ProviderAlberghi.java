package com.trieste.mobile.esame2014alberghi.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.trieste.mobile.esame2014alberghi.database.DatabaseHelperAlberghi;
import com.trieste.mobile.esame2014alberghi.database.tablehelpers.TableHelperAlberghi;

public class ProviderAlberghi extends ContentProvider {



    public static final String AUTORITY = "com.trieste.mobile.esame2014alberghi.contentprovider";

    public static final String BASE_PATH_ALBERGHI = "alberghi";

    public static final int ALL_ALBERGHI = 1;
    public static final int SINGLE_ALBERGO = 0;


    public static final String MIME_TYPE_ALBERGHI = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_alberghi";
    public static final String MIME_TYPE_ALBERGO = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_albergo";

    public static final Uri ALBERGHI_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTORITY
            + "/" + BASE_PATH_ALBERGHI);


    private DatabaseHelperAlberghi database;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTORITY, BASE_PATH_ALBERGHI, ALL_ALBERGHI);
        uriMatcher.addURI(AUTORITY, BASE_PATH_ALBERGHI + "/#", SINGLE_ALBERGO);

    }


    @Override
    public boolean onCreate() {
        database = new DatabaseHelperAlberghi(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SINGLE_ALBERGO:
                return MIME_TYPE_ALBERGO;
            case ALL_ALBERGHI:
                return MIME_TYPE_ALBERGHI;
        }
        return null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = database.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case SINGLE_ALBERGO:
                builder.setTables(TableHelperAlberghi.TABLE_NAME);
                builder.appendWhere(TableHelperAlberghi._ID + " = " + uri.getLastPathSegment());
                break;
            case ALL_ALBERGHI:
                builder.setTables(TableHelperAlberghi.TABLE_NAME);
                break;
        }
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, (sortOrder==null?(TableHelperAlberghi.NOME + " ASC "):sortOrder));
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        switch (uriMatcher.match(uri))
        {
            case ALL_ALBERGHI:
                SQLiteDatabase db = database.getWritableDatabase();
                long result = db.insert(TableHelperAlberghi.TABLE_NAME, null, values);
                String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_ALBERGHI + "/" + result;
                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.parse(resultString);
            default:
                break;
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SINGLE_ALBERGO:
                table = TableHelperAlberghi.TABLE_NAME;
                query = TableHelperAlberghi._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;
            case ALL_ALBERGHI:
                table = TableHelperAlberghi.TABLE_NAME;
                query = selection;
                break;
        }
        int deletedRows = db.delete(table, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SINGLE_ALBERGO:
                table = TableHelperAlberghi.TABLE_NAME;
                query = TableHelperAlberghi._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;
            case ALL_ALBERGHI:
                table = TableHelperAlberghi.TABLE_NAME;
                query = selection;
                break;
        }
        int updatedRows = db.update(table, values, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updatedRows;
    }
}
