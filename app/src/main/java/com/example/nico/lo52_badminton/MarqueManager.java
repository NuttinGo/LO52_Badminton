package com.example.nico.lo52_badminton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nico on 29/09/2017.
 */

public class MarqueManager {
    private static final String TABLE_NAME = "marque";
    public static final String KEY_ID_MARQUE="id_marque";
    public static final String KEY_NOM_MARQUE="nom_marque";
    public static final String CREATE_TABLE_MARQUE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_MARQUE+" INTEGER primary key," +
            " "+KEY_NOM_MARQUE+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public MarqueManager(Context context)
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

    public long addMarque(Marque marque) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_MARQUE, marque.getNom_marque());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modMarque(Marque marque) {
        // modification d'un enrecistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_MARQUE, marque.getNom_marque());

        String where = KEY_ID_MARQUE+" = ?";
        String[] whereArgs = {marque.getId_marque()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supMarque(Marque marque) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_MARQUE+" = ?";
        String[] whereArgs = {marque.getId_marque()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Marque getMarque(int id) {
        // Retourne l'Marque dont l'id est passé en paramètre

        Marque a=new Marque(0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_MARQUE+"="+id, null);
        if (c.moveToFirst()) {
            a.setId_marque(c.getInt(c.getColumnIndex(KEY_ID_MARQUE)));
            a.setNom_marque(c.getString(c.getColumnIndex(KEY_NOM_MARQUE)));
            c.close();
        }

        return a;
    }

    public Cursor getMarques() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}
