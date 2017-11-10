package com.example.nico.lo52_badminton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nico on 29/09/2017.
 */

public class ClientManager {
    public static final String TABLE_NAME = "client";
    public static final String KEY_ID_CLIENT="id_client";
    public static final String KEY_NOM_CLIENT="nom_client";
    public static final String CREATE_TABLE_CLIENT = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_CLIENT+" INTEGER primary key," +
            " "+KEY_NOM_CLIENT+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public ClientManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open()
    {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addClient(Client client) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_CLIENT, client.getNom_client());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modClient(Client client) {
        // modification d'un enrecistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_CLIENT, client.getNom_client());

        String where = KEY_ID_CLIENT+" = ?";
        String[] whereArgs = {client.getId_client()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supClient(Client client) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_CLIENT+" = ?";
        String[] whereArgs = {client.getId_client()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Client getClient(int id) {
        // Retourne l'Client dont l'id est passé en paramètre

        Client a=new Client(0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_CLIENT+"="+id, null);
        if (c.moveToFirst()) {
            a.setId_client(c.getInt(c.getColumnIndex(KEY_ID_CLIENT)));
            a.setNom_client(c.getString(c.getColumnIndex(KEY_NOM_CLIENT)));
            c.close();
        }

        return a;
    }

    public Client getClient(String s) {
        // Retourne l'Client dont l'id est passé en paramètre

        Client a=new Client(0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_NOM_CLIENT+"="+s, null);
        if (c.moveToFirst()) {
            a.setId_client(c.getInt(c.getColumnIndex(KEY_ID_CLIENT)));
            a.setNom_client(c.getString(c.getColumnIndex(KEY_NOM_CLIENT)));
            c.close();
        }

        return a;
    }

    public Cursor getClients() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

    public void init(){

    }

}
