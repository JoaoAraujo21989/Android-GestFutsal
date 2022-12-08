package com.example.gestfutsal.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.gestfutsal.DBHelper;
import com.example.gestfutsal.R;
import com.example.gestfutsal.adapter.AtheletesListAdapter;
import com.example.gestfutsal.model.Athelete;
import com.example.gestfutsal.model.Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewAthletesActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    private DBHelper db;
    List <Athelete> listaAtheletes;
    AtheletesListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_athletes);

        viewHolder.bt_va_newAthelet = findViewById(R.id.bt_va_newAthelet);
        viewHolder.rv_Atheletes = findViewById(R.id.rv_Atheletes);
        viewHolder.bt_va_exit = findViewById(R.id.bt_va_exit);
        viewHolder.bt_va_orderByAsc = findViewById(R.id.bt_va_orderByAsc);
        viewHolder.bt_va_orderByDesc = findViewById(R.id.bt_va_orderByDesc);

        listaAtheletes = new ArrayList<>();
        db = new DBHelper(this);

        //Botão Novo Atleta
        viewHolder.bt_va_newAthelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String page = "new";
                Intent a = new Intent(ViewAthletesActivity.this, CreateEditAtheleteActivity.class);
                a.putExtra("page",page);
                startActivity(a);
            }
        });

        //LISTA DE Atletas
        listaAtheletes = db.Atheletes_SelectAll();

        //Adapter
        adapter = new AtheletesListAdapter(listaAtheletes);
        viewHolder.rv_Atheletes.setAdapter(adapter);

        adapter.setOnItemClickListener(new AtheletesListAdapter.OnIntemClickListener() {
            @Override
            public void OnItemClick(int pos) {
                Intent a = new Intent(ViewAthletesActivity.this, DetailsAtheleteActivity.class);
                a.putExtra("id",listaAtheletes.get(pos).getId());
                startActivity(a);
            }
        });

        //Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewHolder.rv_Atheletes.setLayoutManager(linearLayoutManager);

        //BT Voltar
        viewHolder.bt_va_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(ViewAthletesActivity.this, MenuActivity.class);
                startActivity(e);
            }
        });

        //BT Order By ASC
        viewHolder.bt_va_orderByAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(listaAtheletes,Athelete.AtheleteNameAZComparator);
                adapter.notifyDataSetChanged();
            }
        });

        //BT Order By DESC
        viewHolder.bt_va_orderByDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(listaAtheletes,Athelete.AtheleteNameZAComparator);
                adapter.notifyDataSetChanged();
            }
        });

    }


    //ViewHolder
    private static class ViewHolder {
        Button bt_va_newAthelet;
        RecyclerView rv_Atheletes;
        ImageView bt_va_exit, bt_va_orderByAsc, bt_va_orderByDesc;
    }

}