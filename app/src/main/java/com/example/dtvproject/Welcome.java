package com.example.dtvproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {
private final int SPLASH_DISPLAY_LENGTH=3000;
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



            setContentView(R.layout.activity_welcome);
            imageView = findViewById(R.id.welcomeicon);


            final Animation an = AnimationUtils.loadAnimation(Welcome.this, R.anim.animation1);
            imageView.startAnimation(an);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent in = new Intent(Welcome.this, MainActivity.class);
                    startActivity(in);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
}
