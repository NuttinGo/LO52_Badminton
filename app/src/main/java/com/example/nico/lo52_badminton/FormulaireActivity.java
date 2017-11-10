package com.example.nico.lo52_badminton;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

public class FormulaireActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    boolean chgt =false;
    int idAchat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Button b = (Button) findViewById(R.id.B_valider);

        List<String> tubes = new ArrayList<String>();
        setTitle("Ajout/Modification achat");

        final Intent intent = getIntent();

        if(intent.getExtras()==null) {
            spinner.setOnItemSelectedListener(this);
            ProduitManager produitManager = new ProduitManager(this);
            TubeManager tubeManager = new TubeManager(this);
            Tube t;
            Cursor cursorTube;

            produitManager.open();
            Cursor cursorProduit = produitManager.getProduits();

            tubeManager.open();

            while (cursorProduit.moveToNext()) {
                t = tubeManager.getTube(cursorProduit.getInt(1));
                Log.i("Cursor", cursorProduit.getInt(1) + "");
                tubes.add(t.getNom_tube());
            }

            produitManager.close();
            tubeManager.close();

        }else{
            TubeManager tubeManager = new TubeManager(this);
            tubeManager.open();
            tubes.add(intent.getStringExtra("produit"));
            tubeManager.close();

            findViewById(R.id.spinner).setEnabled(false);

            ((EditText)findViewById(R.id.editText)).setText(intent.getStringExtra("quantite"));
            findViewById(R.id.editText).setEnabled(false);

            ((EditText)findViewById(R.id.editText2)).setText(intent.getStringExtra("acheteur"));
            findViewById(R.id.editText2).setEnabled(false);

            CheckBox c = (CheckBox) findViewById(R.id.checkBox);
            Log.i("bool",intent.getBooleanExtra("estPayee",false)+"");
            if(intent.getBooleanExtra("estPayee",false)==true)
                c.setChecked(true);
            c.setOnClickListener(this);
        }

        ArrayAdapter<String> dataAdapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tubes);
        spinner.setAdapter(dataAdapterSpinner);
        b.setOnClickListener(this);

    }

    @Override
    protected void onResume() {

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> tubes = new ArrayList<String>();
        spinner.setOnItemSelectedListener(this);
        ProduitManager produitManager = new ProduitManager(this);
        TubeManager tubeManager = new TubeManager(this);
        Tube t;
        Cursor cursorTube;

        produitManager.open();
        Cursor cursorProduit = produitManager.getProduits();

        tubeManager.open();

        while (cursorProduit.moveToNext()) {
            t = tubeManager.getTube(cursorProduit.getInt(1));
            Log.i("Cursor", cursorProduit.getInt(1) + "");
            tubes.add(t.getNom_tube());
        }

        produitManager.close();
        tubeManager.close();
        super.onResume();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.B_valider:
                if(getIntent().getExtras()!=null && chgt) {
                    AchatManager achatManager = new AchatManager(this);
                    achatManager.open();
                    Log.i("ID achat",getIntent().getIntExtra("idAchat",0)+"");
                    achatManager.modPayeAchat(getIntent().getIntExtra("idAchat",0));
                    achatManager.close();
                    FormulaireActivity.this.finish();
                }

            case R.id.checkBox:
                //Si tu l'utilisateur appuie sur
                chgt=!chgt;

        }
    }
}
