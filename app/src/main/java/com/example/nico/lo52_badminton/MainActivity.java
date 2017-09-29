package com.example.nico.lo52_badminton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
