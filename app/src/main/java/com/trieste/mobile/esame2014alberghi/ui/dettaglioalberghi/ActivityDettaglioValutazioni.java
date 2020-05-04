package com.trieste.mobile.esame2014alberghi.ui.dettaglioalberghi;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.trieste.mobile.esame2014alberghi.R;
import com.trieste.mobile.esame2014alberghi.contentprovider.ProviderAlberghi;
import com.trieste.mobile.esame2014alberghi.model.Albergo;
import com.trieste.mobile.esame2014alberghi.ui.elencoalberghi.ActivityElencoValutazioni;

public class ActivityDettaglioValutazioni extends AppCompatActivity {

    EditText editNome,editCitta,editCosto;
    RatingBar ratingBarValutazione;
    ModeDettaglio mode;
    long id=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dettaglio_valutazioni);
        editNome = findViewById(R.id.editTextNome);
        editCitta = findViewById(R.id.editTextCitta);
        editCosto = findViewById(R.id.editTextCosto);
        ratingBarValutazione = findViewById(R.id.ratingBarStelle);

        mode = ModeDettaglio.values()[getIntent().getIntExtra(ActivityElencoValutazioni.MODE_DETAIL_ACTIVITY,0)];

        switch (mode)
        {
            case CREATE:
                Toast.makeText(this,"MODE "+ mode.name(),Toast.LENGTH_LONG).show();
                break;
            case UPDATE:
                Toast.makeText(this,"MODE "+ mode.name(),Toast.LENGTH_LONG).show();
                id = getIntent().getLongExtra(ActivityElencoValutazioni.ID_MODE_UPDATE_DETAIL_ACTIVITY,-1);
                try {
                    Cursor currentAlbergo =  getContentResolver().query(Uri.withAppendedPath(ProviderAlberghi.ALBERGHI_URI,id+""),null,null,null,null);
                    currentAlbergo.moveToNext();
                    setFields(new Albergo(currentAlbergo));
                }catch (Exception e){
                    Toast.makeText(this,"Albergo non trovato",Toast.LENGTH_LONG).show();
                }

                break;
            default:
                finish();
            break;
        }

    }

    public Albergo getFields()
    {
        return new Albergo(
                editNome.getText().toString(),
                editCitta.getText().toString(),
                Math.round(ratingBarValutazione.getRating()),
                Integer.parseInt( editCosto.getText().toString())

        );
    }

    public void setFields(Albergo albergo)
    {
        editNome.setText(albergo.getNome());
        editCitta.setText(albergo.getCitta());
        editCosto.setText(albergo.getCosto() + "");
        ratingBarValutazione.setRating(albergo.getStelle());
    }

    public void annullaInserimento(View view) {
        finish();
    }

    public void confermaInserimento(View view) {
        switch (mode)
        {
            case CREATE:
                getContentResolver().insert(ProviderAlberghi.ALBERGHI_URI, getFields().toContentValues());
                break;
            case UPDATE:
                getContentResolver().update(Uri.withAppendedPath(ProviderAlberghi.ALBERGHI_URI,id+""),getFields().toContentValues(),null,null);
                break;
            default:
                break;
        }
        finish();
    }


    public enum ModeDettaglio
    {
        CREATE,UPDATE;
    }
}
