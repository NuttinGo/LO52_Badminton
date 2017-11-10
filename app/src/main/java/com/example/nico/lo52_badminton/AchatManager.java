package com.example.nico.lo52_badminton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nico on 05/10/2017.
 */

public class AchatManager {
    public static final String TABLE_NAME = "achat";
    public static final String KEY_ID_ACHAT="id_achat";
    public static final String KEY_ID_CLIENT="id_client";
    public static final String KEY_ID_PRODUIT="id_produit";
    public static final String KEY_QUANTITE_ACHAT="quantite_achat";
    public static final String KEY_PAIEMENT_EFFECTUE_ACHAT="paiement_effectue_achat";
    public static final String KEY_DATE_ACHAT="date_achat";
    //!!!\\ Pas de type booléen donc Paiement_effectue est un int, false si =0 et true si != 0
    public static final String CREATE_TABLE_ACHAT = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_ACHAT+" INTEGER PRIMARY KEY ," +
            " "+KEY_ID_CLIENT+" INTEGER REFERENCES client(id_client) ON UPDATE CASCADE," +
            " "+KEY_ID_PRODUIT+" INTEGER REFERENCES produit(id_produit) ON UPDATE CASCADE,"+
            " "+KEY_QUANTITE_ACHAT+" INTEGER," +
            " "+KEY_PAIEMENT_EFFECTUE_ACHAT+" INT," +
            " "+KEY_DATE_ACHAT+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public AchatManager(Context context)
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

    public long addAchat(Achat achat) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_ACHAT, achat.getId_achat());
        values.put(KEY_ID_CLIENT, achat.getId_client());
        values.put(KEY_ID_PRODUIT, achat.getId_produit());
        values.put(KEY_QUANTITE_ACHAT, achat.getQuantite_achat());
        values.put(KEY_PAIEMENT_EFFECTUE_ACHAT, achat.getAchat_effectue());
        values.put(KEY_DATE_ACHAT, achat.getDate_achat());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modAchat(Achat achat) {
        // modification d'un enrecistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_ACHAT, achat.getId_achat());
        values.put(KEY_ID_CLIENT, achat.getId_client());
        values.put(KEY_ID_PRODUIT, achat.getId_produit());
        values.put(KEY_QUANTITE_ACHAT, achat.getQuantite_achat());
        values.put(KEY_PAIEMENT_EFFECTUE_ACHAT, achat.getAchat_effectue());
        values.put(KEY_DATE_ACHAT, achat.getDate_achat());

        String where = KEY_ID_ACHAT+" = ?";
        String[] whereArgs = {achat.getId_achat()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int modPayeAchat(int idAchat) {
        // modification d'un enrecistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête
        Achat achat = this.getAchat(idAchat);

        ContentValues values = new ContentValues();
        values.put(KEY_ID_ACHAT, achat.getId_achat());
        values.put(KEY_ID_CLIENT, achat.getId_client());
        values.put(KEY_ID_PRODUIT, achat.getId_produit());
        values.put(KEY_QUANTITE_ACHAT, achat.getQuantite_achat());
        if(achat.getAchat_effectue()==0)
            values.put(KEY_PAIEMENT_EFFECTUE_ACHAT, 1);
        else
            values.put(KEY_PAIEMENT_EFFECTUE_ACHAT, 0);
        values.put(KEY_DATE_ACHAT, achat.getDate_achat());

        String where = KEY_ID_ACHAT+" = ?";
        String[] whereArgs = {achat.getId_achat()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supAchat(Achat achat) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_ACHAT+" = ? ";
        String[] whereArgs = {achat.getId_achat()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Achat getAchat(int idAchat) {
        // Retourne l'Achat dont l'id est passé en paramètre

        Achat a=new Achat(0,0,0,0,0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_ACHAT+"= "+Integer.toString(idAchat), null);
        if (c.moveToFirst()) {
            a.setId_achat(c.getInt(c.getColumnIndex(KEY_ID_ACHAT)));
            a.setId_client(c.getInt(c.getColumnIndex(KEY_ID_CLIENT)));
            a.setId_produit(c.getInt(c.getColumnIndex(KEY_ID_PRODUIT)));
            a.setQuantite_achat(c.getInt(c.getColumnIndex(KEY_QUANTITE_ACHAT)));
            a.setAchat_effectue(c.getInt(c.getColumnIndex(KEY_PAIEMENT_EFFECTUE_ACHAT)));
            a.setDate_achat(c.getString(c.getColumnIndex(KEY_DATE_ACHAT)));
            c.close();
        }

        return a;
    }

    public Cursor getAchats() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }


}
