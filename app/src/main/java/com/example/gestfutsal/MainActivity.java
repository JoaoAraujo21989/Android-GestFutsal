package com.example.gestfutsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.gestfutsal.views.LogInActivity;
import com.example.gestfutsal.views.MenuActivity;

public class MainActivity extends AppCompatActivity {

    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LogInActivity.class);

                //-------------------------------TESTES--------------------------------------------
                //Intent i = new Intent(MainActivity.this,CreateEditAtheleteActivity.class);
                //Intent i = new Intent(MainActivity.this, MenuActivity.class);

                startActivity(i);
            }
        },2000);
    }
}
