package com.example.nico.lo52_badminton;

import android.content.Context;
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
    private MenuItem stockItem;
    private MenuItem achatItem;

    /*private String[] volants = new String[]{
            "Yonex", "Grade 3", "Grade A9"
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("LardeSports");

        mListView = (ListView) findViewById(R.id.listView);

        /*final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, volants);
        mListView.setAdapter(adapter);*/
        List<Volant> volants = genererVolants();

        VolantAdapter adapter = new VolantAdapter(MainActivity.this, volants);
        mListView.setAdapter(adapter);

        MySQLite dbHelper= MySQLite.getInstance(this);

        //dbHelper.onCreate(MySQLite.getInstance(this).getWritableDatabase());

        /*ClientManager c = new ClientManager(this);
        c.open();

        c.addClient(new Client(1,"Jean-Claude"));

        Cursor curs = c.getClients();
        if (curs.moveToFirst())
        {
            do {
                Log.d("test",
                        curs.getInt(curs.getColumnIndex(ClientManager.KEY_ID_CLIENT)) + "," +
                                curs.getString(curs.getColumnIndex(ClientManager.KEY_NOM_CLIENT))
                );
            }
            while (curs.moveToNext());
        }
        c.close(); // fermeture du curseur

// fermeture du gestionnaire
        c.close();*/

        Log.i("MyAPP","Database OK");
        Log.i("MyAPP",dbHelper.getDatabaseName());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        stockItem = menu.findItem(R.id.action_stock);
        achatItem = menu.findItem(R.id.action_achat);
        stockItem.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_stock:
                setContentView(R.layout.activity_main);
                item.setVisible(false);
                achatItem.setVisible(true);
                Log.i("MyAPP","Stock");
                return true;
            case R.id.action_achat:
                setContentView(R.layout.achat);
                item.setVisible(false);
                stockItem.setVisible(true);
                Log.i("MyAPP","Achat");
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
        List<Volant> volants = new ArrayList<Volant>();
        volants.add(new Volant(getResources().getIdentifier("ic_launcher", "mipmap", getPackageName()), "Yonex", "Référence Yonex", "100/150"));
        volants.add(new Volant(getResources().getIdentifier("ic_launcher", "mipmap", getPackageName()), "Grade 3", "Référence Grade 3", "320/400"));
        volants.add(new Volant(getResources().getIdentifier("ic_launcher", "mipmap", getPackageName()), "Grade A9", "Référence Grade A9", "8/1000"));
        volants.add(new Volant(getResources().getIdentifier("aerosensa", "drawable", getPackageName()), "Yonex", "Référence Yonex", "100/150"));
        volants.add(new Volant(getResources().getIdentifier("rsl", "drawable", getPackageName()), "Grade 3", "Référence Grade 3", "320/400"));
        volants.add(new Volant(getResources().getIdentifier("rsl_blanc", "drawable", getPackageName()), "Grade A9", "Référence Grade A9", "8/1000"));
        return volants;
    }
}
