package com.example.gestfutsal.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestfutsal.DBHelper;
import com.example.gestfutsal.R;
import com.example.gestfutsal.adapter.AtheletesListAdapter;
import com.example.gestfutsal.adapter.TeamListAdapter;
import com.example.gestfutsal.model.Athelete;
import com.example.gestfutsal.model.Team;

import java.util.ArrayList;
import java.util.List;

public class DetailsTeamActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    List<Athelete> listaAtheletes;
    AtheletesListAdapter adapter;
    Intent u;
    Intent i;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_team);

        viewHolder.tv_dt_header = findViewById(R.id.tv_dt_header);
        viewHolder.bt_dt_edit = findViewById(R.id.bt_dt_edit);
        viewHolder.rv_dt_atheletes = findViewById(R.id.rv_dt_atheletes);
        viewHolder.bt_dt_exit = findViewById(R.id.bt_dt_exit);
        viewHolder.bt_dt_orderByAsc = findViewById(R.id.bt_dt_orderByAsc);
        viewHolder.bt_dt_orderByDesc = findViewById(R.id.bt_dt_orderByDesc);

        listaAtheletes = new ArrayList<>();


        //Intent U
        u = getIntent();
        int idU = -1;
        if (u.getExtras().getInt("id")>0){
            idU = u.getExtras().getInt("id");
        } else {
            finish();
        }

        //Intent
        i = getIntent();
        int id = -1;
        if (u.getExtras().getInt("id")>0){
            id = idU;
        } else {
            id = i.getExtras().getInt("id");
        }

        //Banco de dados
        db = new DBHelper(this);

        //Carregar dados Team
        uploadTeam(id);

        //BT Editar
        viewHolder.bt_dt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String page = "details";
                Intent j = new Intent(DetailsTeamActivity.this, CreateEditTeamActivity.class);
                j.putExtra("id",i.getExtras().getInt("id"));
                j.putExtra("page",page);
                startActivity(j);
            }
        });

        //BT Voltar
        viewHolder.bt_dt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent v = new Intent(DetailsTeamActivity.this, ViewTeamsActivity.class);
                startActivity(v);
            }
        });

        //BT Order by ASC
        viewHolder.bt_dt_orderByAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //BT Order by DESC
        viewHolder.bt_dt_orderByDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        //LISTA DE EQUIPAS
        int idTeam = i.getExtras().getInt("id");
        listaAtheletes = db.Athelete_SelectByTeam(idTeam);

        //Adapter
        adapter = new AtheletesListAdapter(listaAtheletes);
        viewHolder.rv_dt_atheletes.setAdapter(adapter);
        //Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewHolder.rv_dt_atheletes.setLayoutManager(linearLayoutManager);

        //Click lista de Atleta
        adapter.setOnItemClickListener(new AtheletesListAdapter.OnIntemClickListener() {
            @Override
            public void OnItemClick(int pos) {
                Intent j = new Intent(DetailsTeamActivity.this, DetailsAtheleteActivity.class);
                j.putExtra("id",listaAtheletes.get(pos).getId());
                startActivity(j);
            }
        });

    }

    //Carregar dados para os campos
    private void uploadTeam(int id) {
        Cursor c = db.Team_SelectByID(id);
        c.moveToFirst();
        if (c.getCount()==1){

            int posName = c.getColumnIndex("name");
            String name = c.getString(posName);

            int posUnderAge = c.getColumnIndex("underAge");
            int underAge = c.getInt(posUnderAge);

            viewHolder.tv_dt_header.setText(name);

            if (underAge == 0){
                viewHolder.tv_dt_header.setText(name);
            } else{
                viewHolder.tv_dt_header.setText("Sub " + underAge + " " + name);
            }

        }else if(c.getCount()==0){
            Toast.makeText(this, "Equipa não existe", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Erro ao carregar equipa", Toast.LENGTH_SHORT).show();
        }
    }



    private static class ViewHolder {
        TextView tv_dt_header;
        Button bt_dt_edit;
        static RecyclerView rv_dt_atheletes;
        ImageView bt_dt_exit, bt_dt_orderByAsc, bt_dt_orderByDesc;
    }

}