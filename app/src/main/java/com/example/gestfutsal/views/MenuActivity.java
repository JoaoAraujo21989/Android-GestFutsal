package com.example.gestfutsal.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.gestfutsal.R;

public class MenuActivity extends AppCompatActivity {

    LinearLayout ll_clubes, ll_atletas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ll_atletas =findViewById(R.id.ll_atletas);
        ll_clubes =findViewById(R.id.ll_clubes);

        ll_atletas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, ViewAthletesActivity.class);
                startActivity(i);
            }
        });

        ll_clubes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(MenuActivity.this, ViewTeamsActivity.class);
                startActivity(j);
            }
        });

    }


}