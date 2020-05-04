package com.trieste.mobile.esame2014alberghi.ui.elencoalberghi;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.CursorAdapter;

import com.trieste.mobile.esame2014alberghi.R;
import com.trieste.mobile.esame2014alberghi.model.Albergo;

public class AdapterCursorElencoAlberghi extends CursorAdapter {

    public AdapterCursorElencoAlberghi(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.cell_elenco_alberghi,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Albergo a = new Albergo(cursor);
        TextView nome = view.findViewById(R.id.cellTextViewNome);
        TextView citta = view.findViewById(R.id.cellTextViewCitta);
        TextView stelle = view.findViewById(R.id.cellTextViewStelle);
        TextView costo = view.findViewById(R.id.cellTextViewPrezzo);

        nome.setText(a.getNome());
        citta.setText(a.getCitta());
        stelle.setText(a.getStelle()+" Stelle");
        costo.setText("€ " + a.getCosto());
    }
}
