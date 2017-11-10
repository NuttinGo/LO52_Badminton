package com.example.nico.lo52_badminton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Nico on 29/09/2017.
 */

public class TubeManager {

    public static final String TABLE_NAME = "tube";
    public static final String KEY_ID_TUBE="id_tube";
    public static final String KEY_MARQUE_TUBE="id_marque";
    public static final String KEY_CLASSEMENT_TUBE="classement_fed_tube";
    public static final String KEY_NOM_TUBE="nom_tube";
    public static final String KEY_VALIDITE_TUBE="validite_tube";
    public static final String KEY_IMAGE_TUBE="nom_image_tube";
    public static final String CREATE_TABLE_TUBE = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_TUBE+" INTEGER primary key," +
            " "+KEY_MARQUE_TUBE+" INTEGER REFERENCES marque(id_marque) ON UPDATE CASCADE,"+
            " "+KEY_CLASSEMENT_TUBE+" INTEGER," +
            " "+KEY_NOM_TUBE+" TEXT," +
            " "+KEY_VALIDITE_TUBE+" TEXT," +
            " "+KEY_IMAGE_TUBE+" TEXT" +
            ");";
    private MySQLite maBaseSQLite; // notre gestionnaire du fichier SQLite
    private SQLiteDatabase db;

    // Constructeur
    public TubeManager(Context context)
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

    public long addTube(Tube tube) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TUBE, tube.getId_tube());
        values.put(KEY_MARQUE_TUBE, tube.getId_Marque());
        values.put(KEY_CLASSEMENT_TUBE, tube.getClassementFed_tube());
        values.put(KEY_NOM_TUBE, tube.getNom_tube());
        values.put(KEY_VALIDITE_TUBE, tube.getValidite_tube());
        values.put(KEY_IMAGE_TUBE, tube.getNomImage_tube());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int modTube(Tube tube) {
        // modification d'un enrecistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_TUBE, tube.getId_tube());
        values.put(KEY_MARQUE_TUBE, tube.getId_Marque());
        values.put(KEY_CLASSEMENT_TUBE, tube.getClassementFed_tube());
        values.put(KEY_NOM_TUBE, tube.getNom_tube());
        values.put(KEY_VALIDITE_TUBE, tube.getValidite_tube());
        values.put(KEY_IMAGE_TUBE, tube.getNomImage_tube());

        String where = KEY_ID_TUBE+" = ?";
        String[] whereArgs = {tube.getId_tube()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int supTube(Tube tube) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_TUBE+" = ?";
        String[] whereArgs = {tube.getId_tube()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Tube getTube(int id) {
        // Retourne l'Tube dont l'id est passé en paramètre

        Tube a=new Tube(0,0,0,"","","");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_TUBE+"="+id, null);
        if (c.moveToFirst()) {
            a.setId_tube(c.getInt(c.getColumnIndex(KEY_ID_TUBE)));
            a.setId_Marque(c.getInt(c.getColumnIndex(KEY_MARQUE_TUBE)));
            a.setClassementFed_tube(c.getInt(c.getColumnIndex(KEY_CLASSEMENT_TUBE)));
            a.setNom_Tube(c.getString(c.getColumnIndex(KEY_NOM_TUBE)));
            a.setValidite_tube(c.getString(c.getColumnIndex(KEY_VALIDITE_TUBE)));
            a.setNomImage_Tube(c.getString(c.getColumnIndex(KEY_IMAGE_TUBE)));
            c.close();
        }

        return a;
    }

    public Cursor getTubes() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}
