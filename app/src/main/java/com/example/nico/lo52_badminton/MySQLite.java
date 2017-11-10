package com.example.nico.lo52_badminton;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Nico on 29/09/2017.
 */

public class MySQLite extends SQLiteOpenHelper{


    private static final String DATABASE_NAME = "db.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static MySQLite sInstance;

    public static synchronized MySQLite getInstance(Context context){
        if(sInstance == null){
            sInstance= new MySQLite(context);
        }
        return sInstance;
    }

    private MySQLite(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ClientManager.CREATE_TABLE_CLIENT);
        db.execSQL(MarqueManager.CREATE_TABLE_MARQUE);
        db.execSQL(DistributeurManager.CREATE_TABLE_DISTRIBUTEUR);
        db.execSQL(TubeManager.CREATE_TABLE_TUBE);
        db.execSQL(ProduitManager.CREATE_TABLE_PRODUIT);
        db.execSQL(AchatManager.CREATE_TABLE_ACHAT);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + ClientManager.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + MarqueManager.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + DistributeurManager.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + TubeManager.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + ProduitManager.TABLE_NAME + ";");
        db.execSQL("DROP TABLE " + AchatManager.TABLE_NAME + ";");
        onCreate(db);
    }
}
