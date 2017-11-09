package com.example.nico.lo52_badminton;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentin on 27/10/2017.
 */

public class AchatActivity extends AppCompatActivity{

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.achat);

        setTitle("LardeSports - Achat");

        mListView = (ListView) findViewById(R.id.listView);

        List<Vente> ventes = genererVentes();

        VenteAdapter adapter = new VenteAdapter(AchatActivity.this, ventes);
        mListView.setAdapter(adapter);

        MySQLite dbHelper= MySQLite.getInstance(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem achatItem = menu.findItem(R.id.action_achat);
        achatItem.setVisible(false);


        MenuItem formulaireItem = menu.findItem(R.id.action_formulaire);
        formulaireItem.setVisible(true);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_stock:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
            case R.id.action_formulaire:
                Intent intent2 = new Intent(this,FormulaireActivity.class);
                startActivity(intent2);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class Vente {
        private String nomAcheteur;
        private String prix;
        private String quantite;
        private String reference;
        private Boolean paye;

        public Vente (String nomAcheteur, String prix, String quantite, String reference, Boolean paye) {
            this.nomAcheteur = nomAcheteur;
            this.prix = prix;
            this.quantite = quantite;
            this.reference = reference;
            this.paye = paye;
        }

        public String getNomAcheteur(){
            return this.nomAcheteur;
        }
        public String getPrix(){
            return this.prix;
        }
        public String getQuantite(){
            return this.quantite;
        }
        public String getReference(){
            return this.reference;
        }
        public Boolean getPaye(){
            return this.paye;
        }
    }

    public class VenteAdapter extends ArrayAdapter<Vente> {
        public VenteAdapter(Context context, List<Vente> ventes) {
            super(context, 0, ventes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.achat_layout, parent, false);
            }

            VenteViewHolder viewHolder = (VenteViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new VenteViewHolder();
                viewHolder.nomAcheteur = (TextView) convertView.findViewById(R.id.nomAcheteur);
                viewHolder.prix = (TextView) convertView.findViewById(R.id.prix);
                viewHolder.quantite = (TextView) convertView.findViewById(R.id.quantite);
                viewHolder.reference = (TextView) convertView.findViewById(R.id.reference);
                convertView.setTag(viewHolder);
            }

            Vente vente = getItem(position);

            if(vente.getPaye()==Boolean.TRUE)
            {
                viewHolder.prix.setTextColor(Color.rgb(0, 177, 0));
            }else{
                viewHolder.prix.setTextColor(Color.RED);
            }

            viewHolder.nomAcheteur.setText(vente.getNomAcheteur());
            viewHolder.prix.setText("Prix : " +vente.getPrix());
            viewHolder.quantite.setText("Quantité : " +vente.getQuantite());
            viewHolder.reference.setText("Référence : " + vente.getReference());

            return convertView;
        }

        private class VenteViewHolder{
            public TextView nomAcheteur;
            public TextView prix;
            public TextView quantite;
            public TextView reference;
        }
    }

    private List<Vente> genererVentes(){
        List<Vente> ventes = new ArrayList<Vente>();
        ventes.add(new Vente("Nicolas CARRION","35 €", "7", "5874A52F",Boolean.TRUE));
        ventes.add(new Vente("Florian STAINE","5 €", "1", "6984615G",Boolean.FALSE));
        ventes.add(new Vente("Cédric WELTY","55 €", "9", "321EF651",Boolean.TRUE));
        ventes.add(new Vente("Guillaume BOURRIOT","34 €", "3", "SD12684P",Boolean.TRUE));
        ventes.add(new Vente("Antoine COUPAT","48 €", "7", "DL469CM0",Boolean.FALSE));
        ventes.add(new Vente("Florian BRUNSTEIN","3 €", "1", "3465XZ68",Boolean.FALSE));
        return ventes;
    }

}
