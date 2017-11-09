package com.example.nico.lo52_badminton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Valentin on 27/10/2017.
 */

public class AchatActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        setContentView(R.layout.achat);

        //overridePendingTransition(,);

        setTitle("LardeSports");

        MySQLite dbHelper= MySQLite.getInstance(this);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem achatItem = menu.findItem(R.id.action_achat);
        achatItem.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_stock:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
