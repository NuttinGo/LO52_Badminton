package com.example.nico.lo52_badminton;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
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


public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private int counter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.activity_main);

        setTitle("LardeSports - Stock");

        mListView = (ListView) findViewById(R.id.listView);

        MySQLite dbHelper= MySQLite.getInstance(this);


        ClientManager c = new ClientManager(this);
        TubeManager tubeManager= new TubeManager(this);
        MarqueManager marqueManager = new MarqueManager(this);
        DistributeurManager distributeurManager = new DistributeurManager(this);
        ProduitManager produitManager = new ProduitManager(this);
        AchatManager achatManager = new AchatManager(this);

        c.open();

        c.addClient(new Client(1,"Jean-Claude"));
        c.addClient(new Client(2,"Jean-Kevin"));
        c.addClient(new Client(3,"Yurtag"));
        c.close();

        marqueManager.open();
        marqueManager.addMarque(new Marque(1,"Yonex"));
        marqueManager.addMarque(new Marque(2,"RSL"));
        marqueManager.close();
        distributeurManager.open();
        distributeurManager.addDistributeur(new Distributeur(1,"ComateuxNumérique"));
        distributeurManager.close();

        tubeManager.open();
        tubeManager.addTube(new Tube(1,1,0,"AS30","infini","aerosensa"));
        tubeManager.addTube(new Tube(2,2,0,"Grade 3","infini","rsl_blanc"));
        tubeManager.addTube(new Tube(3,2,0,"Grade A9","infini","rsl"));
        tubeManager.addTube(new Tube(4,2,0,"Grade A1","infini","rsla"));
        tubeManager.close();

        produitManager.open();
        produitManager.addProduit(new Produit(1,1,1,27,500));
        produitManager.addProduit(new Produit(2,1,2,(float)16.70,5000));
        produitManager.addProduit(new Produit(3,1,3,(float)13.70,10000));
        produitManager.addProduit(new Produit(4,1,4,21,6000));
        produitManager.close();

        achatManager.open();
        achatManager.addAchat(new Achat(1,1,2,5,1,"28/10/2017"));
        achatManager.addAchat(new Achat(2,1,4,1,0,"02/11/2017"));
        achatManager.addAchat(new Achat(3,1,1,2,1,"05/03/2017"));
        achatManager.addAchat(new Achat(4,2,1,2,0,"05/06/2017"));
        achatManager.addAchat(new Achat(5,2,3,30,1,"07/01/2016"));
        achatManager.close();

        List<Volant> volants = genererVolants();

        VolantAdapter adapter = new VolantAdapter(MainActivity.this, volants);
        mListView.setAdapter(adapter);

        Log.i("MyAPP","Database OK");
        Log.i("MyAPP",dbHelper.getDatabaseName());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem stockItem = menu.findItem(R.id.action_stock);
        stockItem.setVisible(false);

        MenuItem formulaireItem = menu.findItem(R.id.action_formulaire);
        formulaireItem.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_achat:
                Intent intent = new Intent(this,AchatActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public class Volant {
        private int display;
        private String nom;
        private String description;
        private String stock;

        public Volant(int display, String nom, String description, String stock) {
            this.display = display;
            this.nom = nom;
            this.description = description;
            this.stock = stock;
        }

        public String getNom(){
            return this.nom;
        }
        public String getDescription(){
            return this.description;
        }
        public int getDisplay(){
            return this.display;
        }
        public String getStock(){
            return this.stock;
        }
    }

    public class VolantAdapter extends ArrayAdapter<Volant>{
        public VolantAdapter(Context context, List<Volant> volants) {
            super(context, 0, volants);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_layout, parent, false);
            }

            VolantViewHolder viewHolder = (VolantViewHolder) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new VolantViewHolder();
                viewHolder.nom = (TextView) convertView.findViewById(R.id.nom);
                viewHolder.description = (TextView) convertView.findViewById(R.id.description);
                viewHolder.display = (ImageView) convertView.findViewById(R.id.display);
                viewHolder.stock = (TextView) convertView.findViewById(R.id.stock);
                convertView.setTag(viewHolder);
            }

            Volant volant = getItem(position);

            viewHolder.nom.setText(volant.getNom());
            viewHolder.description.setText(volant.getDescription());
            viewHolder.display.setImageResource(volant.getDisplay());
            viewHolder.stock.setText("Stock : " + volant.getStock());

            return convertView;
        }

        private class VolantViewHolder{
            public TextView nom;
            public TextView description;
            public ImageView display;
            public TextView stock;
        }
    }

    private List<Volant> genererVolants(){
        //Chercher dans BDD
        List<Volant> volants = new ArrayList<Volant>();

        ProduitManager produitManager = new ProduitManager(this);
        TubeManager tubeManager = new TubeManager(this);
        MarqueManager marqueManager = new MarqueManager(this);
        DistributeurManager distributeurManager = new DistributeurManager(this);
        Tube t;
        Cursor cursorTube;

        produitManager.open();
        tubeManager.open();
        marqueManager.open();
        distributeurManager.open();
        Cursor cursorProduit = produitManager.getProduits();


        while(cursorProduit.moveToNext()){
            t= tubeManager.getTube(cursorProduit.getInt(2));
            //Log.i("Cursor",cursorProduit.getInt(1)+"");
            volants.add(new Volant(getResources().getIdentifier(t.getNomImage_tube(),"drawable",getPackageName()),distributeurManager.getDistributeur(cursorProduit.getInt(1)).getNom_distributeur(),marqueManager.getMarque(t.getId_Marque()).getNom_marque()+" - "+t.getNom_tube(),cursorProduit.getString(4)));
        }

        produitManager.close();
        tubeManager.close();
        marqueManager.close();
        distributeurManager.close();

        return volants;
    }
}
