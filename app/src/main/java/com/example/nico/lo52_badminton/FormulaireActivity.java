package com.example.nico.lo52_badminton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class FormulaireActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setTitle("Ajout/Modification achat");
    }

}
