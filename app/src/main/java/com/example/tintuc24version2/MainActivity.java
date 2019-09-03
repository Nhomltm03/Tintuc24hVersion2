package com.example.tintuc24version2;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.tintuc24version2.FragmentAdapter.SectionsPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private  NewsFragment fragment = new NewsFragment();
    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager = getSupportFragmentManager();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    NewsFragment newsFragment = new NewsFragment();
                    manager.beginTransaction().replace(R.id.container, newsFragment, newsFragment.getTag()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    WeatherFragment weatherFragment = new WeatherFragment();
                    manager.beginTransaction().replace(R.id.container, weatherFragment, weatherFragment.getTag()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(fragment);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}
