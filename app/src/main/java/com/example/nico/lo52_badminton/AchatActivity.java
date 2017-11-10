package com.example.nico.lo52_badminton;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valentin on 27/10/2017.
 */

public class AchatActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

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

        mListView.setOnItemClickListener(this);

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("Test",((Vente)parent.getItemAtPosition(position)).idVente+"");
        Intent intent = new Intent ( AchatActivity.this,FormulaireActivity.class);
        intent.putExtra("idAchat",((Vente)parent.getItemAtPosition(position)).idVente);
        intent.putExtra("produit",((Vente)parent.getItemAtPosition(position)).reference);
        intent.putExtra("quantite",((Vente)parent.getItemAtPosition(position)).quantite);
        intent.putExtra("acheteur",((Vente)parent.getItemAtPosition(position)).nomAcheteur);
        intent.putExtra("estPayee",((Vente)parent.getItemAtPosition(position)).paye);
        startActivityForResult(intent,0);
    }

    public class Vente {
        public int idVente;
        private String nomAcheteur;
        private String prix;
        private String quantite;
        private String reference;
        private Boolean paye;

        public Vente (int id,String nomAcheteur, String prix, String quantite, String reference, Boolean paye) {
            this.idVente=id;
            this.nomAcheteur = nomAcheteur;
            this.prix = prix;
            this.quantite = quantite;
            this.reference = reference;
            this.paye = paye;
        }

        public int getIdVente(){
            return this.idVente;
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

        ProduitManager produitManager = new ProduitManager(this);
        TubeManager tubeManager = new TubeManager(this);
        AchatManager achatManager = new AchatManager(this);
        ClientManager clientManager = new ClientManager(this);
        Tube t;
        Cursor cursorTube;

        produitManager.open();
        tubeManager.open();
        clientManager.open();
        achatManager.open();

        Cursor cursorAchats = achatManager.getAchats();

        while(cursorAchats.moveToNext()){
            // client clientManager.getClient(cursorAchats.getInt(1)).get
            t= tubeManager.getTube(cursorAchats.getInt(1));
            //Log.i("Cursor",cursorProduit.getInt(1)+"");
            Produit p = produitManager.getProduit(cursorAchats.getInt(2));
            boolean b =false;
            if(achatManager.getAchat(cursorAchats.getInt(4)).getAchat_effectue()>0)
                b=true;
            ventes.add(new Vente(cursorAchats.getInt(0),clientManager.getClient(cursorAchats.getInt(1)).getNom_client(), p.getPrix_produit()+"",cursorAchats.getInt(3)+"",tubeManager.getTube(p.getId_tube()).getNom_tube(),b));
        }

        produitManager.close();
        tubeManager.close();
        clientManager.close();
        achatManager.close();
        return ventes;
    }

}
