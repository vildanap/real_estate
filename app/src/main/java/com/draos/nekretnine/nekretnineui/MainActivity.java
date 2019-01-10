package com.draos.nekretnine.nekretnineui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView title = (TextView) findViewById(R.id.message);
        title.setText("Home");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        break;
                    case R.id.navigation_search:
                        title.setText("Search");
                        break;
                    case R.id.navigation_favourites:
                        title.setText("Favourites");
                        break;
                    case R.id.navigation_account:
                        Intent b = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(b);
                        break;
                }
                return true;
            }
        });
    }


}
