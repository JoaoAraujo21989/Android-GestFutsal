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
import com.example.gestfutsal.adapter.TeamListAdapter;
import com.example.gestfutsal.model.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ViewTeamsActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    private DBHelper db;
    List<Team> listaTeams;
    TeamListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teams);

        viewHolder.bt_vt_newTeam = findViewById(R.id.bt_vt_newTeam);
        viewHolder.rv_Teams = findViewById(R.id.rv_Teams);
        viewHolder.bt_vt_exit = findViewById(R.id.bt_vt_exit);
        viewHolder.bt_vt_orderByAsc = findViewById(R.id.bt_vt_orderByAsc);
        viewHolder.bt_vt_orderByDesc = findViewById(R.id.bt_vt_orderByDesc);

        listaTeams = new ArrayList<>();
        db = new DBHelper(this);

        //Botão New Team
        viewHolder.bt_vt_newTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String page = "new";
                Intent a = new Intent(ViewTeamsActivity.this, CreateEditTeamActivity.class);
                a.putExtra("page",page);
                startActivity(a);
            }
        });

        //LISTA DE EQUIPAS
        listaTeams = db.Team_SelectAll();

        //Adapter
        adapter = new TeamListAdapter(listaTeams);
        viewHolder.rv_Teams.setAdapter(adapter);

        adapter.setOnItemClickListener(new TeamListAdapter.OnIntemClickListener() {
            @Override
            public void OnItemClick(int pos) {
                Intent i = new Intent(ViewTeamsActivity.this, DetailsTeamActivity.class);
                i.putExtra("id",listaTeams.get(pos).getId());
                startActivity(i);
            }
        });

        //Layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        ViewHolder.rv_Teams.setLayoutManager(linearLayoutManager);

        //BT Voltar
        viewHolder.bt_vt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(ViewTeamsActivity.this, MenuActivity.class);
                startActivity(e);
            }
        });

        //BT Order by ASC
        viewHolder.bt_vt_orderByAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(listaTeams,Team.TeamNameAZComparator);
                adapter.notifyDataSetChanged();
            }
        });

        //BT Order by DESC
        viewHolder.bt_vt_orderByDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.sort(listaTeams,Team.TeamNameZAComparator);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private static class ViewHolder {
        Button bt_vt_newTeam;
        static RecyclerView rv_Teams;
        ImageView bt_vt_exit, bt_vt_orderByAsc, bt_vt_orderByDesc;
    }
}