package com.draos.nekretnine.nekretnineui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements
        SearchFragment.OnFragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        FavouritesFragment.OnFragmentInteractionListener,
        AccountFragment.OnFragmentInteractionListener,
        AdvertiseFragment.OnFragmentInteractionListener{

    private ActionBar toolbar;
    private BottomNavigationView navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        toolbar = getSupportActionBar();
        toolbar.setTitle("Home");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        fragmentTransaction.commit();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        toolbar.setTitle("Home");
                        fragment = HomeFragment.newInstance();
                        break;
                    case R.id.navigation_search:
                        toolbar.setTitle("Search");
                        fragment = SearchFragment.newInstance();
                        break;
                    case R.id.navigation_favourites:
                        toolbar.setTitle("Favourites");
                        fragment = FavouritesFragment.newInstance();
                        break;
                    case R.id.navigation_account:
                        toolbar.setTitle("Account");
                        fragment = AccountFragment.newInstance();
                        break;
                }

                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, fragment);
                    fragmentTransaction.commit();
                }

                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(navigation.getSelectedItemId() == R.id.navigation_home){
            super.onBackPressed();
        }else{
            navigation.setSelectedItemId(R.id.navigation_home);
            toolbar.setTitle("Home");
        }
    }
}