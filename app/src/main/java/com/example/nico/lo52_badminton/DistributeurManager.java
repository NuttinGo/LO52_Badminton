package com.example.nico.lo52_badminton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nico on 29/09/2017.
 */

public class DistributeurManager {
    private static final String TABLE_NAME = "distributeur";
    public static final String KEY_ID_DISTRIBUTEUR="id_distributeur";
    public static final String KEY_NOM_DISTRIBUTEUR="nom_distributeur";
    public static final String CREATE_TABLE_DISTRIBUTEUR = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_DISTRIBUTEUR+" INTEGER primary key," +
            " "+KEY_NOM_DISTRIBUTEUR+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public DistributeurManager(Context context)
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

    public long addDistributeur(Distributeur distributeur) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_DISTRIBUTEUR, distributeur.getNom_distributeur());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modDistributeur(Distributeur distributeur) {
        // modification d'un enrecistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_NOM_DISTRIBUTEUR, distributeur.getNom_distributeur());

        String where = KEY_ID_DISTRIBUTEUR+" = ?";
        String[] whereArgs = {distributeur.getId_distributeur()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supDistributeur(Distributeur distributeur) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_DISTRIBUTEUR+" = ?";
        String[] whereArgs = {distributeur.getId_distributeur()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Distributeur getDistributeur(int id) {
        // Retourne l'Distributeur dont l'id est passé en paramètre

        Distributeur a=new Distributeur(0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_DISTRIBUTEUR+"="+id, null);
        if (c.moveToFirst()) {
            a.setId_distributeur(c.getInt(c.getColumnIndex(KEY_ID_DISTRIBUTEUR)));
            a.setNom_distributeur(c.getString(c.getColumnIndex(KEY_NOM_DISTRIBUTEUR)));
            c.close();
        }

        return a;
    }

    public Cursor getDistributeurs() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }


}
