package com.example.gestfutsal.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestfutsal.DBHelper;
import com.example.gestfutsal.MapsActivity;
import com.example.gestfutsal.R;
import com.example.gestfutsal.model.Team;

import java.util.List;

public class DetailsAtheleteActivity extends AppCompatActivity {

    private ViewHolder viewHolder = new ViewHolder();
    private DBHelper db;
    Intent i;
    Intent u;
    List<Team> listTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_athelete);

        viewHolder.bt_da_edit = findViewById(R.id.bt_da_edit);
        viewHolder.bt_da_exit_athelete = findViewById(R.id.bt_da_exit_athelete);
        viewHolder.tv_da_name = findViewById(R.id.tv_da_name);
        viewHolder.tv_da_number = findViewById(R.id.tv_da_number);
        viewHolder.tv_da_levelTeam = findViewById(R.id.tv_da_levelTeam);
        viewHolder.tv_da_genre = findViewById(R.id.tv_da_genre);
        viewHolder.tv_da_email = findViewById(R.id.tv_da_email);
        viewHolder.tv_da_tel = findViewById(R.id.tv_da_tel);
        viewHolder.tv_da_address = findViewById(R.id.tv_da_address);
        viewHolder.iv_da_email_athelete = findViewById(R.id.iv_da_email_athelete);
        viewHolder.iv_da_tel_athelete = findViewById(R.id.iv_da_tel_athelete);
        viewHolder.iv_da_address_athelete = findViewById(R.id.iv_da_address_athelete);
        viewHolder.iv_da_tel_whats_athelete = findViewById(R.id.iv_da_tel_whats_athelete);

        //Banco de dados
        db = new DBHelper(this);

        //Intent U
        u = getIntent();
        int idU = -1;
        if (u.getExtras().getInt("id")>0){
            idU = u.getExtras().getInt("id");
        } else {
            finish();
        }

        //Intent I
        i = getIntent();
        int id = -1;
        if (u.getExtras().getInt("id")>0){
            id = idU;
        } else {
            id = i.getExtras().getInt("id");
        }

        //Carregar dados Athelete by ID
        uploadAthelete(id);


        //Botão Editar Atleta
        int finalId = id;
        viewHolder.bt_da_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String page = "details";
                Intent j = new Intent(DetailsAtheleteActivity.this, CreateEditAtheleteActivity.class);
                j.putExtra("id", finalId);
                j.putExtra("page",page);
                startActivity(j);
            }
        });

        //Botão Sair
        viewHolder.bt_da_exit_athelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailsAtheleteActivity.this, ViewAthletesActivity.class);
                startActivity(i);
                finish();
            }
        });

        //Botão Whats
        viewHolder.iv_da_tel_whats_athelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + viewHolder.tv_da_tel.getText().toString());
                Intent w = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(w);
                finish();
            }
        });


        //Botão enviar email
        viewHolder.iv_da_email_athelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String envEmail = viewHolder.tv_da_email.getText().toString().trim();
                if (!envEmail.isEmpty()){
                    Intent e = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" +envEmail));
                    e.putExtra(Intent.EXTRA_EMAIL,envEmail);
                    e.putExtra(Intent.EXTRA_SUBJECT,"Email de comfirmação");
                    e.putExtra(Intent.EXTRA_TEXT,"Email enviado pela aplicação de GestFutsal");
                    startActivity(Intent.createChooser(e,"Chooser"));
                }
            }
        });

        //Botão chamar tel
        viewHolder.iv_da_tel_athelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numTel = viewHolder.tv_da_tel.getText().toString().trim();
                if (!numTel.isEmpty()){
                    Intent t = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" +numTel));
                    startActivity(t);
                }
            }
        });

        //Botão ver google maps
        viewHolder.iv_da_address_athelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String morada = viewHolder.tv_da_address.getText().toString().trim();
                if (!morada.isEmpty()){
                    Intent m = new Intent(DetailsAtheleteActivity.this, MapsActivity.class);
                    m.putExtra("morada",morada);
                    startActivity(m);
                }
            }
        });


    }

    //Carregar dados do atleta by ID
    private void uploadAthelete(int id) {
        //Carregar dados Equipas para Ver ATLETA
        listTeam = db.Team_SelectAll();

        Cursor c = db.Athelete_SelectByID(id);
        c.moveToFirst();
        if (c.getCount() == 1) {

            int posName = c.getColumnIndex("name");
            String name = c.getString(posName);

            int posNumber = c.getColumnIndex("number");
            int number = c.getInt(posNumber);

            int posGenre = c.getColumnIndex("genre");
            String genre = c.getString(posGenre);

            int posEmail = c.getColumnIndex("email");
            String email = c.getString(posEmail);

            int posTel = c.getColumnIndex("tel");
            String tel = c.getString(posTel);

            int posAddress = c.getColumnIndex("address");
            String address = c.getString(posAddress);

            int posIdTeam = c.getColumnIndex("idTeam");
            int idTeam = c.getInt(posIdTeam);

            viewHolder.tv_da_name.setText(name);
            viewHolder.tv_da_number.setText(String.valueOf(number));

            for (int j = 0; j < listTeam.size(); j++) {
                if (listTeam.get(j).getId()==idTeam){
                    viewHolder.tv_da_levelTeam.setText(listTeam.get(j).getName());
                }
            }

            viewHolder.tv_da_genre.setText(genre);
            viewHolder.tv_da_email.setText(email);
            viewHolder.tv_da_tel.setText(tel);
            viewHolder.tv_da_address.setText(address);

        } else if (c.getCount() == 0) {
            Toast.makeText(this, "Equipa não existe", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Erro ao carregar equipa", Toast.LENGTH_SHORT).show();
        }

    }

    //ViewHolder
    private static class ViewHolder {
        Button bt_da_edit;
        TextView tv_da_name, tv_da_number, tv_da_levelTeam, tv_da_genre, tv_da_email, tv_da_tel, tv_da_address;
        ImageView iv_da_email_athelete, iv_da_tel_athelete, iv_da_address_athelete, bt_da_exit_athelete, iv_da_tel_whats_athelete;
    }
}