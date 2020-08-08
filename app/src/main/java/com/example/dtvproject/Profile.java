package com.example.dtvproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Profile extends AppCompatActivity {

    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        BottomNavigationView navView = findViewById(R.id.nav_bottom_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
                switch (menuItem.getItemId()){

                    case R.id.navigation_home:
                        in=new Intent(Profile.this,MainActivity.class);
                        startActivity(in);
                        break;
                    case R.id.nav_bottom_search:
                        in=new Intent(Profile.this,Seach_all.class);
                        startActivity(in);
                        break;
                    case R.id.nav_bottom_profile:

                        break;
                }
                return false;
            }
        });

    }
}
