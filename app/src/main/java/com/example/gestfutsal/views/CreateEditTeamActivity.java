package com.example.gestfutsal.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestfutsal.DBHelper;
import com.example.gestfutsal.R;

public class CreateEditTeamActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    private DBHelper db;
    Intent j;
    Intent a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_team);

        viewHolder.bt_cet_register_team = findViewById(R.id.bt_cet_register_team);
        viewHolder.bt_cet_update_team = findViewById(R.id.bt_cet_update_team);
        viewHolder.bt_cet_delete_team = findViewById(R.id.bt_cet_delete_team);
        viewHolder.bt_cet_exit_team = findViewById(R.id.bt_cet_exit_team);
        viewHolder.et_cet_levelTeam = findViewById(R.id.et_cet_levelTeam);
        viewHolder.et_cet_nameTeam = findViewById(R.id.et_cet_nameTeam);
        viewHolder.tv_cet_header = findViewById(R.id.tv_cet_header);

        //Intent J
        j = getIntent();
        int id = -1;

        //Intent A
        a = getIntent();
        String page = j.getExtras().getString("page").toString();
        String origenDetails = "details";
        String origenNew = "new";


        try {
            id = j.getExtras().getInt("id");
        } catch (Exception e) {
        }

        //Base dados
        db = new DBHelper(this);

        //BT registo
        viewHolder.bt_cet_register_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = viewHolder.et_cet_nameTeam.getText().toString().trim();
                long res = 0;

                if (viewHolder.et_cet_levelTeam.getText().toString().trim().isEmpty()) {
                    res = db.Insert_Team(name);
                } else {
                    int levelTeam = Integer.parseInt(viewHolder.et_cet_levelTeam.getText().toString());
                    res = db.Insert_Team(name, levelTeam);
                }
                if (res > 0) {
                    Toast.makeText(CreateEditTeamActivity.this, "Registo efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(CreateEditTeamActivity.this, ViewTeamsActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(CreateEditTeamActivity.this, "Erro ao registar!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        //BT update team
        viewHolder.bt_cet_update_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = viewHolder.et_cet_nameTeam.getText().toString().trim();
                int id = j.getExtras().getInt("id");
                long res = 0;

                if (viewHolder.et_cet_levelTeam.getText().toString().trim().isEmpty()) {
                    res = db.Update_Team(id, name);
                } else {
                    int levelTeam = Integer.parseInt(viewHolder.et_cet_levelTeam.getText().toString());
                    res = db.Update_Team(id, name, levelTeam);
                }
                if (res > 0) {
                    Toast.makeText(CreateEditTeamActivity.this, "Registo salvo com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent u = new Intent(CreateEditTeamActivity.this, DetailsTeamActivity.class);
                    u.putExtra("id",id);
                    startActivity(u);
                } else {
                    Toast.makeText(CreateEditTeamActivity.this, "Erro ao editar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BT Apagar
        viewHolder.bt_cet_delete_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = j.getExtras().getInt("id");
                long res = 0;
                res = db.Delete_Team(id);
                if (res > 0) {
                    Intent i = new Intent(CreateEditTeamActivity.this, ViewTeamsActivity.class);
                    startActivity(i);
                    Toast.makeText(CreateEditTeamActivity.this, "Registo eliminado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateEditTeamActivity.this, "Erro ao eliminar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //BT Sair
        viewHolder.bt_cet_exit_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (origenDetails.equals(page)){
                    Intent s = new Intent(CreateEditTeamActivity.this, DetailsTeamActivity.class);
                    startActivity(s);
                    finish();
                } else if (origenNew.equals(page)){
                    Intent s = new Intent(CreateEditTeamActivity.this, ViewTeamsActivity.class);
                    startActivity(s);
                    finish();
                }
            }
        });


        //Carregar dados para editar Team
        if (id > 0) {
            uploadDetailsTeam(id);
        }

    }

    private void uploadDetailsTeam(int id) {
        Cursor c = db.Team_SelectByID(id);
        c.moveToFirst();
        if (c.getCount() == 1) {

            int posName = c.getColumnIndex("name");
            String name = c.getString(posName);

            int posUnderAge = c.getColumnIndex("underAge");
            int underAge = c.getInt(posUnderAge);

            viewHolder.tv_cet_header.setText("Editar " + name);
            viewHolder.et_cet_nameTeam.setText(name);
            viewHolder.et_cet_levelTeam.setText(String.valueOf(underAge));
            viewHolder.bt_cet_register_team.setVisibility(View.GONE);
            viewHolder.bt_cet_update_team.setVisibility(View.VISIBLE);
            viewHolder.bt_cet_delete_team.setVisibility(View.VISIBLE);
        }
    }

    private static class ViewHolder {
        Button bt_cet_register_team, bt_cet_update_team, bt_cet_delete_team;
        EditText et_cet_levelTeam, et_cet_nameTeam;
        TextView tv_cet_header;
        ImageView bt_cet_exit_team;
    }
}
