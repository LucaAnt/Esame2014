package com.trieste.mobile.esame2014alberghi.ui.elencoalberghi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.trieste.mobile.esame2014alberghi.R;
import com.trieste.mobile.esame2014alberghi.contentprovider.ProviderAlberghi;
import com.trieste.mobile.esame2014alberghi.model.Albergo;
import com.trieste.mobile.esame2014alberghi.ui.dettaglioalberghi.ActivityDettaglioValutazioni;

import java.util.LinkedList;
import java.util.List;

import static com.trieste.mobile.esame2014alberghi.ui.dettaglioalberghi.ActivityDettaglioValutazioni.*;

public class ActivityElencoValutazioni extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, DialogFragmentDeleteConfirmation.ConfirmDialogFragmentListener {


    public static final String MODE_DETAIL_ACTIVITY = "MODE_DETAIL_ACTIVITY";
    public static final String ID_MODE_UPDATE_DETAIL_ACTIVITY = "ID_MODE_UPDATE_DETAIL_ACTIVITY";
    private static int LOADER_ID = 1;
    ListView listAlberghi;
    AdapterCursorElencoAlberghi adapterAlberghi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elenco_valutazioni);
        listAlberghi = findViewById(R.id.tableViewAlberghi);
        adapterAlberghi = new AdapterCursorElencoAlberghi(this,getContentResolver().query(ProviderAlberghi.ALBERGHI_URI,null,null,null,null));
        listAlberghi.setAdapter(adapterAlberghi);
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        setupListeners();

//        //PROVIDER / DATABASE TEST METHODS
//        List<Albergo> alberghi = new LinkedList<>();
//        alberghi.add(new Albergo("Ristoro degli dei","Milano",5,100));
//        alberghi.add(new Albergo("Ristoro dei poveri","Roma",1,10));
//        alberghi.add(new Albergo("Ristoro","Ancona",3,50));
//
//        for (Albergo a:alberghi)
//        {
//        //INSERT/CREATE
//            getContentResolver().insert(ProviderAlberghi.ALBERGHI_URI, a.toContentValues());
//        }

        //UPDATE
        //getContentResolver().update(Uri.withAppendedPath(ProviderAlberghi.ALBERGHI_URI,11+""),(new Albergo("ALBERGO MODIFICATO","LECCE",0,0)).toContentValues(),null,null);

        //DELETE
        //getContentResolver().delete(Uri.withAppendedPath(ProviderAlberghi.ALBERGHI_URI,11+""),null,null);

        //READ
//        Cursor c = getContentResolver().query(ProviderAlberghi.ALBERGHI_URI,null,null,null,null);
//        while (c.moveToNext())
//            Log.d("Albergo letto:", (new Albergo(c)).toString());
    }

    public void setupListeners()
    {
        listAlberghi.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogFragmentDeleteConfirmation dialog = new DialogFragmentDeleteConfirmation("ATTENZIONE","Sei sicuro di voler cancellare l'albergo ?",id);
                dialog.show(getSupportFragmentManager(),null);
                return true;
            }
        });

        listAlberghi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchDetailActivity(ModeDettaglio.UPDATE.ordinal(),id);
            }
        });

        Button b = findViewById(R.id.buttonAggiungiAlbergo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDetailActivity(ModeDettaglio.CREATE.ordinal(),-1);
            }
        });
    }

    public void launchDetailActivity(int mode, long id)
    {
        Intent i = new Intent(this, ActivityDettaglioValutazioni.class);
        i.putExtra(MODE_DETAIL_ACTIVITY,mode);
        if (mode == ModeDettaglio.UPDATE.ordinal())
            i.putExtra(ID_MODE_UPDATE_DETAIL_ACTIVITY,id);
        startActivity(i);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, ProviderAlberghi.ALBERGHI_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapterAlberghi.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapterAlberghi.changeCursor(null);
    }

    @Override
    public void onNegativePressed() {
        Toast.makeText(this,"Nessun elemento cancellato...",Toast.LENGTH_LONG);
    }

    @Override
    public void onPositivePressed(long id) {
        getContentResolver().delete(Uri.withAppendedPath(ProviderAlberghi.ALBERGHI_URI,id+""),null,null);
    }
}
