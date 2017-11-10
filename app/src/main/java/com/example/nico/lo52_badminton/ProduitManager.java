package com.example.nico.lo52_badminton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nico on 29/09/2017.
 */

public class ProduitManager {
    public static final String TABLE_NAME = "produit";
    public static final String KEY_ID_PRODUIT="id_produit";
    public static final String KEY_ID_DISTRIBUTEUR="id_distributeur";
    public static final String KEY_ID_TUBE="id_tube";
    public static final String KEY_PRIX_PRODUIT="prix_produit";
    public static final String KEY_STOCK_PRODUIT="stock_produit";
    public static final String CREATE_TABLE_PRODUIT = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_PRODUIT+" INTEGER PRIMARY KEY," +
            " "+KEY_ID_DISTRIBUTEUR+" INTEGER REFERENCES distributeur(id_distributeur) ON UPDATE CASCADE," +
            " "+KEY_ID_TUBE+" INTEGER REFERENCES tube(id_tube) ON UPDATE CASCADE,"+
            " "+KEY_PRIX_PRODUIT+" INTEGER," +
            " "+KEY_STOCK_PRODUIT+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public ProduitManager(Context context)
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

    public long addProduit(Produit produit) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_PRODUIT, produit.getId_produit());
        values.put(KEY_ID_DISTRIBUTEUR, produit.getId_distributeur());
        values.put(KEY_ID_TUBE, produit.getId_tube());
        values.put(KEY_PRIX_PRODUIT, produit.getPrix_produit());
        values.put(KEY_STOCK_PRODUIT, produit.getStock_produit());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modProduit(Produit produit) {
        // modification d'un enrecistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_PRODUIT, produit.getId_produit());
        values.put(KEY_ID_DISTRIBUTEUR, produit.getId_distributeur());
        values.put(KEY_ID_TUBE, produit.getId_tube());
        values.put(KEY_PRIX_PRODUIT, produit.getPrix_produit());
        values.put(KEY_STOCK_PRODUIT, produit.getStock_produit());

        String where = KEY_ID_PRODUIT+" = ?";
        String[] whereArgs = {produit.getId_distributeur()+"",produit.getId_tube()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supProduit(Produit produit) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_PRODUIT+" = ?";
        String[] whereArgs = {produit.getId_distributeur()+"",produit.getId_tube()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Produit getProduit(int idProd) {
        // Retourne l'Produit dont l'id est passé en paramètre

        Produit a=new Produit(0,0,0,0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_PRODUIT+"="+idProd, null);
        if (c.moveToFirst()) {
            a.setId_produit(c.getInt(c.getColumnIndex(KEY_ID_PRODUIT)));
            a.setId_distributeur(c.getInt(c.getColumnIndex(KEY_ID_DISTRIBUTEUR)));
            a.setId_tube(c.getInt(c.getColumnIndex(KEY_ID_TUBE)));
            a.setPrix_produit(c.getFloat(c.getColumnIndex(KEY_PRIX_PRODUIT)));
            a.setStock_produit(c.getInt(c.getColumnIndex(KEY_STOCK_PRODUIT)));
            c.close();
        }

        return a;
    }

    public Cursor getProduits() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }


}
